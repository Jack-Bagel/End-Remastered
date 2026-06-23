package com.teamremastered.endrem.util;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;

public class LootInjection {

    public static void register() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source) -> {

            // Injected Eyes
//            for (JsonEye eye : JsonEye.getEyes()) {
//                for (ResourceLocation table : eye.getLootTablesID()) {
//                    if (table.equals(key.location())) {
//                        LootPool.Builder poolBuilder = LootPool.lootPool()
//                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, eye.getLootToInjectID())));
//                        tableBuilder.withPool(poolBuilder);
//                    }
//                }
//            }
//
//            // Hardcoded Injected Items
//            if (ResourceLocation.withDefaultNamespace("entities/witch").equals(key.location())) {
//                ResourceKey<LootTable> resourceKey = ResourceKey.create(Registries.LOOT_TABLE, EndRemasteredCommon.ModResourceLocation("minecraft/entities/witch"));
//                LootPool.Builder poolBuilder = LootPool.lootPool()
//                        .add(NestedLootTable.lootTableReference(resourceKey));
//                tableBuilder.withPool(poolBuilder);
//
//            } else if (ResourceLocation.withDefaultNamespace("entities/skeleton_horse").equals(key.location())) {
//                ResourceKey<LootTable> resourceKey = ResourceKey.create(Registries.LOOT_TABLE, EndRemasteredCommon.ModResourceLocation("minecraft/entities/skeleton_horse"));
//                LootPool.Builder poolBuilder = LootPool.lootPool()
//                        .add(NestedLootTable.lootTableReference(resourceKey));
//                tableBuilder.withPool(poolBuilder);
//            }
        });
    }
}