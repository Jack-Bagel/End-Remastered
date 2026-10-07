package com.teamremastered.endrem.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.teamremastered.endrem.EndRemasteredCommon;
import com.teamremastered.endrem.component.EyeDataComponent;
import com.teamremastered.endrem.registry.CommonDataComponentRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TabLoader {

    public static List<ItemStack> populateEndremTab() {
        List<ItemStack> displayedItems = new ArrayList<>();

        EyeDataManager.getInstance().getLoadedEyes().forEach((eye) -> {
            ItemStack stack = new ItemStack(BuiltInRegistries.ITEM.get(
                    EndRemasteredCommon.ModResourceLocation("dummy_eye")));
            stack.set(CommonDataComponentRegistry.DATA_EYE_COMPONENT, new EyeDataComponent(eye.id()));

            displayedItems.add(stack);
        });

        return displayedItems;
    }
}
