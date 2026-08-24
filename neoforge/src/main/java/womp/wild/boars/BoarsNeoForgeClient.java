package womp.wild.boars;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import womp.wild.boars.client.BoarsClient;
import womp.wild.boars.client.model.BabyBoarModel;
import womp.wild.boars.client.model.BoarWithTusksModel;
import womp.wild.boars.client.renderer.BoarRenderer;
import womp.wild.boars.registry.BoarEntities;

@EventBusSubscriber(modid = BoarsCommon.MOD_ID, value = Dist.CLIENT)
public class BoarsNeoForgeClient {
    
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BoarsClient.BOAR_MODEL, BoarWithTusksModel::createBodyLayer);
        event.registerLayerDefinition(BoarsClient.BABY_BOAR_MODEL, BabyBoarModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(BoarEntities.WILD_BOAR.get(), BoarRenderer::new);
    }
}
