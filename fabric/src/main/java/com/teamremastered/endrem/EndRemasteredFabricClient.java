package com.teamremastered.endrem;

import com.teamremastered.endrem.client.EyeDynamicBakedModel;
import com.teamremastered.endrem.registry.RegisterHandler;
import com.teamremastered.endrem.util.EyeModelManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EndRemasteredFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        RegisterHandler.clientInit();

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
                    return new EyeDynamicBakedModel(model, eyeModelCache::get);
                }

                return model;
            });
        });
    }
}