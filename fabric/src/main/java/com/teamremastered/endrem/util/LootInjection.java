package com.teamremastered.endrem.util;

import com.teamremastered.endrem.CommonClass;
import com.teamremastered.endrem.config.ERConfigHandler;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;

public class LootInjection {

    //Function to get the injection path and ModId
    private static String getInjection(String lootTableID, Boolean isModId) {

        String[] injectionParts = lootTableID.split(":");
        String modId = injectionParts[0];
        String path = injectionParts[1];

        if (isModId) {
            return modId;
        }
        else {
            return  path;
        }
    }

    /* Chests */
    private static final ResourceLocation JUNGLE_TEMPLE_CHEST_LOOT_TABLE_ID = ResourceLocation.fromNamespaceAndPath(getInjection(ERConfigHandler.ROGUE_EYE_LOOT_TABLE_ID,true), getInjection(ERConfigHandler.ROGUE_EYE_LOOT_TABLE_ID,false));

    private static final ResourceLocation OUTPOST_CHEST_LOOT_TABLE_ID = ResourceLocation.fromNamespaceAndPath(getInjection(ERConfigHandler.CORRUPTED_EYE_LOOT_TABLE_ID,true), getInjection(ERConfigHandler.CORRUPTED_EYE_LOOT_TABLE_ID,false));

    private static final ResourceLocation BURIED_TREASURE_CHEST_LOOT_TABLE_ID = ResourceLocation.fromNamespaceAndPath(getInjection(ERConfigHandler.BLACK_EYE_LOOT_TABLE_ID,true), getInjection(ERConfigHandler.BLACK_EYE_LOOT_TABLE_ID,false));

    private static final ResourceLocation MINESHAFT_CHEST_LOOT_TABLE_ID = ResourceLocation.fromNamespaceAndPath(getInjection(ERConfigHandler.LOST_EYE_LOOT_TABLE_ID,true), getInjection(ERConfigHandler.LOST_EYE_LOOT_TABLE_ID,false));

    private static final ResourceLocation DESERT_PYRAMID_CHEST_LOOT_TABLE_ID = ResourceLocation.fromNamespaceAndPath(getInjection(ERConfigHandler.OLD_EYE_LOOT_TABLE_ID, true), getInjection(ERConfigHandler.OLD_EYE_LOOT_TABLE_ID, false));

    private static final ResourceLocation IGLOO_CHEST_LOOT_TABLE_ID = ResourceLocation.fromNamespaceAndPath(getInjection(ERConfigHandler.COLD_EYE_LOOT_TABLE_ID,true), getInjection(ERConfigHandler.COLD_EYE_LOOT_TABLE_ID,false));

    private static final ResourceLocation NETHER_BRIDGE_CHEST_LOOT_TABLE_ID = ResourceLocation.fromNamespaceAndPath(getInjection(ERConfigHandler.NETHER_EYE_LOOT_TABLE_ID,true), getInjection(ERConfigHandler.NETHER_EYE_LOOT_TABLE_ID,false));

    private static final ResourceLocation BASTION_TREASURE_LOOT_TABLE_ID = ResourceLocation.fromNamespaceAndPath(getInjection(ERConfigHandler.CURSED_EYE_LOOT_TABLE_ID,true), getInjection(ERConfigHandler.CURSED_EYE_LOOT_TABLE_ID,false));

    private static final ResourceLocation WOODLAND_MANSION_LOOT_TABLE_ID = ResourceLocation.fromNamespaceAndPath(getInjection(ERConfigHandler.MAGICAL_EYE_MANSION_LOOT_TABLE_ID,true), getInjection(ERConfigHandler.MAGICAL_EYE_MANSION_LOOT_TABLE_ID,false));


    /* Entities */
    private static final ResourceLocation EVOKER_LOOT_TABLE_ID = ResourceLocation.fromNamespaceAndPath(getInjection(ERConfigHandler.MAGICAL_EYE_EVOKER_LOOT_TABLE_ID,true), getInjection(ERConfigHandler.MAGICAL_EYE_EVOKER_LOOT_TABLE_ID,false));
    private static final ResourceLocation WITHER_LOOT_TABLE_ID = ResourceLocation.fromNamespaceAndPath(getInjection(ERConfigHandler.WITHER_EYE_LOOT_TABLE_ID,true), getInjection(ERConfigHandler.WITHER_EYE_LOOT_TABLE_ID,false));
    private static final ResourceLocation ELDER_GUARDIAN_LOOT_TABLE_ID = ResourceLocation.fromNamespaceAndPath(getInjection(ERConfigHandler.GUARDIAN_EYE_LOOT_TABLE_ID,true), getInjection(ERConfigHandler.GUARDIAN_EYE_LOOT_TABLE_ID,false));
    private static final ResourceLocation WITCH_LOOT_TABLE_ID = ResourceLocation.fromNamespaceAndPath(getInjection(ERConfigHandler.WITCH_PUPIL_LOOT_TABLE_ID,true), getInjection(ERConfigHandler.WITCH_PUPIL_LOOT_TABLE_ID,false));
    private static final ResourceLocation SKELETON_HORSE_LOOT_TABLE_ID = ResourceLocation.fromNamespaceAndPath(getInjection(ERConfigHandler.UNDEAD_SOUL_LOOT_TABLE_ID,true), getInjection(ERConfigHandler.UNDEAD_SOUL_LOOT_TABLE_ID,false));

    /* Keys */
    private static final ResourceKey<LootTable> BLACK_EYE_LOOT_TABLE_KEY = ResourceKey.create(Registries.LOOT_TABLE, CommonClass.ModResourceLocation("minecraft/chests/buried_treasure"));
    private static final ResourceKey<LootTable> COLD_EYE_LOOT_TABLE_KEY = ResourceKey.create(Registries.LOOT_TABLE, CommonClass.ModResourceLocation("minecraft/chests/igloo_chest"));
    private static final ResourceKey<LootTable> CORRUPTED_EYE_LOOT_TABLE_KEY = ResourceKey.create(Registries.LOOT_TABLE, CommonClass.ModResourceLocation("minecraft/chests/pillager_outpost"));
    private static final ResourceKey<LootTable> LOST_EYE_LOOT_TABLE_KEY = ResourceKey.create(Registries.LOOT_TABLE, CommonClass.ModResourceLocation("minecraft/chests/abandoned_mineshaft"));
    private static final ResourceKey<LootTable> NETHER_EYE_LOOT_TABLE_KEY = ResourceKey.create(Registries.LOOT_TABLE, CommonClass.ModResourceLocation("minecraft/chests/nether_bridge"));
    private static final ResourceKey<LootTable> OLD_EYE_LOOT_TABLE_KEY = ResourceKey.create(Registries.LOOT_TABLE, CommonClass.ModResourceLocation("minecraft/chests/desert_pyramid"));
    private static final ResourceKey<LootTable> ROGUE_EYE_LOOT_TABLE_KEY = ResourceKey.create(Registries.LOOT_TABLE, CommonClass.ModResourceLocation("minecraft/chests/jungle_temple"));
    private static final ResourceKey<LootTable> CURSED_EYE_LOOT_TABLE_KEY = ResourceKey.create(Registries.LOOT_TABLE, CommonClass.ModResourceLocation("minecraft/chests/bastion_treasure"));
    private static final ResourceKey<LootTable> GUARDIAN_EYE_LOOT_TABLE_KEY = ResourceKey.create(Registries.LOOT_TABLE, CommonClass.ModResourceLocation("minecraft/entities/elder_guardian"));
    private static final ResourceKey<LootTable> MAGICAL_EYE_LOOT_TABLE_KEY = ResourceKey.create(Registries.LOOT_TABLE, CommonClass.ModResourceLocation("minecraft/entities/evoker"));
    private static final ResourceKey<LootTable> MAGICAL_EYE_WOODLAND_LOOT_TABLE_KEY = ResourceKey.create(Registries.LOOT_TABLE, CommonClass.ModResourceLocation("minecraft/chests/woodland_mansion"));
    private static final ResourceKey<LootTable> WITHER_EYE_LOOT_TABLE_KEY = ResourceKey.create(Registries.LOOT_TABLE, CommonClass.ModResourceLocation("minecraft/entities/wither"));
    private static final ResourceKey<LootTable> WITCH_PUPIL_LOOT_TABLE_KEY = ResourceKey.create(Registries.LOOT_TABLE, CommonClass.ModResourceLocation("minecraft/entities/witch"));
    private static final ResourceKey<LootTable> UNDEAD_SOUL_LOOT_TABLE_KEY = ResourceKey.create(Registries.LOOT_TABLE, CommonClass.ModResourceLocation("minecraft/entities/skeleton_horse"));

    public static void initRegister() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source) -> {
            /* Chests Loot */
            if (JUNGLE_TEMPLE_CHEST_LOOT_TABLE_ID.equals(key.location())) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                .add(NestedLootTable.lootTableReference(ROGUE_EYE_LOOT_TABLE_KEY));
                tableBuilder.withPool(poolBuilder);
            }
            else if (OUTPOST_CHEST_LOOT_TABLE_ID.equals(key.registry())) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                .add(NestedLootTable.lootTableReference(CORRUPTED_EYE_LOOT_TABLE_KEY));
                tableBuilder.withPool(poolBuilder);
            }
            else if (BURIED_TREASURE_CHEST_LOOT_TABLE_ID.equals(key.registry())) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                .add(NestedLootTable.lootTableReference(BLACK_EYE_LOOT_TABLE_KEY));
                tableBuilder.withPool(poolBuilder);
            }
            else if (MINESHAFT_CHEST_LOOT_TABLE_ID.equals(key.registry())) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                .add(NestedLootTable.lootTableReference(LOST_EYE_LOOT_TABLE_KEY));
                tableBuilder.withPool(poolBuilder);
            }
            else if (DESERT_PYRAMID_CHEST_LOOT_TABLE_ID.equals(key.registry())) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                .add(NestedLootTable.lootTableReference(OLD_EYE_LOOT_TABLE_KEY));
                tableBuilder.withPool(poolBuilder);
            }
            else if (IGLOO_CHEST_LOOT_TABLE_ID.equals(key.registry())) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                .add(NestedLootTable.lootTableReference(COLD_EYE_LOOT_TABLE_KEY));
                tableBuilder.withPool(poolBuilder);
            }
            else if (NETHER_BRIDGE_CHEST_LOOT_TABLE_ID.equals(key.registry())) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                .add(NestedLootTable.lootTableReference(NETHER_EYE_LOOT_TABLE_KEY));
                tableBuilder.withPool(poolBuilder);
            }

            else if (BASTION_TREASURE_LOOT_TABLE_ID.equals(key.registry())) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                .add(NestedLootTable.lootTableReference(CURSED_EYE_LOOT_TABLE_KEY));
                tableBuilder.withPool(poolBuilder);
            }
            else if (WOODLAND_MANSION_LOOT_TABLE_ID.equals(key.registry())) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                .add(NestedLootTable.lootTableReference(MAGICAL_EYE_WOODLAND_LOOT_TABLE_KEY));
                tableBuilder.withPool(poolBuilder);
            }

            /* Entities Loot */
            else if (EVOKER_LOOT_TABLE_ID.equals(key.registry())) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                .add(NestedLootTable.lootTableReference(MAGICAL_EYE_LOOT_TABLE_KEY));
                tableBuilder.withPool(poolBuilder);
            }
            else if (WITHER_LOOT_TABLE_ID.equals(key.registry())) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                .add(NestedLootTable.lootTableReference(WITHER_EYE_LOOT_TABLE_KEY));
                tableBuilder.withPool(poolBuilder);
            }
            else if (ELDER_GUARDIAN_LOOT_TABLE_ID.equals(key.registry())) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                .add(NestedLootTable.lootTableReference(GUARDIAN_EYE_LOOT_TABLE_KEY));
                tableBuilder.withPool(poolBuilder);
            }
            else if (WITCH_LOOT_TABLE_ID.equals(key.registry())) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                .add(NestedLootTable.lootTableReference(WITCH_PUPIL_LOOT_TABLE_KEY));
                tableBuilder.withPool(poolBuilder);
            }
            else if (SKELETON_HORSE_LOOT_TABLE_ID.equals(key.registry())) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                .add(NestedLootTable.lootTableReference(UNDEAD_SOUL_LOOT_TABLE_KEY));
                tableBuilder.withPool(poolBuilder);
            }
        });
    }
}