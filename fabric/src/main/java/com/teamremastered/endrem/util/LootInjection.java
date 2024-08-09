package com.teamremastered.endrem.util;

import com.teamremastered.endrem.CommonClass;
import com.teamremastered.endrem.item.JsonEye;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;

public class LootInjection {

    public static void register() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source) -> {

            // Injected Eyes
            for (JsonEye eye : JsonEye.getEyes()) {
                for (ResourceLocation table : eye.getLootTablesID()) {
                    if (table.equals(key.location())) {
                        LootPool.Builder poolBuilder = LootPool.lootPool()
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, eye.getLootToInjectID())));
                        tableBuilder.withPool(poolBuilder);
                    }
                }
            }

            // Hardcoded Injected Items
            if (ResourceLocation.withDefaultNamespace("entities/witch").equals(key.location())) {
                ResourceKey<LootTable> resourceKey = ResourceKey.create(Registries.LOOT_TABLE, CommonClass.ModResourceLocation("minecraft/entities/witch"));
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .add(NestedLootTable.lootTableReference(resourceKey));
                tableBuilder.withPool(poolBuilder);

            } else if (ResourceLocation.withDefaultNamespace("entities/skeleton_horse").equals(key.location())) {
                ResourceKey<LootTable> resourceKey = ResourceKey.create(Registries.LOOT_TABLE, CommonClass.ModResourceLocation("minecraft/entities/skeleton_horse"));
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .add(NestedLootTable.lootTableReference(resourceKey));
                tableBuilder.withPool(poolBuilder);
            }
        });
    }
}