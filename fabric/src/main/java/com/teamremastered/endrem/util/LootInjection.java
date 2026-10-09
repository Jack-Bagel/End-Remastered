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
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class LootInjection {
    private static final Set<ResourceLocation> MISSING_TABLE = ConcurrentHashMap.newKeySet();

    public static void init() {

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
           Optional<HolderLookup.RegistryLookup<EyeData>> registryLookup = registries.lookup(CommonRegistryKey.EYE_DATA);
            // Injected Eyes
            EyeDataManager.getDynamicEyes(registryLookup).forEach(eye -> {
                        if (eye.injections().isEmpty() && MISSING_TABLE.add(eye.id())) {
                            Constants.LOGGER.warn("\"{}\" does not have a loot_injections field or it is empty. Ignore if the eye is not meant to be injected", eye.id());
                        }
                        eye.injections().forEach(injection ->  {
                            if (injection.targetTableIDs().isEmpty() && MISSING_TABLE.add(eye.id())) {
                                Constants.LOGGER.warn("\"{}\" does not have a target table or it is empty.", eye.id());
                            }

                            injection.targetTableIDs().forEach(targetTableID -> {
                                ResourceLocation sourceTableID = injection.sourceTableID();

                                if (sourceTableID.equals(ResourceLocation.withDefaultNamespace("empty")) && MISSING_TABLE.add(eye.id())) {
                                    Constants.LOGGER.warn("\"{}\" has no source table to use. Make sure it has one.", sourceTableID);
                                    return; //Skips only the iteration bc we're in a lambda
                                }

                                if (targetTableID.equals(key.location())) {
                                    // The pool is validated by Minecraft so there's no need to check if it is valid.
                                    LootPool.Builder poolBuilder = LootPool.lootPool().add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, sourceTableID)));
                                    tableBuilder.withPool(poolBuilder);
                                }
                            });
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