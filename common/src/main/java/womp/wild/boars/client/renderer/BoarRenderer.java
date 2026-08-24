package womp.wild.boars.client.renderer;

import java.util.Map;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.AdultAndBabyModelPair;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import womp.wild.boars.BoarsCommon;
import womp.wild.boars.client.BoarsClient;
import womp.wild.boars.client.model.BabyBoarModel;
import womp.wild.boars.client.model.BoarModel;
import womp.wild.boars.client.model.BoarWithTusksModel;
import womp.wild.boars.client.model.BoarNoTusksModel;
import womp.wild.boars.client.states.BoarRenderState;
import womp.wild.boars.entity.Boar;
import womp.wild.boars.entity.Boar.BoarVariant;

public class BoarRenderer extends MobRenderer<Boar, BoarRenderState, BoarModel> {
    private final Map<BoarVariant, AdultAndBabyModelPair<BoarModel>> models;

    public BoarRenderer(Context context) {
        super(context, new BoarWithTusksModel(context.bakeLayer(BoarsClient.BOAR_MODEL)), 0.7f);
        models = bakeModels(context);
    }

    private static Map<BoarVariant, AdultAndBabyModelPair<BoarModel>> bakeModels(final EntityRendererProvider.Context context) {
		return Maps.newEnumMap(
			Map.of(
				BoarVariant.BLACK_BOAR,
				new AdultAndBabyModelPair<>(new BoarWithTusksModel(context.bakeLayer(BoarsClient.BOAR_MODEL)), new BabyBoarModel(context.bakeLayer(BoarsClient.BABY_BOAR_MODEL))),
				BoarVariant.BLACK_BOAR_NO_TUSKS,
				new AdultAndBabyModelPair<>(new BoarNoTusksModel(context.bakeLayer(BoarsClient.BOAR_MODEL)), new BabyBoarModel(context.bakeLayer(BoarsClient.BABY_BOAR_MODEL))),
                BoarVariant.BROWN_BOAR,
				new AdultAndBabyModelPair<>(new BoarWithTusksModel(context.bakeLayer(BoarsClient.BOAR_MODEL)), new BabyBoarModel(context.bakeLayer(BoarsClient.BABY_BOAR_MODEL))),
				BoarVariant.BROWN_BOAR_NO_TUSKS,
				new AdultAndBabyModelPair<>(new BoarNoTusksModel(context.bakeLayer(BoarsClient.BOAR_MODEL)), new BabyBoarModel(context.bakeLayer(BoarsClient.BABY_BOAR_MODEL)))
			)
		);
	}

    @Override
    public Identifier getTextureLocation(BoarRenderState state) {
        int variant = state.variant.getIndex();
        return state.isBaby ? BoarsCommon.id("textures/entity/wild_boar/baby_boar.png") : variant <= 1 ? BoarsCommon.id("textures/entity/wild_boar/black_boar.png") : BoarsCommon.id("textures/entity/wild_boar/brown_boar.png");
    }

    @Override
    protected float getShadowRadius(BoarRenderState state) {
        return state.isBaby ? 0.5f : super.getShadowRadius(state);
    }

    @Override
    public BoarRenderState createRenderState() {
        return new BoarRenderState();
    }

    @Override
    public void submit(BoarRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
            CameraRenderState camera) {
        if (state.variant != null) {
            this.model = this.models.get(state.variant).getModel(state.isBaby);
            super.submit(state, poseStack, submitNodeCollector, camera);
        }
    }
    
    @Override
    public void extractRenderState(Boar entity, BoarRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.isAttacking = entity.getTarget() != null;
        state.variant = entity.getVariant();
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.sniffAnimationState.copyFrom(entity.sniffAnimationState);
        state.scratchLeftEarAnimationState.copyFrom(entity.scratchLeftEarAnimationState);
        state.scratchRightEarAnimationState.copyFrom(entity.scratchRightEarAnimationState);
        state.attackAnimation.copyFrom(entity.attackAnimationState);
    }
}
