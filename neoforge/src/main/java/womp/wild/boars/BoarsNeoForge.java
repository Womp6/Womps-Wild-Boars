package womp.wild.boars;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import womp.wild.boars.platform.NeoForgePlatformHelper;

@Mod(BoarsCommon.MOD_ID)
public class BoarsNeoForge {

    public BoarsNeoForge(IEventBus eventBus) {
        BoarsCommon.init();
        NeoForgePlatformHelper.register(eventBus);
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }
}