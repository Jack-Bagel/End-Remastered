package com.teamremastered.endrem.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.EndRemasteredCommon;
import com.teamremastered.endrem.component.EyeDataComponent;
import com.teamremastered.endrem.item.EyeData;
import com.teamremastered.endrem.registry.CommonDataComponentRegistry;
import com.teamremastered.endrem.registry.CommonItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.teamremastered.endrem.registry.CommonRegistryKey.EYE_DATA;

public class TabLoader {

    public static List<ItemStack> populateEndremTab(Optional<HolderLookup.RegistryLookup<EyeData>> registryLookup) {
        List<ItemStack> displayedItems = new ArrayList<>();
        EyeDataManager.getDynamicEyes(registryLookup).forEach(eye -> {
            ItemStack stack = new ItemStack(BuiltInRegistries.ITEM.get(
                    EndRemasteredCommon.ModResourceLocation("dummy_eye")));
            stack.set(CommonDataComponentRegistry.DATA_EYE_COMPONENT, new EyeDataComponent(eye.id()));
            displayedItems.add(stack);
        });

        displayedItems.add(new ItemStack(CommonItemRegistry.UNDEAD_SOUL));
        displayedItems.add(new ItemStack(CommonItemRegistry.WITCH_PUPIL));

        return displayedItems;
    }
}