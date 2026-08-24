package womp.wild.boars.entity.goals;

import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import womp.wild.boars.entity.Boar;

public class ScratchEarGoal extends RandomLookAroundGoal {

    private final Boar boar;
    private int timeToScratch = 0, timeSinceLastScratch = 0;


    public ScratchEarGoal(Boar boar) {
        super(boar);
        this.boar = boar;
    }

    @Override
    public void start() {
        super.start();
        timeToScratch = 0;
        timeSinceLastScratch = 0;
    }
    
    @Override
    public void tick() {
        if (timeToScratch == 0) {
            boar.setScratching((int)(Math.random() * 2 + 1));
            timeToScratch = boar.getRandom().nextInt(40, 101);
            timeSinceLastScratch = 0;
        } else if (timeToScratch > 0) {
            timeToScratch--;
            timeSinceLastScratch++;
        }
        super.tick();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() || timeSinceLastScratch < 30;
    }

    @Override
    public void stop() {
        super.stop();
        timeToScratch = -1;
    }
}
