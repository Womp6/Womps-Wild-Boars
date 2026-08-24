package womp.wild.boars.client.model;

import net.minecraft.client.model.geom.ModelPart;

public class BoarNoTusksModel extends BoarWithTusksModel {

	public BoarNoTusksModel(ModelPart root) {
		super(root);
		this.rightTusk = head.getChild("snout_tip").getChild("rightTusk");
		this.leftTusk = head.getChild("snout_tip").getChild("leftTusk");
		this.leftTusk.visible = false;
        this.rightTusk.visible = false;
	}
}