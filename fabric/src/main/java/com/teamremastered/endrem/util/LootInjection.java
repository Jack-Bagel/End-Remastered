package com.teamremastered.endrem.util;

import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.EndRemasteredCommon;
import com.teamremastered.endrem.item.SerializedEye;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;

public class LootInjection {

    public static void register() {

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            EyeDataManager eyeDataManager = EyeDataManager.getInstance();
            // Injected Eyes
            for (var entry : eyeDataManager.getLoadedEyes().entrySet()) {
                for (ResourceLocation table : entry.getValue().lootTablesID()) {
                    if (table.equals(key.location())) {
                        LootPool.Builder poolBuilder = LootPool.lootPool().add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, entry.getValue().poolID())));

                        if (entry.getValue().poolID().equals(ResourceLocation.withDefaultNamespace("empty"))) {
                            return;
                        }
                        else if (entry.getValue().lootTablesID().isEmpty()) {
                            Constants.LOGGER.warn("\"{}\" has no loot table to inject into", entry.getValue().poolID());
                            return;
                        }

                        tableBuilder.withPool(poolBuilder);
                    }
                }
            }

            // Hardcoded Injected Items
            if (ResourceLocation.withDefaultNamespace("entities/witch").equals(key.location())) {
                ResourceKey<LootTable> resourceKey = ResourceKey.create(Registries.LOOT_TABLE, EndRemasteredCommon.ModResourceLocation("minecraft/entities/witch"));
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .add(NestedLootTable.lootTableReference(resourceKey));
                tableBuilder.withPool(poolBuilder);

            } else if (ResourceLocation.withDefaultNamespace("entities/skeleton_horse").equals(key.location())) {
                ResourceKey<LootTable> resourceKey = ResourceKey.create(Registries.LOOT_TABLE, EndRemasteredCommon.ModResourceLocation("minecraft/entities/skeleton_horse"));
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .add(NestedLootTable.lootTableReference(resourceKey));
                tableBuilder.withPool(poolBuilder);
            }
        });

    }
}