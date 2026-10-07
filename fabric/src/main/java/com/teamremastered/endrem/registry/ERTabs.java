package com.teamremastered.endrem.registry;

import com.teamremastered.endrem.EndRemasteredCommon;
import com.teamremastered.endrem.util.TabLoader;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ERTabs {

    public static final ResourceKey<CreativeModeTab> ITEM_GROUP = ResourceKey.create(Registries.CREATIVE_MODE_TAB, EndRemasteredCommon.ModResourceLocation("endrem_tab"));

    public static void init() {

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ITEM_GROUP, FabricItemGroup.builder()
                .title(Component.translatable("itemGroup.endrem.endrem_tab"))
                .icon(() -> new ItemStack(CommonItemRegistry.DUMMY_EYE))
                .displayItems((enabledFeatures, entries) -> {
                        entries.acceptAll(TabLoader.populateEndremTab());
                }).build());
    }
}