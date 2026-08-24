package womp.wild.boars;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;
import womp.wild.boars.client.BoarsClient;
import womp.wild.boars.client.model.BabyBoarModel;
import womp.wild.boars.client.model.BoarWithTusksModel;
import womp.wild.boars.client.renderer.BoarRenderer;
import womp.wild.boars.registry.BoarEntities;

public class BoarsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRenderers.register(BoarEntities.WILD_BOAR.get(), BoarRenderer::new);

        ModelLayerRegistry.registerModelLayer(BoarsClient.BOAR_MODEL, BoarWithTusksModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(BoarsClient.BABY_BOAR_MODEL, BabyBoarModel::createBodyLayer);
    }
}
