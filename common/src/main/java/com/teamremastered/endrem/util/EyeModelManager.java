package com.teamremastered.endrem.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EyeModelManager {
    public static final String TEXTURE_PATH_PREFIX = "models/eye";
    public static final String MODEL_PATH_PREFIX = "eye/";

    public static List<ResourceLocation> discoverCustomTextures(ResourceManager manager) {
        List<ResourceLocation> discoveredModels = new ArrayList<>();
        Map<ResourceLocation, Resource> resources = manager.listResources(
                TEXTURE_PATH_PREFIX,
                in_loc -> in_loc.getPath().endsWith(".json")
        );

        for (ResourceLocation rawLoc : resources.keySet()) {
            String path = MODEL_PATH_PREFIX + rawLoc.getPath().substring(11, rawLoc.getPath().length() - 5); // Strip away the "models/" prefix and ".json" suffix
            ResourceLocation modelLoc = ResourceLocation.fromNamespaceAndPath(rawLoc.getNamespace(), path);
            discoveredModels.add(modelLoc);
        }
        return discoveredModels;
    }
}