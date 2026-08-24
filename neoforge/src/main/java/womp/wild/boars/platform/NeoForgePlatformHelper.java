package womp.wild.boars.platform;

import womp.wild.boars.BoarsCommon;
import womp.wild.boars.platform.services.IPlatformHelper;

import java.util.function.Supplier;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.getCurrent().isProduction();
    }

    // Boars Registry

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(BoarsCommon.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.createEntities(BoarsCommon.MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, BoarsCommon.MOD_ID);

    @Override
    public Supplier<Item> registerItem(String name, Supplier<Item> item) {
        return ITEMS.register(name, item);
    }

    @Override
    public <T extends Mob> Supplier<EntityType<T>> registerEntity(String name, Supplier<EntityType<T>> entityType) {
        return ENTITIES.register(name, entityType);
    }

    @Override
    public Supplier<SoundEvent> registerSound(String subtitle, Supplier<SoundEvent> soundEvent) {
        return SOUNDS.register(subtitle, soundEvent);
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
        ENTITIES.register(bus);
        SOUNDS.register(bus);
    }
}