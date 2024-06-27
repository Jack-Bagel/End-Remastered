package com.teamremastered.endrem.util;

import com.teamremastered.endrem.config.ERConfigHandler;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.world.level.storage.loot.LootPool;

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
    private static final Identifier JUNGLE_TEMPLE_CHEST_LOOT_TABLE_ID = new Identifier(getInjection(ERConfigHandler.ROGUE_EYE_LOOT_TABLE_ID,true), getInjection(ERConfigHandler.ROGUE_EYE_LOOT_TABLE_ID,false));

    private static final Identifier OUTPOST_CHEST_LOOT_TABLE_ID = new Identifier(getInjection(ERConfigHandler.CORRUPTED_EYE_LOOT_TABLE_ID,true), getInjection(ERConfigHandler.CORRUPTED_EYE_LOOT_TABLE_ID,false));

    private static final Identifier BURIED_TREASURE_CHEST_LOOT_TABLE_ID = new Identifier(getInjection(ERConfigHandler.BLACK_EYE_LOOT_TABLE_ID,true), getInjection(ERConfigHandler.BLACK_EYE_LOOT_TABLE_ID,false));

    private static final Identifier MINESHAFT_CHEST_LOOT_TABLE_ID = new Identifier(getInjection(ERConfigHandler.LOST_EYE_LOOT_TABLE_ID,true), getInjection(ERConfigHandler.LOST_EYE_LOOT_TABLE_ID,false));

    private static final Identifier DESERT_PYRAMID_CHEST_LOOT_TABLE_ID = new Identifier(getInjection(ERConfigHandler.OLD_EYE_LOOT_TABLE_ID, true), getInjection(ERConfigHandler.OLD_EYE_LOOT_TABLE_ID, false));

    private static final Identifier IGLOO_CHEST_LOOT_TABLE_ID = new Identifier(getInjection(ERConfigHandler.COLD_EYE_LOOT_TABLE_ID,true), getInjection(ERConfigHandler.COLD_EYE_LOOT_TABLE_ID,false));

    private static final Identifier NETHER_BRIDGE_CHEST_LOOT_TABLE_ID = new Identifier(getInjection(ERConfigHandler.NETHER_EYE_LOOT_TABLE_ID,true), getInjection(ERConfigHandler.NETHER_EYE_LOOT_TABLE_ID,false));

    private static final Identifier BASTION_TREASURE_LOOT_TABLE_ID = new Identifier(getInjection(ERConfigHandler.CURSED_EYE_LOOT_TABLE_ID,true), getInjection(ERConfigHandler.CURSED_EYE_LOOT_TABLE_ID,false));

    private static final Identifier WOODLAND_MANSION_LOOT_TABLE_ID = new Identifier(getInjection(ERConfigHandler.MAGICAL_EYE_MANSION_LOOT_TABLE_ID,true), getInjection(ERConfigHandler.MAGICAL_EYE_MANSION_LOOT_TABLE_ID,false));


    /* Entities */
    private static final Identifier EVOKER_LOOT_TABLE_ID = new Identifier(getInjection(ERConfigHandler.MAGICAL_EYE_EVOKER_LOOT_TABLE_ID,true), getInjection(ERConfigHandler.MAGICAL_EYE_EVOKER_LOOT_TABLE_ID,false));
    private static final Identifier WITHER_LOOT_TABLE_ID = new Identifier(getInjection(ERConfigHandler.WITHER_EYE_LOOT_TABLE_ID,true), getInjection(ERConfigHandler.WITHER_EYE_LOOT_TABLE_ID,false));
    private static final Identifier ELDER_GUARDIAN_LOOT_TABLE_ID = new Identifier(getInjection(ERConfigHandler.GUARDIAN_EYE_LOOT_TABLE_ID,true), getInjection(ERConfigHandler.GUARDIAN_EYE_LOOT_TABLE_ID,false));
    private static final Identifier WITCH_LOOT_TABLE_ID = new Identifier(getInjection(ERConfigHandler.WITCH_PUPIL_LOOT_TABLE_ID,true), getInjection(ERConfigHandler.WITCH_PUPIL_LOOT_TABLE_ID,false));
    private static final Identifier SKELETON_HORSE_LOOT_TABLE_ID = new Identifier(getInjection(ERConfigHandler.UNDEAD_SOUL_LOOT_TABLE_ID,true), getInjection(ERConfigHandler.UNDEAD_SOUL_LOOT_TABLE_ID,false));



    public static void initRegister() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, table, setter) -> {
            /* Chests Loot */
            if (JUNGLE_TEMPLE_CHEST_LOOT_TABLE_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .with(LootTableEntry.builder(new Identifier("endrem", "minecraft/chests/jungle_temple")));
                table.pool(poolBuilder);
            } else if (OUTPOST_CHEST_LOOT_TABLE_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .with(LootTableEntry.builder(new Identifier("endrem", "minecraft/chests/pillager_outpost")));
                table.pool(poolBuilder);
            }
            else if (BURIED_TREASURE_CHEST_LOOT_TABLE_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .with(LootTableEntry.builder(new Identifier("endrem", "minecraft/chests/buried_treasure")));
                table.pool(poolBuilder);
            }
            else if (MINESHAFT_CHEST_LOOT_TABLE_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .with(LootTableEntry.builder(new Identifier("endrem", "minecraft/chests/abandoned_mineshaft")));
                table.pool(poolBuilder);
            }
            else if (DESERT_PYRAMID_CHEST_LOOT_TABLE_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .with(LootTableEntry.builder(new Identifier("endrem", "minecraft/chests/desert_pyramid")));
                table.pool(poolBuilder);
            }
            else if (IGLOO_CHEST_LOOT_TABLE_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .with(LootTableEntry.builder(new Identifier("endrem", "minecraft/chests/igloo_chest")));
                table.pool(poolBuilder);
            }
            else if (NETHER_BRIDGE_CHEST_LOOT_TABLE_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .with(LootTableEntry.builder(new Identifier("endrem", "minecraft/chests/nether_bridge")));
                table.pool(poolBuilder);
            }

            else if (BASTION_TREASURE_LOOT_TABLE_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .with(LootTableEntry.builder(new Identifier("endrem", "minecraft/chests/bastion_treasure")));
                table.pool(poolBuilder);
            }
            else if (WOODLAND_MANSION_LOOT_TABLE_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .with(LootTableEntry.builder(new Identifier("endrem", "minecraft/chests/woodland_mansion")));
                table.pool(poolBuilder);
            }

            /* Entities Loot */
            else if (EVOKER_LOOT_TABLE_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .with(LootTableEntry.builder(new Identifier("endrem", "minecraft/entities/evoker")));
                table.pool(poolBuilder);
            }
            else if (WITHER_LOOT_TABLE_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .with(LootTableEntry.builder(new Identifier("endrem", "minecraft/entities/wither")));
                table.pool(poolBuilder);
            }
            else if (ELDER_GUARDIAN_LOOT_TABLE_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .with(LootTableEntry.builder(new Identifier("endrem", "minecraft/entities/elder_guardian")));
                table.pool(poolBuilder);
            }
            else if (WITCH_LOOT_TABLE_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .with(LootTableEntry.builder(new Identifier("endrem", "minecraft/entities/witch")));
                table.pool(poolBuilder);
            }
            else if (SKELETON_HORSE_LOOT_TABLE_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .with(LootTableEntry.builder(new Identifier("endrem", "minecraft/entities/skeleton_horse")));
                table.pool(poolBuilder);
            }
        });
    }
}