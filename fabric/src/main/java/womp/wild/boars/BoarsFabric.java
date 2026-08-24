package womp.wild.boars;

import net.fabricmc.api.ModInitializer;

public class BoarsFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        BoarsCommon.init();
        BoarsFabricEvents.register();
    }
}
