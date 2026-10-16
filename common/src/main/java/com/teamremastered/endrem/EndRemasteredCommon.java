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
            ConfigOptions.create();
    }
}