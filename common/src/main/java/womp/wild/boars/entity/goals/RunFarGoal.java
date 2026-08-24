package womp.wild.boars.entity.goals;

import java.util.EnumSet;

import org.jspecify.annotations.Nullable;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;
import womp.wild.boars.entity.Boar;

public class RunFarGoal extends Goal {

    private final PathfinderMob mob;
    private final double speedModifier;
    protected double wantedX;
	protected double wantedY;
	protected double wantedZ;
    private int runTime;

    public RunFarGoal(PathfinderMob mob, double speedModifier) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }
    
    protected @Nullable Vec3 getPosition() {
        return LandRandomPos.getPosAway(mob, 50, 100, 10, mob.position());
    }

    @Override
    public boolean canUse() {
        if (this.mob.getNoActionTime() >= 100) {
            return false;
        }

        if (mob.getRandom().nextInt(200) != 0) return false;
        
        Vec3 pos = this.getPosition();
		if (pos == null) {
			return false;
		}

		this.wantedX = pos.x;
		this.wantedY = pos.y;
		this.wantedZ = pos.z;
		return true;
    }

    @Override
    public void start() {
        this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
        runTime = 0;
        if (mob instanceof Boar boar) boar.stopSniffing();
    }

    @Override
	public boolean canContinueToUse() {
		return !this.mob.getNavigation().isDone() && runTime++ < 400;
	}

    @Override
	public void stop() {
		this.mob.getNavigation().stop();
		super.stop();
	}

    @Override
    public boolean isInterruptable() {
        return true;
    }
}
