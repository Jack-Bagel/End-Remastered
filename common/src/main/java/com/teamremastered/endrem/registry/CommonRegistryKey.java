package com.teamremastered.endrem.registry;

import com.teamremastered.endrem.EndRemasteredCommon;
import com.teamremastered.endrem.item.EyeData;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class CommonRegistryKey {

    public static final ResourceKey<Registry<EyeData>> EYE_DATA =
            ResourceKey.createRegistryKey(
                    EndRemasteredCommon.ModResourceLocation("eyes")
            );
}
