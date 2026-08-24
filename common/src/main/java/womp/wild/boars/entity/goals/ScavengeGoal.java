package womp.wild.boars.entity.goals;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import womp.wild.boars.entity.Boar;

public class ScavengeGoal extends Goal {

    private final Boar boar;
    private final Predicate<ItemEntity> allowedItems;
    
    public ScavengeGoal(Boar boar, Predicate<ItemEntity> allowedItems) {
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.boar = boar;
        this.allowedItems = allowedItems;
    }

    @Override
    public boolean canUse() {
        if (!boar.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
            return false;
        }

        if (boar.getTarget() != null || boar.getLastHurtByMob() != null) {
            return false;
        }

        if (boar.getRandom().nextInt(reducedTickDelay(10)) != 0) {
            return false;
        }

        if (boar.eatCooldown > 0) return false;

        List<ItemEntity> items = boar.level().getEntitiesOfClass(ItemEntity.class, boar.getBoundingBox().inflate(8.0, 8.0, 8.0), allowedItems);
        return !items.isEmpty() && boar.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
    }

    @Override
    public void tick() {
        List<ItemEntity> items = boar.level().getEntitiesOfClass(ItemEntity.class, boar.getBoundingBox().inflate(8.0, 8.0, 8.0), allowedItems);
        ItemStack itemStack = boar.getItemBySlot(EquipmentSlot.MAINHAND);
        if (itemStack.isEmpty() && !items.isEmpty()) {
            boar.getNavigation().moveTo(items.get(0), 1.1F);
        }
    }

    @Override
    public void start() {
        List<ItemEntity> items = boar.level().getEntitiesOfClass(ItemEntity.class, boar.getBoundingBox().inflate(8.0, 8.0, 8.0), allowedItems);
        if (!items.isEmpty()) {
            boar.getNavigation().moveTo(items.get(0), 1.1F);
        }
    }

    @Override
    public void stop() {
        super.stop();
    }
}
