package com.teamremastered.endrem.registry;

import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.client.EndPortalFrameRenderer;
import com.teamremastered.endrem.client.EyeDynamicBakedModel;
import com.teamremastered.endrem.client.EyeItemOverrides;
import com.teamremastered.endrem.client.EyeModel;
import com.teamremastered.endrem.util.EyeModelManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterHandlerClient {

    public static void init(IEventBus modEventBus) {
        modEventBus.addListener(RegisterHandlerClient::registerBlockEntityRenderer);
        modEventBus.addListener(RegisterHandlerClient::registerLayerDefinition);
        modEventBus.addListener(RegisterHandlerClient::registerAdditionalModels);
        modEventBus.addListener(RegisterHandlerClient::modifyBakingResult);
    }

    private static void registerBlockEntityRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(CommonBlockRegistry.END_PORTAL_FRAME_BLOCK_ENTITY, EndPortalFrameRenderer::new);
    }

    private static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CommonModelRegistry.EYE, EyeModel::createBodyLayer);
    }

    public static void registerAdditionalModels(ModelEvent.RegisterAdditional event) {
        ResourceManager manager = Minecraft.getInstance().getResourceManager();
        List<ResourceLocation> extraModels = EyeModelManager.discoverCustomTextures(manager);
        extraModels.forEach( extraModel -> event.register(ModelResourceLocation.standalone(extraModel)));
    }


    public static void modifyBakingResult(ModelEvent.ModifyBakingResult event) {
        Map<ResourceLocation, BakedModel> eyeModelCache = new HashMap<>();

        // Cache eye models — NeoForge gives you the full map directly
        event.getModels().forEach((mrl, model) -> {
            if (mrl.getVariant().equals(ModelResourceLocation.STANDALONE_VARIANT) &&
                    mrl.id().getPath().startsWith("eye/")) {
                eyeModelCache.put(mrl.id(), model);
            }
        });

        // Replace dummy_eye inventory model
        ModelResourceLocation inventoryMRL = new ModelResourceLocation(
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "dummy_eye"), "inventory"
        );

        BakedModel original = event.getModels().get(inventoryMRL);
        if (original != null) {
            event.getModels().put(inventoryMRL,
                    new EyeDynamicBakedModel(original, new EyeItemOverrides(original, eyeModelCache::get)));
        }
    }
}
