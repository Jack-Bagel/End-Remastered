package com.teamremastered.endrem.util;

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
    public static List<ItemStack> populateEndremTab(MinecraftServer server) {
        List<ItemStack> displayedItems = new ArrayList<>();
        ResourceManager manager = server.getResourceManager();
        manager.listResources("eyes", path -> path.getPath().endsWith(".json"))
                .forEach((location, resource) -> {
                    String itemID = location.getPath().split("/")[1].split("\\.")[0];
                    ResourceLocation eyeId = ResourceLocation.fromNamespaceAndPath(location.getNamespace(), itemID);
                    ItemStack stack = new ItemStack(BuiltInRegistries.ITEM.get(
                            EndRemasteredCommon.ModResourceLocation("dummy_eye")));
                    stack.set(CommonDataComponentRegistry.DATA_EYE_COMPONENT, new EyeDataComponent(eyeId));

                    displayedItems.add(stack);
                });

        return displayedItems;
    }
}
