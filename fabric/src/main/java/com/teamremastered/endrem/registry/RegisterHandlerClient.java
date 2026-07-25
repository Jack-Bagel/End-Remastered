package com.teamremastered.endrem.registry;

import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.EndRemasteredCommon;
import com.teamremastered.endrem.client.EndPortalFrameRenderer;
import com.teamremastered.endrem.client.EyeDynamicBakedModel;
import com.teamremastered.endrem.client.EyeItemOverrides;
import com.teamremastered.endrem.client.EyeModel;
import com.teamremastered.endrem.util.EyeModelManager;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.impl.client.rendering.BlockEntityRendererRegistryImpl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterHandlerClient {

    public static void init() {
        EntityModelLayerRegistry.registerModelLayer(CommonModelRegistry.EYE, EyeModel::createBodyLayer);
        BlockEntityRendererRegistryImpl.register(CommonBlockRegistry.END_PORTAL_FRAME_BLOCK_ENTITY, EndPortalFrameRenderer::new);
        modifyEyeModelsAfterBake();
    }

    private static void modifyEyeModelsAfterBake() {
        ModelLoadingPlugin.register(pluginContext -> {
            ResourceManager manager = Minecraft.getInstance().getResourceManager();
            List<ResourceLocation> extraModels = EyeModelManager.discoverCustomTextures(manager);

            // Register eyes models to ensure they get baked
            extraModels.forEach(pluginContext::addModels);

            Map<ResourceLocation, BakedModel> eyeModelCache = new HashMap<>();
            pluginContext.modifyModelAfterBake().register((model, context) -> {

                if (context.resourceId() != null &&
                        context.resourceId().getPath().startsWith("eye/")) {
                    eyeModelCache.put(context.resourceId(), model);
                }

                ModelResourceLocation inventoryMRL = ModelResourceLocation.inventory(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "dummy_eye"));

                if (inventoryMRL.equals(context.topLevelId())) {
                    return new EyeDynamicBakedModel(model, new EyeItemOverrides(model, eyeModelCache::get));
                }

                return model;
            });
        });
    }
}