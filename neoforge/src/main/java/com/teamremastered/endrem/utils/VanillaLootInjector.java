package com.teamremastered.endrem.utils;

import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.item.EyeData;
import com.teamremastered.endrem.registry.CommonRegistryKey;
import com.teamremastered.endrem.util.EyeDataManager;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

import java.util.Optional;

public class VanillaLootInjector {

    public static void init(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.addListener(VanillaLootInjector::resourceReloadListener);
    }

    public static void resourceReloadListener(AddReloadListenerEvent event) {
        Optional<HolderLookup.RegistryLookup<EyeData>> registryLookup = event.getServerResources().getRegistryLookup().lookup(CommonRegistryKey.EYE_DATA);

        EyeDataManager.getDynamicEyes(registryLookup).forEach(eye -> {
            eye.injections().forEach(injection -> {
                injection.targetTableIDs().forEach(targetTableID -> {

                    ResourceLocation sourceTableID = injection.sourceTableID();
                    ResourceKey<LootTable> resourceKey = ResourceKey.create(Registries.LOOT_TABLE, targetTableID);
                    LootTable sourceTable = event.getServerResources().fullRegistries().getLootTable(ResourceKey.create(Registries.LOOT_TABLE, sourceTableID));
                    LootTable targetTable = event.getServerResources().fullRegistries().getLootTable(resourceKey);
                    Optional<LootPool> injectedTable = Optional.ofNullable(sourceTable.getPool("eye_pool"));

                    try {
                        if (targetTable != LootTable.EMPTY && sourceTable != LootTable.EMPTY && injectedTable.isPresent()) {
                            event.getServerResources().fullRegistries().getLootTable(resourceKey).addPool(injectedTable.get());
                        }
                        else if (targetTable == LootTable.EMPTY && sourceTable == LootTable.EMPTY) {
                            Constants.LOGGER.warn("The target and injected loot tables provided by \"{}\" are invalid.", eye.id());
                        }
                        else if (targetTable == LootTable.EMPTY) {
                            Constants.LOGGER.warn("The target loot table provided by \"{}\" is invalid.", eye.id());
                        }
                        else {
                            Constants.LOGGER.warn("The injected loot table provided by \"{}\" is invalid.", eye.id());
                        }
                    } catch (Exception e) {
                        Constants.LOGGER.error(e.getLocalizedMessage());
                        Constants.LOGGER.error("Could not find the \"eye_pool\" inside the Loot Table located in: " + eye.id());
                    }
                });
            });
        });
    }
}