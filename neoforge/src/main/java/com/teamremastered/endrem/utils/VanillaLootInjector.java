package com.teamremastered.endrem.utils;

import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.item.JsonEye;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

import java.util.Optional;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class VanillaLootInjector {
    @SubscribeEvent
    public static void resourceReloadListener(AddReloadListenerEvent event) {
        for (JsonEye eye : JsonEye.getEyes()) {
            for (ResourceLocation loot : eye.getLootTablesID()) {
                ResourceKey<LootTable> resourceKey = ResourceKey.create(Registries.LOOT_TABLE, loot);
                LootTable injectTable = event.getServerResources().fullRegistries().getLootTable(ResourceKey.create(Registries.LOOT_TABLE, eye.getLootToInjectID()));
                try {
                    event.getServerResources().fullRegistries().getLootTable(resourceKey).addPool(injectTable.getPool("eye_pool"));
                } catch (Exception e) {
                    Constants.LOGGER.error("Could not find the \"eye_pool\" inside the Loot Table located in: " + eye.getLootToInjectID().toString() );
                }
            }
        }
    }
}