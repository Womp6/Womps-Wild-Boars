package womp.wild.boars.entity.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import womp.wild.boars.entity.Boar;

public class BoarMeleeAttackGoal extends MeleeAttackGoal {

    private final Boar boar;
    private int attackWindup = -1;

    public BoarMeleeAttackGoal(Boar mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(mob, speedModifier, followingTargetEvenIfNotSeen);
        this.boar = mob;
    }

    @Override
    protected void checkAndPerformAttack(final LivingEntity target) {
        if (this.canPerformAttack(target) && attackWindup == 0) {
            this.resetAttackCooldown();
            this.mob.doHurtTarget(getServerLevel(this.mob), target);
            attackWindup = -1;
            boar.setAttacking(false);
        } else if (attackWindup == 0) {
            attackWindup = -1;
            boar.setAttacking(false);
        } else if (this.isTimeToAttack() && this.mob.distanceToSqr(target) < (target.getBbWidth() + 1.0F) * (target.getBbWidth() + 1.0F) && attackWindup == -1) {
            attackWindup = 7;
            boar.setAttacking(true);
            boar.makeSound(boar.getWarningSound());
        } else boar.setAttacking(false);
    }

    @Override
    public void stop() {
        super.stop();
        boar.setAttacking(false);
    }

    @Override
    public void tick() {
        super.tick();
        if (attackWindup > 0) {
            attackWindup--;
        }
    }

    @Override
    public void start() {
        boar.stopSniffing();
        super.start();
    }
}
