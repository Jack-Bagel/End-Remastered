package com.teamremastered.endrem.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamremastered.endrem.EndRemasteredCommon;
import com.teamremastered.endrem.block.EndPortalFrameBlockEntity;
import com.teamremastered.endrem.registry.CommonModelRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import org.joml.Matrix4f;

public class EndPortalFrameRenderer implements BlockEntityRenderer<EndPortalFrameBlockEntity> {
    private final EyeModel eyeModel;
    public static Material EYE_TEXTURE;
    public EndPortalFrameRenderer(BlockEntityRendererProvider.Context ctx) {
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
    public void render(EndPortalFrameBlockEntity endPortalFrameBlockEntity, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int combinedLight, int combinedOverlay) {
        String eye = endPortalFrameBlockEntity.getEyeIdentificator().getPath();

        if (eye.equals("empty")) {
            return;
        }
        Direction FACING = endPortalFrameBlockEntity.getBlockState().getValue(HorizontalDirectionalBlock.FACING);
        poseStack.pushPose();

        poseStack.mulPose(new Matrix4f().translate(0.5f, 0.0f, 0.5f));
        poseStack.mulPose(new Matrix4f().rotateY(rotateEye(FACING)));

        EYE_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, EndRemasteredCommon.ModResourceLocation("block/eyes/" + eye));
        VertexConsumer vertexconsumer = EYE_TEXTURE.buffer(multiBufferSource, RenderType::entitySolid);
        this.eyeModel.render(poseStack, vertexconsumer, combinedLight, combinedOverlay, -1);

        poseStack.popPose();
    }
}