package com.teamremastered.endrem.utils;

import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.item.SerializedEye;
import com.teamremastered.endrem.util.EyeDataManager;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

public class VanillaLootInjector {

    public static void init(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.addListener(VanillaLootInjector::resourceReloadListener);
    }

    public static void resourceReloadListener(AddReloadListenerEvent event) {
        EyeDataManager eyeDataManager = EyeDataManager.getInstance();

        for (var entry : eyeDataManager.getLoadedEyes().entrySet()) {
            for (ResourceLocation loot : entry.getValue().lootTablesID()) {
                ResourceKey<LootTable> resourceKey = ResourceKey.create(Registries.LOOT_TABLE, loot);
                LootTable injectTable = event.getServerResources().fullRegistries().getLootTable(ResourceKey.create(Registries.LOOT_TABLE, entry.getValue().poolID()));
                LootTable targetTable = event.getServerResources().fullRegistries().getLootTable(resourceKey);
                try {
                    if (targetTable != LootTable.EMPTY && injectTable != LootTable.EMPTY) {
                        event.getServerResources().fullRegistries().getLootTable(resourceKey).addPool(injectTable.getPool("eye_pool"));
                    }
                    else if (targetTable == LootTable.EMPTY && injectTable == LootTable.EMPTY){
                        Constants.LOGGER.warn("The target and injected loot tables provided by \"{}\" are invalid.", entry.getKey());
                    }
                    else if (targetTable == LootTable.EMPTY){
                        Constants.LOGGER.warn("The target loot table provided by \"{}\" is invalid.", entry.getKey());
                    }
                    else if (injectTable == LootTable.EMPTY){
                        Constants.LOGGER.warn("The injected loot table provided by \"{}\" is invalid.", entry.getKey());
                    }
                } catch (Exception e) {
                    Constants.LOGGER.error("Could not find the \"eye_pool\" inside the Loot Table located in: " + entry.getKey());
                }
            }
        }
    }
}