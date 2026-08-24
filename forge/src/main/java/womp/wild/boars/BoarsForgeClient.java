package womp.wild.boars;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import womp.wild.boars.client.BoarsClient;
import womp.wild.boars.client.model.BabyBoarModel;
import womp.wild.boars.client.model.BoarWithTusksModel;
import womp.wild.boars.client.renderer.BoarRenderer;
import womp.wild.boars.registry.BoarEntities;

@Mod.EventBusSubscriber(modid = BoarsCommon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.BOTH, value = Dist.CLIENT)
public class BoarsForgeClient {
    
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
