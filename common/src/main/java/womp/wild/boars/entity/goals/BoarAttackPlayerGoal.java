package womp.wild.boars.entity.goals;

import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import womp.wild.boars.entity.Boar;

public class BoarAttackPlayerGoal extends NearestAttackableTargetGoal<Player> {

    private final Boar boar;

    public BoarAttackPlayerGoal(Boar boar) {
        super(boar, Player.class, 20, true, true, null);
        this.boar = boar;
    }

    @Override
    public boolean canUse() {
        if (boar.isBaby()) {
            return false;
        }

        if (super.canUse()) {
            for (Boar boar : this.boar.level().getEntitiesOfClass(Boar.class, this.boar.getBoundingBox().inflate(8.0, 4.0, 8.0))) {
                if (boar.isBaby()) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    protected double getFollowDistance() {
        return super.getFollowDistance() * 0.75;
    }
}
