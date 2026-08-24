package womp.wild.boars.entity.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import womp.wild.boars.entity.Boar;

public class SniffGroundGoal extends RandomLookAroundGoal {

    private final Boar boar;
    private int timeSinceSniffing = 0;


    public SniffGroundGoal(Boar boar) {
        super(boar);
        this.boar = boar;
    }

    @Override
    public boolean canUse() {
        return boar.getRandom().nextInt(200) == 0;
    }

    @Override
    public void start() {
        super.start();
        timeSinceSniffing = 0;
        boar.setSniffing(true);
        boar.timeToNextSniff = 10;
    }
    
    @Override
    public void tick() {
        timeSinceSniffing++;
        super.tick();
    }

    @Override
    public boolean canContinueToUse() {
        return timeSinceSniffing < 179 && boar.getTarget() == null;
    }

    @Override
    public void stop() {
        super.stop();
        boar.stopSniffing();
        if (timeSinceSniffing > 170 && (int)(Math.random() * 10) == 0) dropRandomItem(); 
    }

    @Override
    public boolean isInterruptable() {
        return timeSinceSniffing > 179 || boar.getTarget() != null;
    }

    public void dropRandomItem() {
        Vec3 view = boar.getViewVector(1.0F);
        Vec3 sniffPos = boar.position().add(view.x * 19.0 / 16.0, 0, view.y * 19.0 / 16.0);
        BlockPos sniffBlockPos = BlockPos.containing(sniffPos).below();

        if (!boar.level().getBlockState(sniffBlockPos).is(BlockTags.GRASS_BLOCKS)) return;

        Item item;
        int pick = (int)(Math.random() * 5);
        if (pick == 0) item = Items.BEETROOT;
        else if (pick == 1) item = Items.POTATO;
        else if (pick == 2) item = Items.CARROT;
        else if (pick == 3) item = Items.WHEAT_SEEDS;
        else item = Items.BEETROOT_SEEDS;

        ItemEntity toDrop = new ItemEntity(boar.level(), sniffPos.x, sniffPos.y, sniffPos.z, new ItemStack(item, (int)(Math.random() * 2 + 1)));
        toDrop.setPickUpDelay(15);
        boar.level().addFreshEntity(toDrop);
    }
}