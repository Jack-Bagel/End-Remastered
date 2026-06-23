package com.teamremastered.endrem.registry;

import com.teamremastered.endrem.EndRemasteredCommon;
import com.teamremastered.endrem.component.EyeDataComponent;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ERTabs {

    public static final ResourceKey<CreativeModeTab> ITEM_GROUP = ResourceKey.create(Registries.CREATIVE_MODE_TAB, EndRemasteredCommon.ModResourceLocation("endrem_tab"));
    private static MinecraftServer serverInstance = null;


    public static void init() {

        ServerLifecycleEvents.SERVER_STARTING.register((server) -> {
            serverInstance = server;
        });

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ITEM_GROUP, FabricItemGroup.builder()
                .title(Component.translatable("itemGroup.endrem.endrem_tab"))
                .icon(() -> new ItemStack(CommonItemRegistry.DUMMY_EYE))
                .displayItems((enabledFeatures, entries) -> {
                    if (serverInstance != null) {
                        entries.acceptAll(populateEndremTab(serverInstance));
                    }
                }).build());
    }

    private static List<ItemStack> populateEndremTab(MinecraftServer server) {
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