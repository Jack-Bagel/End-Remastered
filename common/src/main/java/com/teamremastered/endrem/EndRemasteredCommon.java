package com.teamremastered.endrem;

import com.teamremastered.endrem.config.ConfigOptions;
import com.teamremastered.endrem.item.JsonEye;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class EndRemasteredCommon {

    public static ResourceLocation ModResourceLocation(String id) {
        return ResourceLocation.fromNamespaceAndPath("endrem", id);
    }

    public static void init() {
        try {
            ConfigOptions.create();
            Constants.LOGGER.info("End Remastered config loaded with success");

            JsonEye.create();
            Constants.LOGGER.info("End Remastered eyes loaded with success");

        } catch (IOException e) {
            Constants.LOGGER.error("Something went wrong with the config");
        }
    }
}