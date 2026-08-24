package womp.wild.boars.platform;

import womp.wild.boars.BoarsCommon;
import womp.wild.boars.platform.services.IPlatformHelper;

import java.util.function.Supplier;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    // Boars Registry

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BoarsCommon.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, BoarsCommon.MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, BoarsCommon.MOD_ID);

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

    public static void register(BusGroup bus) {
        ITEMS.register(bus);
        ENTITIES.register(bus);
        SOUNDS.register(bus);
    }
}