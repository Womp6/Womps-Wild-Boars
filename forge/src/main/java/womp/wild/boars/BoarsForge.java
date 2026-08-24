package womp.wild.boars;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import womp.wild.boars.platform.ForgePlatformHelper;

@Mod(BoarsCommon.MOD_ID)
public class BoarsForge {

    public BoarsForge(FMLJavaModLoadingContext context) {
        var modEventBus = context.getModBusGroup();
        BoarsCommon.init();
        ForgePlatformHelper.register(modEventBus);
    }
}