package com.teamremastered.endrem.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.teamremastered.endrem.EndRemasteredCommon;
import com.teamremastered.endrem.block.AncientPortalFrameEntity;
import com.teamremastered.endrem.registry.CommonModelRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class AncientPortalRenderer implements BlockEntityRenderer<AncientPortalFrameEntity, AncientPortalState> {
    private final EyeModel eyeModel;
    private final MaterialSet materials;


    public AncientPortalRenderer(BlockEntityRendererProvider.Context ctx) {
        this.materials = ctx.materials();
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
    public AncientPortalState createRenderState() {
        return new AncientPortalState();
    }

    @Override
    public void extractRenderState(AncientPortalFrameEntity  blockEntity, AncientPortalState  renderState, float partialTick, Vec3 cameraPosition, ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderState.extractBase(blockEntity, renderState, breakProgress);

        renderState.eye = blockEntity.getEye();
        renderState.facing = blockEntity.getBlockState().getValue(HorizontalDirectionalBlock.FACING);
    }

    @Override
    public void submit(AncientPortalState state, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        if (state.eye.equals("empty")) {
            return;
        }
        Material eyeTexture = Sheets.BLOCKS_MAPPER.apply(EndRemasteredCommon.ModResourceLocation("eyes/" + state.eye));

        poseStack.pushPose();
        poseStack.mulPose(new Matrix4f().translate(0.5f, 0.0f, 0.5f));
        poseStack.mulPose(new Matrix4f().rotateY(rotateEye(state.facing)));

        nodeCollector.submitModel(
                this.eyeModel,
                null,
                poseStack,
                eyeTexture.renderType(RenderType::entitySolid),
                state.lightCoords,
                OverlayTexture.NO_OVERLAY,
                -1,
                this.materials.get(eyeTexture),
                0,
                state.breakProgress
        );
        poseStack.popPose();
    }
}