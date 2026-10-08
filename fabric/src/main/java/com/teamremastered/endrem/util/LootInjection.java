package com.teamremastered.endrem.util;

import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.EndRemasteredCommon;
import com.teamremastered.endrem.item.EyeData;
import com.teamremastered.endrem.registry.CommonRegistryKey;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;

import java.util.Optional;

public class LootInjection {

    public static void init() {

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
           Optional<HolderLookup.RegistryLookup<EyeData>> registryLookup = registries.lookup(CommonRegistryKey.EYE_DATA);
            // Injected Eyes
            EyeDataManager.getDynamicEyes(registryLookup) .forEach(eye -> {
                        eye.lootTablesID().forEach(table -> {

                            if (table.equals(key.location())) {
                        LootPool.Builder poolBuilder = LootPool.lootPool().add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, eye.poolID())));

                        if (eye.poolID().equals(ResourceLocation.withDefaultNamespace("empty"))) {
                            Constants.LOGGER.warn("\"{}\" has no pool to use", eye.poolID());
                            return; //Skips only the iteration, bc of lambda
                        }
                        else if (eye.lootTablesID().isEmpty()) {
                            Constants.LOGGER.warn("\"{}\" has no loot table to inject into", eye.poolID());
                            return;
                        }

                        tableBuilder.withPool(poolBuilder);
                    }
                });
            });

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