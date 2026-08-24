package womp.wild.boars.entity.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BeetrootBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gamerules.GameRules;
import womp.wild.boars.entity.Boar;

public class AttackCropGoal extends MoveToBlockGoal {
    
    protected int ticksWaited;
    private final Boar boar;

    public AttackCropGoal(Boar boar, double speedModifier, int searchRange, int verticalSearchRange) {
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
        return ((blockState.is(Blocks.WHEAT) || blockState.is(Blocks.POTATOES) || blockState.is(Blocks.CARROTS)) && blockState.getValue(CropBlock.AGE) == 7)
            || (blockState.is(Blocks.BEETROOTS) && blockState.getValue(BeetrootBlock.AGE) == 3);
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
            boar.level().destroyBlock(blockPos, true, boar);
            boar.level().gameEvent(GameEvent.BLOCK_CHANGE, this.blockPos, GameEvent.Context.of(boar));
            boar.attackCropCooldown = 6000;
        }
    }

    @Override
    public boolean canUse() {
        return super.canUse() && boar.eatCooldown <= 0 && boar.attackCropCooldown <= 0;
    }

    @Override
    public void start() {
        this.ticksWaited = 0;
        super.start();
    }
}