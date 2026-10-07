package com.teamremastered.endrem.util;

import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.EndRemasteredCommon;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;

public class LootInjection {

    public static void init() {

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            EyeDataManager eyeDataManager = EyeDataManager.getInstance();
            // Injected Eyes
            for (var entry : eyeDataManager.getLoadedEyes()) {
                for (ResourceLocation table : entry.lootTablesID()) {
                    if (table.equals(key.location())) {
                        LootPool.Builder poolBuilder = LootPool.lootPool().add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, entry.poolID())));

                        if (entry.poolID().equals(ResourceLocation.withDefaultNamespace("empty"))) {
                            Constants.LOGGER.warn("\"{}\" has no pool to use", entry.poolID());
                            continue;
                        }
                        else if (entry.lootTablesID().isEmpty()) {
                            Constants.LOGGER.warn("\"{}\" has no loot table to inject into", entry.poolID());
                            continue;
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