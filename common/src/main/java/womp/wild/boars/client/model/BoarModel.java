package womp.wild.boars.client.model;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import womp.wild.boars.client.animations.BoarAnimations;
import womp.wild.boars.client.states.BoarRenderState;

public abstract class BoarModel extends EntityModel<BoarRenderState> {

    protected final ModelPart head;
	protected final ModelPart fr_leg;
	protected final ModelPart fl_leg;
	protected final ModelPart br_leg;
	protected final ModelPart bl_leg;
	protected final ModelPart body;
	protected final ModelPart tail;
	protected final ModelPart rightEar;
	protected final ModelPart leftEar;
    protected ModelPart rightTusk;
	protected ModelPart leftTusk;

	protected KeyframeAnimation idleAnimation;
	protected final KeyframeAnimation scratchRightEarAnimation;
	protected final KeyframeAnimation scratchLeftEarAnimation;
	protected KeyframeAnimation runAnimation;
	protected final KeyframeAnimation sniffAnimation;
	protected final KeyframeAnimation attackAnimation;

    protected BoarModel(ModelPart root) {
        super(root);

        this.body = root.getChild("body");
		this.head = body.getChild("head");
		this.fr_leg = root.getChild("fr_leg");
		this.fl_leg = root.getChild("fl_leg");
		this.br_leg = root.getChild("br_leg");
		this.bl_leg = root.getChild("bl_leg");
		this.tail = body.getChild("tail");
		this.rightEar = head.getChild("ear1");
		this.leftEar = head.getChild("ear2");

        this.idleAnimation = BoarAnimations.IDLE.bake(root);
		this.scratchRightEarAnimation = BoarAnimations.SCRATCH_RIGHT_EAR.bake(root);
		this.scratchLeftEarAnimation = BoarAnimations.SCRATCH_LEFT_EAR.bake(root);
		this.runAnimation = BoarAnimations.RUN.bake(root);
		this.sniffAnimation = BoarAnimations.SNIFF.bake(root);
		this.attackAnimation = BoarAnimations.ATTACK.bake(root);
    }

    @Override
	public void setupAnim(BoarRenderState boarRenderState) {
		super.setupAnim(boarRenderState);
		this.head.xRot = boarRenderState.xRot * (float) (Math.PI / 180.0) * 0.7f;
		this.head.yRot = boarRenderState.yRot * (float) (Math.PI / 180.0) * 0.7f;
		float animationPos = boarRenderState.walkAnimationPos;
		float animationSpeed = boarRenderState.walkAnimationSpeed;
		if (animationSpeed < 0.75) {
			if (boarRenderState.isAttacking) this.runAnimation.applyWalk(animationPos, animationSpeed, boarRenderState.isBaby ? 1f : 1.1f, boarRenderState.isBaby ? 1f : 1.2f);
			else {
				this.br_leg.xRot = Mth.cos(animationPos * 0.6662F) * 1.4F * animationSpeed;
				this.bl_leg.xRot = Mth.cos(animationPos * 0.6662F + (float) Math.PI) * 1.4F * animationSpeed;
				this.fr_leg.xRot = Mth.cos(animationPos * 0.6662F + (float) Math.PI) * 1.4F * animationSpeed;
				this.fl_leg.xRot = Mth.cos(animationPos * 0.6662F) * 1.4F * animationSpeed;
				if (animationSpeed > 0.01f) {
					this.tail.xRot = (float)(110 * Math.PI / 180.0);
					this.tail.zRot = (float)(5 * Math.PI / 18) * Mth.sin(animationPos * 0.6662f) * animationSpeed * 1.2f;
					this.leftEar.xRot = (float)(8 * Math.PI / 180) * Mth.sin(animationPos * 0.6662f) * animationSpeed * 2f;
					this.rightEar.xRot = (float)(8 * Math.PI / 180) * Mth.sin(animationPos * 0.6662f + (float)Math.PI) * animationSpeed * 2f;
					this.body.y += boarRenderState.isBaby ? -0.2f * Mth.cos(animationPos * 0.6662f * 0.7f + (float)(Math.PI / 3)) + 0.1f : -0.4f * Mth.cos(animationPos * 0.6662f * 0.7f + (float)(Math.PI / 3)) + 0.2f;
					this.body.xRot = boarRenderState.isBaby ? (float)(Math.PI / 90) * Mth.sin(animationPos * 0.4f) : (float)(Math.PI / 90) * Mth.sin(animationPos * 0.6662f);
					this.head.xRot += (float)(5 * Math.PI / 180);
				}
			}
		} else {
			this.runAnimation.applyWalk(animationPos, animationSpeed, boarRenderState.isBaby ? 1f : 1.1f, boarRenderState.isBaby ? 1f : 1.2f);
		}
		this.idleAnimation.apply(boarRenderState.idleAnimationState, boarRenderState.ageInTicks, 1f);
		this.scratchRightEarAnimation.apply(boarRenderState.scratchRightEarAnimationState, boarRenderState.ageInTicks, 1f);
		this.scratchLeftEarAnimation.apply(boarRenderState.scratchLeftEarAnimationState, boarRenderState.ageInTicks, 1f);
		this.sniffAnimation.apply(boarRenderState.sniffAnimationState, boarRenderState.ageInTicks, 1f);
		this.attackAnimation.apply(boarRenderState.attackAnimation, boarRenderState.ageInTicks, 1f);
	}  
}
