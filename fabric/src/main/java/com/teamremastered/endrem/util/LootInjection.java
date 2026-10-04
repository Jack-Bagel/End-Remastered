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
        LootTableEvents.MODIFY.register((key, tableBuilder, _, registryLookup) -> {

            //TODO: This doesn't work for the wither eye, idk why but it would be nice to know.
//            for (JsonEye eye : JsonEye.getEyes()) {
//                for (Identifier table : eye.getLootTablesID()) {
//                    if (table.equals(key.identifier())) {
//                        ResourceKey<LootTable> lootTableKey = ResourceKey.create(Registries.LOOT_TABLE, eye.getLootToInjectID());
//                        tableBuilder.modifyPools(pool -> pool.add(NestedLootTable.lootTableReference(registryLookup.getOrThrow(lootTableKey))));
//                    }
//                }
//            }

            // Injected Eyes
            for (JsonEye eye : JsonEye.getEyes()) {
                for (Identifier table : eye.getLootTablesID()) {
                    if (table.equals(key.identifier())) {
                        ResourceKey<LootTable> lootTableKey = ResourceKey.create(Registries.LOOT_TABLE, eye.getLootToInjectID());
                        LootPool.Builder poolBuilder = LootPool.lootPool()
                                .add(NestedLootTable.lootTableReference(registryLookup.getOrThrow(lootTableKey)));
                        tableBuilder.withPool(poolBuilder);                    }
                }
            }

            // Hardcoded Injected Items
            if (Identifier.withDefaultNamespace("entities/witch").equals(key.identifier())) {
                ResourceKey<LootTable> lootTableKey = ResourceKey.create(Registries.LOOT_TABLE, EndRemasteredCommon.ModIdentifier("minecraft/entities/witch"));
                tableBuilder.modifyPools(pool -> pool.add(NestedLootTable.lootTableReference(registryLookup.getOrThrow(lootTableKey))));
            }

            else if (Identifier.withDefaultNamespace("entities/skeleton_horse").equals(key.identifier())) {
                ResourceKey<LootTable> lootTableKey = ResourceKey.create(Registries.LOOT_TABLE, EndRemasteredCommon.ModIdentifier("minecraft/entities/skeleton_horse"));
                tableBuilder.modifyPools(pool -> pool.add(NestedLootTable.lootTableReference(registryLookup.getOrThrow(lootTableKey))));
            }
        });
    }
}