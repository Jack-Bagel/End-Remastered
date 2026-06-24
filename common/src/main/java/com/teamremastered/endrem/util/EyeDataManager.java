package com.teamremastered.endrem.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.item.SerializedEye;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EyeDataManager {
    private static final EyeDataManager INSTANCE = new EyeDataManager();
    private final Map<ResourceLocation, SerializedEye> loadedEyes = new HashMap<>();

    private EyeDataManager() {};

    public void loadEyes(ResourceManager manager) {
        final FileToIdConverter FILE_CONVERTER = FileToIdConverter.json("eyes");
        this.loadedEyes.clear();

        Map<ResourceLocation, Resource> resources = FILE_CONVERTER.listMatchingResources(manager);

        for (Map.Entry<ResourceLocation, Resource> entry : resources.entrySet()) {
            ResourceLocation filePath = entry.getKey();
            ResourceLocation fileID = FILE_CONVERTER.fileToId(filePath);

            try (Reader reader = entry.getValue().openAsReader()) {
                // Read the file into a generic JsonElement
                JsonElement json = JsonParser.parseReader(reader);

                // Use the Codec to validate and deserialize the JSON into your Record
                SerializedEye.CODEC.parse(JsonOps.INSTANCE, json)
                        .resultOrPartial(error -> Constants.LOGGER.error("Failed to parse eye file " + fileID + ": " + error))
                        .ifPresent(serializedEye -> this.loadedEyes.put(fileID, serializedEye));

            } catch (Exception e) {
                Constants.LOGGER.error("Error reading eye file " + filePath + ": " + e.getMessage());
            }
        }
    }

    public Map<ResourceLocation, SerializedEye> getLoadedEyes() {
        return this.loadedEyes;
    }

    public static EyeDataManager getInstance() {
        return INSTANCE;
    }
}
