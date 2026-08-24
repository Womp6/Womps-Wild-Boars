package womp.wild.boars.entity.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gamerules.GameRules;
import womp.wild.boars.entity.Boar;

public class PickBerriesGoal extends MoveToBlockGoal {
    
    protected int ticksWaited;
    private final Boar boar;

    public PickBerriesGoal(Boar boar, double speedModifier, int searchRange, int verticalSearchRange) {
        super(boar, speedModifier, searchRange, verticalSearchRange);
        this.boar = boar;
    }

    @Override
    public double acceptedDistance() {
        return 2.0;
    }

    @Override
    public boolean shouldRecalculatePath() {
        return this.tryTicks % 100 == 0;
    }

    @Override
    protected boolean isValidTarget(final LevelReader level, final BlockPos pos) {
        BlockState blockState = level.getBlockState(pos);
        return blockState.is(Blocks.SWEET_BERRY_BUSH) && blockState.getValue(SweetBerryBushBlock.AGE) >= 2;
    }

    @Override
    public void tick() {
        if (this.isReachedTarget()) {
            if (this.ticksWaited >= 40 && !boar.isMoving()) {
                this.onReachedTarget();
            } else if (!boar.isMoving()) {
                this.ticksWaited++;
            } else this.ticksWaited = 0;
        }

        super.tick();
    }

    protected void onReachedTarget() {
        if (getServerLevel(boar.level()).getGameRules().get(GameRules.MOB_GRIEFING)) {
            BlockState state = boar.level().getBlockState(this.blockPos);
            if (state.is(Blocks.SWEET_BERRY_BUSH)) {
                this.pickSweetBerries(state);
            }
        }
    }

    private void pickSweetBerries(BlockState state) {
        int age = state.getValue(SweetBerryBushBlock.AGE);
        state.setValue(SweetBerryBushBlock.AGE, 1);
        int count = boar.level().getRandom().nextInt(2) + (age == 3 ? 1 : 0);
        boar.playEatSound();
        boar.eatCooldown = 1000;
        Block.popResource(boar.level(), this.blockPos, new ItemStack(Items.SWEET_BERRIES, count));

        boar.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 1.0F, 1.0F);
        boar.level().setBlock(this.blockPos, state.setValue(SweetBerryBushBlock.AGE, 1), 2);
        boar.level().gameEvent(GameEvent.BLOCK_CHANGE, this.blockPos, GameEvent.Context.of(boar));
    }

    @Override
    public boolean canUse() {
        return super.canUse() && boar.eatCooldown <= 0;
    }

    @Override
    public void start() {
        this.ticksWaited = 0;
        super.start();
    }
}
