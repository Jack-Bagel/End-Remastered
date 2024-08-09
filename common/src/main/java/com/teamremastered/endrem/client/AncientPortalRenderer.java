package com.teamremastered.endrem.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.EndRemasteredCommon;
import com.teamremastered.endrem.block.AncientPortalFrameEntity;
import com.teamremastered.endrem.registry.CommonModelRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.joml.Matrix4f;

import java.util.Properties;

public class AncientPortalRenderer implements BlockEntityRenderer<AncientPortalFrameEntity> {
    private final EyeModel eyeModel;

    public AncientPortalRenderer(BlockEntityRendererProvider.Context ctx) {
        this.eyeModel = new EyeModel(ctx.bakeLayer(CommonModelRegistry.EYE));
    }

    private float rotateEye(Direction facing) {
        double rotation = switch (facing.getSerializedName()) {
            case "south" -> Math.PI;
            case "east" -> -Math.PI / 2;
            case "west" -> Math.PI / 2;
            default -> 0;
        };

        return (float) rotation;
    }

    @Override
    public void render(AncientPortalFrameEntity ancientPortalFrameEntity, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int combinedLight, int combinedOverlay) {
        if (!ancientPortalFrameEntity.isEmpty()) {
            Direction FACING = ancientPortalFrameEntity.getBlockState().getValue(HorizontalDirectionalBlock.FACING);

            poseStack.pushPose();
            poseStack.mulPose(new Matrix4f().translate(0.5f, 0.0f, 0.5f));
            poseStack.mulPose(new Matrix4f().rotateY(rotateEye(FACING)));
            ancientPortalFrameEntity.eyeTexture = new Material(TextureAtlas.LOCATION_BLOCKS, EndRemasteredCommon.ModResourceLocation("block/eyes/" + ancientPortalFrameEntity.getEye()));
            ancientPortalFrameEntity.setChanged();
            VertexConsumer vertexconsumer = ancientPortalFrameEntity.eyeTexture.buffer(multiBufferSource, RenderType::entitySolid);
            this.eyeModel.render(poseStack, vertexconsumer, combinedLight, combinedOverlay, -1);
            poseStack.popPose();
        }
    }
}