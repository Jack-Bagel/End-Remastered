package com.teamremastered.endrem.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;

public class EyeModel extends Model {
    private final ModelPart eye;
    public EyeModel(ModelPart root) {
        super(RenderType::entitySolid);
        eye = root.getChild("eye");
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int combinedLight, int combinedOverlay, int i2) {
        this.render(poseStack, vertexConsumer, combinedLight, combinedOverlay, i2);
    }

    public void render(PoseStack poseStack, VertexConsumer vertexConsumer, int combinedLight, int combinedOverlay, int p_350753_) {
        this.eye.render(poseStack, vertexConsumer, combinedLight, combinedOverlay, p_350753_);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("eye", CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, -3.0F, 8.0F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(12.0F, 16.0F, -12.0F));
        //PartPose.offset(20.0F, 16.0F, -4.0F))
        // 32 and 11 are the texture size
        return LayerDefinition.create(meshdefinition, 32, 11);
    }
}