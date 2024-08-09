package com.teamremastered.endrem.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamremastered.endrem.EndRemasteredCommon;
import com.teamremastered.endrem.block.AncientPortalFrameEntity;
import com.teamremastered.endrem.registry.CommonModelRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;

public class AncientPortalRenderer implements BlockEntityRenderer<AncientPortalFrameEntity> {
    private final EyeModel eyeModel;

    public AncientPortalRenderer(BlockEntityRendererProvider.Context ctx) {
        this.eyeModel = new EyeModel(ctx.bakeLayer(CommonModelRegistry.EYE));
    }

    @Override
    public void render(AncientPortalFrameEntity ancientPortalFrameEntity, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int combinedLight, int combinedOverlay) {
        //TODO: Change eye orientation based on frame direction
        String eye = ancientPortalFrameEntity.getEye();
        if (!eye.equals("empty")) {
            poseStack.pushPose();
            ancientPortalFrameEntity.eyeTexture = new Material(TextureAtlas.LOCATION_BLOCKS, EndRemasteredCommon.ModResourceLocation("block/eyes/" + eye));
            ancientPortalFrameEntity.setChanged();
            VertexConsumer vertexconsumer = ancientPortalFrameEntity.eyeTexture.buffer(multiBufferSource, RenderType::entitySolid);
            this.eyeModel.render(poseStack, vertexconsumer, combinedLight, combinedOverlay, -1);
            poseStack.popPose();
        }
    }
}