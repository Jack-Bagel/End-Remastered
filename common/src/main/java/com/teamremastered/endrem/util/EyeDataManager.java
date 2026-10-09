package com.teamremastered.endrem.util;

import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.EndRemasteredCommon;
import com.teamremastered.endrem.item.EyeData;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Optional;

public class EyeDataManager {

    public static ArrayList<EyeData> getDynamicEyes(Optional<HolderLookup.RegistryLookup<EyeData>> registryLookup) {
        ArrayList<EyeData> result = new ArrayList<>();
        registryLookup.ifPresent(lookup -> {
            lookup.listElements().forEach(eyeRef -> {
                result.add(eyeRef.value());
            });
        });

        if (registryLookup.isEmpty()) {
            Constants.LOGGER.error("Could not get the registry lookup for the EyeData. End Remastered won't be able to load the eyes");
        } else if (result.isEmpty()) {
            Constants.LOGGER.error("Something went wrong when loading the eyes.");
        }

        return result;
    }

    public static boolean isEyeLoaded(ResourceLocation eyeID, Optional<HolderLookup.RegistryLookup<EyeData>> lookup) {
        return EyeDataManager.getDynamicEyes(lookup).stream().anyMatch(eyeData ->
                eyeData.id().equals(eyeID));
    }
}