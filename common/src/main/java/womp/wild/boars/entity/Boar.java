package womp.wild.boars.entity;

import java.util.function.IntFunction;
import java.util.function.Predicate;

import org.jspecify.annotations.Nullable;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions.Selector;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import womp.wild.boars.entity.goals.AttackCropGoal;
import womp.wild.boars.entity.goals.BoarAttackPlayerGoal;
import womp.wild.boars.entity.goals.BoarHurtByTargetGoal;
import womp.wild.boars.entity.goals.BoarMeleeAttackGoal;
import womp.wild.boars.entity.goals.PickBerriesGoal;
import womp.wild.boars.entity.goals.RunFarGoal;
import womp.wild.boars.entity.goals.ScavengeGoal;
import womp.wild.boars.entity.goals.ScratchEarGoal;
import womp.wild.boars.entity.goals.SniffGroundGoal;
import womp.wild.boars.registry.BoarEntities;
import womp.wild.boars.registry.BoarSounds;
import womp.wild.boars.util.BoarTags;

public class Boar extends Animal implements NeutralMob {

    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(40, 60);
    private static final Selector PREY_SELECTOR = (target, level) -> target.is(EntityType.RABBIT) || target.is(EntityType.FROG) || (target.is(EntityType.CHICKEN) && target.isBaby());
    private static final Predicate<ItemEntity> ALLOWED_ITEMS = e -> !e.hasPickUpDelay() && e.isAlive() && e.getItem().is(BoarTags.Items.BOAR_FOOD);
    private long persistentAngerEndTime;
	private @Nullable EntityReference<LivingEntity> persistentAngerTarget;
    public int eatCooldown = 0, attackCropCooldown = 0;
    private int attackAnimTicks = 0;
    public int sniffTicks = 0, timeToNextSniff = 10;
    public static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(Boar.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> SCRATCH = SynchedEntityData.defineId(Boar.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> SNIFFING = SynchedEntityData.defineId(Boar.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Boar.class, EntityDataSerializers.INT);

    public AnimationState idleAnimationState = new AnimationState();
    public AnimationState scratchLeftEarAnimationState = new AnimationState();
    public AnimationState scratchRightEarAnimationState = new AnimationState();
    public AnimationState sniffAnimationState = new AnimationState();
    public AnimationState attackAnimationState = new AnimationState();

    public Boar(EntityType<? extends Animal> type, Level level) {
        super(type, level);
        setCanPickUpLoot(true);
    }

    public void setupAnimationStates() {
        if (!isMoving() && !isSniffing()) {
            idleAnimationState.startIfStopped(this.tickCount);
            if (getEarScratch() != 0) {
                int ear = getEarScratch();
                if (ear == 1) scratchLeftEarAnimationState.start(this.tickCount);
                else scratchRightEarAnimationState.start(this.tickCount);
                setScratching(0);
            }
        } else idleAnimationState.stop();
        if (isAttacking() && attackAnimTicks <= 0) {
            attackAnimationState.startIfStopped(this.tickCount);
            attackAnimTicks = 15;
        } else attackAnimTicks--;

        if (!isSniffing() && sniffAnimationState.isStarted()) {
            sniffAnimationState.stop();
        }
    }

    public static AttributeSupplier.Builder createBoarAttributes() {
        return Animal.createMobAttributes().add(Attributes.MAX_HEALTH, 14.0d).add(Attributes.ATTACK_DAMAGE, 6.0d).add(Attributes.MOVEMENT_SPEED, 0.25);
    }

    public boolean canSpawn(EntityType<? extends Boar> type, LevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
		boolean brightEnoughToSpawn = EntitySpawnReason.ignoresLightRequirements(spawnReason) || isBrightEnoughToSpawn(level, pos);
		return level.getBlockState(pos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && brightEnoughToSpawn;
	}

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(BoarTags.Items.BOAR_BREED);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        Boar child;
        if((child = BoarEntities.WILD_BOAR.get().create(level, EntitySpawnReason.BREEDING)) != null && partner instanceof Boar mate) {
            child.setVariant((random.nextBoolean() ? this : mate).getVariant());
            child.setPersistenceRequired();
            return child;
        }
        return null;
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BoarMeleeAttackGoal(this, 1.25, true));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
		this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(4, new PickBerriesGoal(this, 1.1f, 12, 5));
        this.goalSelector.addGoal(4, new AttackCropGoal(this, 0.9f, 8, 4));
        this.goalSelector.addGoal(5, new ScavengeGoal(this, ALLOWED_ITEMS));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(8, new ScratchEarGoal(this));
		this.goalSelector.addGoal(9, new SniffGroundGoal(this));
		this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(11, new RunFarGoal(this, 1.42));
        this.targetSelector.addGoal(1, new BoarHurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(2, new BoarAttackPlayerGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Animal.class, false, (entity, level) -> {
            return eatCooldown <= 0 && PREY_SELECTOR.test(entity, level);
        }));
        this.targetSelector.addGoal(5, new ResetUniversalAngerTargetGoal<>(this, false));
    }

    @Override
	protected void readAdditionalSaveData(ValueInput input) {
		super.readAdditionalSaveData(input);
		this.readPersistentAngerSaveData(this.level(), input);
        eatCooldown = input.getIntOr("eatCooldown", 0);
        attackCropCooldown = input.getIntOr("attackCropCooldown", 0);
        setCanPickUpLoot(true);
        this.setVariant(BoarVariant.byId(input.getIntOr("variant", getRandomVariant(random).getIndex())));
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		super.addAdditionalSaveData(output);
		this.addPersistentAngerSaveData(output);
        output.putInt("eatCooldown", eatCooldown);
        output.putInt("attackCropCooldown", attackCropCooldown);
        output.putInt("variant", this.getVariant().getIndex());
	}

	@Override
	public void startPersistentAngerTimer() {
		this.setTimeToRemainAngry(PERSISTENT_ANGER_TIME.sample(this.random));
	}

	@Override
	public void setPersistentAngerEndTime(long endTime) {
		this.persistentAngerEndTime = endTime;
	}

	@Override
	public long getPersistentAngerEndTime() {
		return persistentAngerEndTime;
	}

	@Override
	public void setPersistentAngerTarget(EntityReference<LivingEntity> persistentAngerTarget) {
		this.persistentAngerTarget = persistentAngerTarget;
	}

	@Override
	public EntityReference<LivingEntity> getPersistentAngerTarget() {
		return persistentAngerTarget;
	}

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
			this.updatePersistentAnger((ServerLevel)this.level(), true);
		}

        if (eatCooldown > 0) eatCooldown--;
        if (attackCropCooldown > 0) attackCropCooldown--;

        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
        if (attackAnimTicks > 0) attackAnimTicks--;

        if (isSniffing()) {
            if (timeToNextSniff <= 0) {
                this.playSound(getSniffSound(), 0.6f, 1f);
                timeToNextSniff = random.nextInt(30, 51);
            } else timeToNextSniff--;
        }

        if (isSniffing()) {
            if (this.level().isClientSide()) sniffAnimationState.startIfStopped(this.tickCount);
        }
    }

    public void stopSniffing() {
        setSniffing(false);
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.9F;
    }

    private void dropItemStack(ItemStack itemStack) {
		ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), itemStack);
		this.level().addFreshEntity(itemEntity);
	}

    @Override
	protected void pickUpItem(ServerLevel level, ItemEntity entity) {
		ItemStack itemStack = entity.getItem();
		if (itemStack.is(BoarTags.Items.BOAR_FOOD)) {
			int count = itemStack.getCount();
			if (count > 1) {
				this.dropItemStack(itemStack.split(count - 1));
			}
            onItemPickup(entity);
            playEatSound();
			entity.discard();
            this.eatCooldown = 1000;
            this.attackCropCooldown = 6000;
		}
	}

    public void playEatSound() {
        this.playSound(SoundEvents.GENERIC_EAT.value());
    }

    public boolean isMoving() {
        return xOld != getX() || zOld != getZ();
    }
 
    @Override
    public boolean wantsToPickUp(ServerLevel level, ItemStack itemStack) {
        return eatCooldown == 0 && itemStack.is(BoarTags.Items.BOAR_FOOD) && !isMoving();
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    /**
     * Pass 0 for no ears, 1 for the left ear, and 2 for the right ear.
     * @param ear
     */
    public void setScratching(int ear) {
        this.entityData.set(SCRATCH, ear);
    }

    public int getEarScratch() {
        return this.entityData.get(SCRATCH);
    }

    public void setSniffing(boolean value) {
        this.entityData.set(SNIFFING, value);
    }

    public boolean isSniffing() {
        return this.entityData.get(SNIFFING);
    }

    @Override
    protected void defineSynchedData(Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(ATTACKING, false).define(SCRATCH, 0).define(SNIFFING, false).define(VARIANT, 0);
    }

    public BoarVariant getVariant() {
        return BoarVariant.byId(this.entityData.get(VARIANT));
    }

    public void setVariant(BoarVariant variant) {
        this.entityData.set(VARIANT, variant.getIndex());
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return BoarSounds.BOAR_AMBIENT.get();
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        return BoarSounds.BOAR_HURT.get();
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return BoarSounds.BOAR_DEATH.get();
    }

    public SoundEvent getWarningSound() {
        return BoarSounds.BOAR_WARN.get();
    }

    public SoundEvent getSniffSound() {
        return BoarSounds.BOAR_SNIFF.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockState) {
        this.playSound(BoarSounds.BOAR_STEP.get(), 0.20f, 1);
    }

    @Override
	public EntityDimensions getDefaultDimensions(final Pose pose) {
		return this.isBaby() ? BoarEntities.WILD_BOAR.get().getDimensions().scale(0.7F).withEyeHeight(10F / 16) : super.getDefaultDimensions(pose);
	}

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, SpawnGroupData groupData) {
        BoarVariant variant;
        RandomSource random = level.getRandom();
        if (groupData instanceof BoarData data) {
            variant = data.variant;
        } else {
            variant = getRandomVariant(random);
            groupData = new BoarData(variant);
        }
        setVariant(variant);
        return super.finalizeSpawn(level, difficulty, spawnReason, groupData);
    }

    public BoarVariant getRandomVariant(RandomSource random) {
        BoarVariant[] variants = BoarVariant.values();
        int[] weights = new int[variants.length];
        weights[0] = variants[0].getChance();
        for (int i = 1; i < variants.length; i++) {
            weights[i] = weights[i - 1] + variants[i].chance;
        }
        int choice = random.nextInt(0, 100);
        for (int i = 0; i < weights.length; i++) {
            if (choice < weights[i]) return variants[i];
        }
        return BoarVariant.BLACK_BOAR;
    }

    public static enum BoarVariant implements StringRepresentable {

        BLACK_BOAR(0, "black_boar", 40),
        BLACK_BOAR_NO_TUSKS(1, "black_boar_no_tusks", 30),
        BROWN_BOAR(2, "brown_boar", 20),
        BROWN_BOAR_NO_TUSKS(3, "brown_boar_no_tusks", 10);

        public static final Codec<BoarVariant> CODEC;
        private static final IntFunction<BoarVariant> BY_ID;
        final int id;
        private final String name;
        private final int chance;

        private BoarVariant(int id, String name, int chance) {
            this.id = id;
            this.name = name;
            this.chance = chance;
        }

        public int getIndex() {
            return this.id;
        }

        public int getChance() {
            return chance;
        }

        public static BoarVariant byId(int id) {
            return BY_ID.apply(id);
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        static {
            CODEC = StringRepresentable.fromEnum(BoarVariant::values);
            BY_ID = ByIdMap.continuous(BoarVariant::getIndex, BoarVariant.values(), ByIdMap.OutOfBoundsStrategy.CLAMP);
        }
    }

    static class BoarData extends AgeableMob.AgeableMobGroupData {
        public final BoarVariant variant;

        BoarData(BoarVariant variant) {
            super(true);
            this.variant = variant;
        }
    }
}
