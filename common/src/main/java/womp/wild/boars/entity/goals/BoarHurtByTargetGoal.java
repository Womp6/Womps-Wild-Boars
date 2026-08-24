package womp.wild.boars.entity.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import womp.wild.boars.entity.Boar;

public class BoarHurtByTargetGoal extends HurtByTargetGoal {
    private final Boar boar;

    public BoarHurtByTargetGoal(Boar boar) {
        super(boar);
        this.boar = boar;
    }

    @Override
    public void start() {
        super.start();
        if (boar.isBaby()) {
            this.alertOthers();
            this.stop();
        }
    }

    @Override
    public void alertOther(Mob other, final LivingEntity hurtByMob) {
        if (other instanceof Boar && !other.isBaby()) {
            super.alertOther(other, hurtByMob);
        }
    }
}
