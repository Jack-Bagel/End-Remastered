package com.teamremastered.endrem.util;

import com.teamremastered.endrem.EndRemasteredCommon;
import com.teamremastered.endrem.item.JsonEye;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;

public class LootInjection {

    public static void register() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, provider) -> {

            // Injected Eyes
            for (JsonEye eye : JsonEye.getEyes()) {
                for (Identifier table : eye.getLootTablesID()) {
                    if (table.equals(key.identifier())) {
                        LootPool.Builder poolBuilder = LootPool.lootPool()
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, eye.getLootToInjectID())));
                        tableBuilder.withPool(poolBuilder);
                    }
                }
            }

            // Hardcoded Injected Items
            if (Identifier.withDefaultNamespace("entities/witch").equals(key.identifier())) {
                ResourceKey<LootTable> resourceKey = ResourceKey.create(Registries.LOOT_TABLE, EndRemasteredCommon.ModResourceLocation("minecraft/entities/witch"));
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .add(NestedLootTable.lootTableReference(resourceKey));
                tableBuilder.withPool(poolBuilder);

            } else if (Identifier.withDefaultNamespace("entities/skeleton_horse").equals(key.identifier())) {
                ResourceKey<LootTable> resourceKey = ResourceKey.create(Registries.LOOT_TABLE, EndRemasteredCommon.ModResourceLocation("minecraft/entities/skeleton_horse"));
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .add(NestedLootTable.lootTableReference(resourceKey));
                tableBuilder.withPool(poolBuilder);
            }
        });
    }
}