package womp.wild.boars.platform;

import womp.wild.boars.BoarsCommon;
import womp.wild.boars.platform.services.IPlatformHelper;

import java.util.function.Supplier;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public Supplier<Item> registerItem(String name, Supplier<Item> item) {
        Item toReturn = Registry.register(BuiltInRegistries.ITEM, BoarsCommon.id(name), item.get());
        return () -> toReturn;
    }

    @Override
    public <T extends Mob> Supplier<EntityType<T>> registerEntity(String name, Supplier<EntityType<T>> entityType) {
        EntityType<T> toReturn = Registry.register(BuiltInRegistries.ENTITY_TYPE, BoarsCommon.id(name), entityType.get());
        return () -> toReturn;
    }

    @Override
    public Supplier<SoundEvent> registerSound(String subtitle, Supplier<SoundEvent> soundEvent) {
        SoundEvent toReturn = Registry.register(BuiltInRegistries.SOUND_EVENT, BoarsCommon.id(subtitle), soundEvent.get());
        return () -> toReturn;
    }
}
