package womp.wild.boars.registry;

import java.util.function.Supplier;

import net.minecraft.sounds.SoundEvent;
import womp.wild.boars.BoarsCommon;
import womp.wild.boars.platform.Services;

public class BoarSounds {

    public static final Supplier<SoundEvent> BOAR_AMBIENT = register("entity.wild_boar.ambient");
    public static final Supplier<SoundEvent> BOAR_HURT = register("entity.wild_boar.hurt");
    public static final Supplier<SoundEvent> BOAR_DEATH = register("entity.wild_boar.death");
    public static final Supplier<SoundEvent> BOAR_WARN = register("entity.wild_boar.warn");
    public static final Supplier<SoundEvent> BOAR_SNIFF = register("entity.wild_boar.sniff");
    public static final Supplier<SoundEvent> BOAR_STEP = register("entity.wild_boar.step");
    
    public static Supplier<SoundEvent> register(String subtitle) {
        return Services.PLATFORM.registerSound(subtitle, () -> SoundEvent.createVariableRangeEvent(BoarsCommon.id(subtitle)));
    }

    public static void registerSounds() {}
}
