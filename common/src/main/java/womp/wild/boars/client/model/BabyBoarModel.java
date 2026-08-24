package womp.wild.boars.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import womp.wild.boars.client.animations.BoarAnimations;

public class BabyBoarModel extends BoarModel {

	public BabyBoarModel(ModelPart root) {
		super(root);

		this.idleAnimation = BoarAnimations.BABY_IDLE.bake(root);
		this.runAnimation = BoarAnimations.BABY_RUN.bake(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("fr_leg", CubeListBuilder.create().texOffs(0, 41).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.25F, 19.0F, -2.5F));

		partdefinition.addOrReplaceChild("fl_leg", CubeListBuilder.create().texOffs(12, 41).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.25F, 19.0F, -2.5F));

		partdefinition.addOrReplaceChild("br_leg", CubeListBuilder.create().texOffs(42, 0).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.25F, 19.0F, 5.5F));

		partdefinition.addOrReplaceChild("bl_leg", CubeListBuilder.create().texOffs(42, 9).addBox(-1.75F, -1.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 19.0F, 5.5F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -3.0F, -10.0F, 8.0F, 6.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(40, 19).addBox(-4.0F, -2.0F, 3.0F, 8.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 19).addBox(-4.0F, -4.0F, -10.0F, 8.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(0, 32).addBox(-3.0F, -5.0F, -9.0F, 6.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 5.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(28, 32).addBox(-3.5F, -4.5F, -4.0F, 7.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(40, 25).addBox(-2.5F, -1.5F, -6.0F, 5.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -10.0F));

		PartDefinition snout_tip = head.addOrReplaceChild("snout_tip", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.75F, -4.0F, 0.1309F, 0.0F, 0.0F));

		snout_tip.addOrReplaceChild("snout2_r1", CubeListBuilder.create().texOffs(24, 44).addBox(1.5F, -2.75F, -2.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 0.8042F, -2.4872F, 0.0436F, 0.0F, 0.0F));

		PartDefinition ear2 = head.addOrReplaceChild("ear2", CubeListBuilder.create(), PartPose.offsetAndRotation(2.9703F, -3.7747F, -1.7292F, 0.0873F, 0.0F, 0.1309F));

		ear2.addOrReplaceChild("ear22_r1", CubeListBuilder.create().texOffs(46, 48).addBox(0.5453F, 1.13F, -1.2676F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 44).addBox(0.0453F, 2.13F, -1.2676F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0086F, -4.13F, 1.0114F, 0.0F, 0.2618F, 0.0F));

		PartDefinition ear1 = head.addOrReplaceChild("ear1", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.9703F, -3.7747F, -1.7292F, 0.0873F, 0.0F, -0.1309F));

		ear1.addOrReplaceChild("ear12_r1", CubeListBuilder.create().texOffs(40, 48).addBox(-2.5F, 1.0F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(44, 44).addBox(-3.0F, 2.0F, -1.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.9492F, -4.13F, 1.0114F, 0.0F, -0.2618F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 3.5F));

		tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(36, 48).addBox(-0.5F, 1.8505F, 0.241F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.75F, -1.25F, 0.3054F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}