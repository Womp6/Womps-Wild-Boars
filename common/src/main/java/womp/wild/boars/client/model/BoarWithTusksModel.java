package womp.wild.boars.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class BoarWithTusksModel extends BoarModel {

	public BoarWithTusksModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("fr_leg", CubeListBuilder.create().texOffs(0, 58).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.75F, 16.0F, -5.0F));

		partdefinition.addOrReplaceChild("fl_leg", CubeListBuilder.create().texOffs(74, 0).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.75F, 16.0F, -5.0F));

		partdefinition.addOrReplaceChild("br_leg", CubeListBuilder.create().texOffs(58, 11).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.75F, 16.0F, 9.0F));

		partdefinition.addOrReplaceChild("bl_leg", CubeListBuilder.create().texOffs(16, 58).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.75F, 16.0F, 9.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -6.0F, -10.0F, 10.0F, 9.0F, 19.0F, new CubeDeformation(0.0F))
		.texOffs(52, 36).addBox(-5.0F, -5.0F, 9.0F, 10.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(-5.0F, -7.0F, -10.0F, 10.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 45).addBox(-4.0F, -8.0F, -9.0F, 8.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(52, 28).addBox(-4.0F, -9.0F, -7.0F, 8.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.0F, 2.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -1.75F, 9.0F));
		
		tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -1.1495F, 0.241F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(30, 72).addBox(-4.0F, -4.0F, -5.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(50, 58).addBox(-3.0F, -1.0F, -7.0F, 6.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -10.0F));

		PartDefinition snout_tip = head.addOrReplaceChild("snout_tip", CubeListBuilder.create().texOffs(32, 58).addBox(-2.0F, -2.0F, -6.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(58, 24).addBox(-2.0F, -3.0F, -3.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.75F, -5.5F, 0.1309F, 0.0F, 0.0F));

		snout_tip.addOrReplaceChild("leftTusk", CubeListBuilder.create().texOffs(66, 45).addBox(-2.0F, -3.0F, -4.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.75F, 0.75F, -1.0F, 0.0F, 0.0F, 0.3927F));

		snout_tip.addOrReplaceChild("rightTusk", CubeListBuilder.create().texOffs(44, 66).addBox(1.0F, -3.0F, -4.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.75F, 0.75F, -1.0F, 0.0F, 0.0F, -0.3927F));

		PartDefinition ear2 = head.addOrReplaceChild("ear2", CubeListBuilder.create(), PartPose.offsetAndRotation(4.0F, -3.0F, -2.0F, 0.0873F, 0.0F, 0.1309F));

		ear2.addOrReplaceChild("ear22_r1", CubeListBuilder.create().texOffs(32, 66).addBox(1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(50, 65).addBox(0.0F, 1.0F, -1.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -4.0F, 1.0F, 0.0F, 0.2618F, 0.0F));

		PartDefinition ear1 = head.addOrReplaceChild("ear1", CubeListBuilder.create(), PartPose.offsetAndRotation(-4.0F, -3.0F, -2.0F, 0.0873F, 0.0F, -0.1309F));

		ear1.addOrReplaceChild("ear12_r1", CubeListBuilder.create().texOffs(38, 66).addBox(-3.0F, 0.0F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(60, 65).addBox(-4.0F, 1.0F, -1.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -4.0F, 1.0F, 0.0F, -0.2618F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}
}