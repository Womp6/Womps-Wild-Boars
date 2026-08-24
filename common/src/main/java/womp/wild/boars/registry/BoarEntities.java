package womp.wild.boars.registry;

import java.util.function.Supplier;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import womp.wild.boars.BoarsCommon;
import womp.wild.boars.entity.Boar;
import womp.wild.boars.platform.Services;

public class BoarEntities {

    public static final Supplier<EntityType<Boar>> WILD_BOAR = register("wild_boar", MobCategory.CREATURE, Boar::new, 0.9F, 1.3F, 15f / 16);

    private static <T extends Mob> Supplier<EntityType<T>> register(String name, MobCategory group, EntityType.EntityFactory<T> factory, float width, float height, float eyeHeight) {
        return Services.PLATFORM.registerEntity(name, () -> EntityType.Builder.of(factory, group).sized(width, height).eyeHeight(eyeHeight).build(BoarsCommon.createKey(name, Registries.ENTITY_TYPE)));
    }

    public static void registerEntities() {}
}
