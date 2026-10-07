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
import java.util.Map;

public class EyeDataManager {
    private static final EyeDataManager INSTANCE = new EyeDataManager();
    private final ArrayList<SerializedEye> loadedEyes = new ArrayList<>();

    private EyeDataManager() {}

    public void loadEyes(ResourceManager manager) {
        final FileToIdConverter FILE_CONVERTER = FileToIdConverter.json("eyes");
        this.loadedEyes.clear();

        Map<ResourceLocation, Resource> resources = FILE_CONVERTER.listMatchingResources(manager);

        for (Map.Entry<ResourceLocation, Resource> entry : resources.entrySet()) {
            ResourceLocation filePath = entry.getKey();

            try (Reader reader = entry.getValue().openAsReader()) {
                JsonElement json = JsonParser.parseReader(reader);

                // Use the Codec to validate and deserialize the JSON
                SerializedEye.CODEC.parse(JsonOps.INSTANCE, json)
                        .resultOrPartial(error -> Constants.LOGGER.error("Failed to parse eye file at" + filePath + ": " + error))
                        .ifPresent(this.loadedEyes::add);

            } catch (Exception e) {
                Constants.LOGGER.error("Error reading eye file " + filePath + ": " + e.getMessage());
            }
        }
    }

    public ArrayList<SerializedEye> getLoadedEyes() {
        return this.loadedEyes;
    }

    public static EyeDataManager getInstance() {
        return INSTANCE;
    }
}
