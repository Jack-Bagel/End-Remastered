package com.teamremastered.endrem.registry;

import com.teamremastered.endrem.EndRemasteredCommon;
import com.teamremastered.endrem.Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class ERTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);
    private static MinecraftServer serverInstance = null;

    public static void init(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.addListener(ERTabs::getServerInstance);
        TABS.register(modEventBus);
    }

    public static final Supplier<CreativeModeTab> EYES_TAB = TABS.register("endrem_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.endrem.endrem_tab"))
                    .icon(() -> new ItemStack(CommonItemRegistry.EXOTIC_EYE))
                    .displayItems((featureFlags, entries) -> {
                        if (serverInstance != null) {
                            entries.acceptAll(populateEndremTab(serverInstance));
                        }
                    }).build());


    private static Set<ItemStack> populateEndremTab(MinecraftServer server) {
        Set<ItemStack> displayedItems = new HashSet<>();
        ResourceManager manager = server.getResourceManager();
        List<ResourceLocation> files = new ArrayList<>();
        manager.listResources("eyes", path -> path.getPath().endsWith(".json"))
                .forEach((location, resource) -> {
                    files.add(location);
                    String itemID = location.getPath().split("/")[1].split("\\.")[0];
                    displayedItems.add(new ItemStack(BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModResourceLocation(itemID))));
                });

        return displayedItems;
    }

    private static void getServerInstance(ServerStartingEvent event) {
        serverInstance = event.getServer();
    }
}