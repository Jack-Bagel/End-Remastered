package com.teamremastered.endrem;

import com.teamremastered.endrem.config.ConfigOptions;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;

import static com.teamremastered.endrem.registry.CommonRegistryKey.EYE_DATA;

public class EndRemasteredCommon {

    public static ResourceLocation ModResourceLocation(String id) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, id);
    }

    public static void init() {
        try {
            ConfigOptions.create();
            Constants.LOGGER.info("End Remastered config loaded with success");

        } catch (IOException e) {
            Constants.LOGGER.error("Something went wrong with the config");
        }
    }
}