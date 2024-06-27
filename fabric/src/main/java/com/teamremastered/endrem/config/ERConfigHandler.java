package com.teamremastered.endrem.config;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.teamremastered.endrem.Constants;
import net.fabricmc.loader.api.FabricLoader;


public class ERConfigHandler {

    private static Path configFilePath;
    private static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static boolean USE_EYE_OF_ENDER = false;
    public static boolean THROW_EYE_OF_ENDER = false;
    public static boolean FRAME_HAS_RANDOM_EYE = false;
    public static int EYE_BREAK_PROBABILITY = 10;
    public static boolean IS_CRYPTIC_EYE_OBTAINABLE = true;
    public static boolean IS_EVIL_EYE_OBTAINABLE = true;
    public static boolean CAN_REMOVE_EYE = false;


    public static String ROGUE_EYE_LOOT_TABLE_ID = "minecraft:chests/jungle_temple";
    public static String CORRUPTED_EYE_LOOT_TABLE_ID = "minecraft:chests/pillager_outpost";
    public static String BLACK_EYE_LOOT_TABLE_ID = "minecraft:chests/buried_treasure";
    public static String LOST_EYE_LOOT_TABLE_ID = "minecraft:chests/abandoned_mineshaft";
    public static String OLD_EYE_LOOT_TABLE_ID = "minecraft:chests/desert_pyramid";
    public static String COLD_EYE_LOOT_TABLE_ID = "minecraft:chests/igloo_chest";
    public static String NETHER_EYE_LOOT_TABLE_ID = "minecraft:chests/nether_bridge";
    public static String CURSED_EYE_LOOT_TABLE_ID = "minecraft:chests/bastion_treasure";
    public static String MAGICAL_EYE_MANSION_LOOT_TABLE_ID = "minecraft:chests/woodland_mansion";

    public static String MAGICAL_EYE_EVOKER_LOOT_TABLE_ID = "minecraft:entities/evoker";
    public static String WITHER_EYE_LOOT_TABLE_ID = "minecraft:entities/wither";
    public static String GUARDIAN_EYE_LOOT_TABLE_ID = "minecraft:entities/elder_guardian";
    public static String WITCH_PUPIL_LOOT_TABLE_ID = "minecraft:entities/witch";
    public static String UNDEAD_SOUL_LOOT_TABLE_ID = "minecraft:entities/skeleton_horse";

    public static void load() {

        Reader reader;
        if(getFilePath().toFile().exists()) {
            try {
                reader = Files.newBufferedReader(getFilePath());

                Data data = gson.fromJson(reader, Data.class);

                USE_EYE_OF_ENDER = data.common.ENABLE_EYE_OF_ENDER;
                THROW_EYE_OF_ENDER = data.common.THROW_EYE_OF_ENDER;
                FRAME_HAS_RANDOM_EYE = data.common.FRAME_HAS_RANDOM_EYE;
                EYE_BREAK_PROBABILITY = data.common.EYE_BREAK_PROBABILITY;
                IS_CRYPTIC_EYE_OBTAINABLE = data.common.IS_CRYPTIC_EYE_OBTAINABLE;
                IS_EVIL_EYE_OBTAINABLE = data.common.IS_EVIL_EYE_OBTAINABLE;
                CAN_REMOVE_EYE = data.common.CAN_REMOVE_EYE;

                ROGUE_EYE_LOOT_TABLE_ID = data.common.ROGUE_EYE_LOOT_TABLE_ID;
                CORRUPTED_EYE_LOOT_TABLE_ID = data.common.CORRUPTED_EYE_LOOT_TABLE_ID;
                BLACK_EYE_LOOT_TABLE_ID = data.common.BLACK_EYE_LOOT_TABLE_ID;
                LOST_EYE_LOOT_TABLE_ID = data.common.LOST_EYE_LOOT_TABLE_ID;
                OLD_EYE_LOOT_TABLE_ID = data.common.OLD_EYE_LOOT_TABLE_ID;
                COLD_EYE_LOOT_TABLE_ID = data.common.COLD_EYE_LOOT_TABLE_ID;
                NETHER_EYE_LOOT_TABLE_ID = data.common.NETHER_EYE_LOOT_TABLE_ID;
                CURSED_EYE_LOOT_TABLE_ID = data.common.CURSED_EYE_LOOT_TABLE_ID;
                MAGICAL_EYE_MANSION_LOOT_TABLE_ID = data.common.MAGICAL_EYE_MANSION_LOOT_TABLE_ID;
                MAGICAL_EYE_EVOKER_LOOT_TABLE_ID = data.common.MAGICAL_EYE_EVOKER_LOOT_TABLE_ID;
                WITHER_EYE_LOOT_TABLE_ID = data.common.WITHER_EYE_LOOT_TABLE_ID;
                GUARDIAN_EYE_LOOT_TABLE_ID = data.common.GUARDIAN_EYE_LOOT_TABLE_ID;
                WITCH_PUPIL_LOOT_TABLE_ID = data.common.WITCH_PUPIL_LOOT_TABLE_ID;
                UNDEAD_SOUL_LOOT_TABLE_ID = data.common.UNDEAD_SOUL_LOOT_TABLE_ID;
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        save();
    }

    public static void save() {
        try {
            Writer writer = Files.newBufferedWriter(getFilePath());
            Data data = new Data(new Data.Common(
                    USE_EYE_OF_ENDER,
                    THROW_EYE_OF_ENDER,
                    FRAME_HAS_RANDOM_EYE,
                    EYE_BREAK_PROBABILITY,
                    IS_CRYPTIC_EYE_OBTAINABLE,
                    IS_EVIL_EYE_OBTAINABLE,
                    CAN_REMOVE_EYE,

                    ROGUE_EYE_LOOT_TABLE_ID,
                    CORRUPTED_EYE_LOOT_TABLE_ID,
                    BLACK_EYE_LOOT_TABLE_ID,
                    LOST_EYE_LOOT_TABLE_ID,
                    OLD_EYE_LOOT_TABLE_ID,
                    COLD_EYE_LOOT_TABLE_ID,
                    NETHER_EYE_LOOT_TABLE_ID,
                    CURSED_EYE_LOOT_TABLE_ID,
                    MAGICAL_EYE_MANSION_LOOT_TABLE_ID,
                    MAGICAL_EYE_EVOKER_LOOT_TABLE_ID,
                    WITHER_EYE_LOOT_TABLE_ID,
                    GUARDIAN_EYE_LOOT_TABLE_ID,
                    WITCH_PUPIL_LOOT_TABLE_ID,
                    UNDEAD_SOUL_LOOT_TABLE_ID));
            gson.toJson(data, writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Path getFilePath() {
        if(configFilePath == null) {
            configFilePath = FabricLoader.getInstance().getConfigDir().resolve(Constants.MOD_ID + ".json");
        }
        return configFilePath;
    }

    private static class Data {

        private Common common;

        public Data(Common common) {
            this.common = common;
        }

        private static class Common {

            private final String enableEyeOfEnderComment = "Enable/Disable usage of Ender Eyes";
            private final boolean ENABLE_EYE_OF_ENDER;

            private final String throwEyeOfEnderComment = "Enable/Disable throwing of Ender Eyes";
            private final boolean THROW_EYE_OF_ENDER;

            private final String frameHasRandomEyeComment = "Enable/Disable whether or not ender eyes can naturally generate within the frames of the portal";
            private final boolean FRAME_HAS_RANDOM_EYE;

            private final String eyeBreakProbabilityComment = "Percentage chance of eyes breaking when thrown";
            private final int EYE_BREAK_PROBABILITY;

            private final String crypticAndEvilEyeComment = "Decides if the cryptic eye and the evil eye is obtainable in game";
            private final boolean IS_CRYPTIC_EYE_OBTAINABLE;
            private final boolean IS_EVIL_EYE_OBTAINABLE;

            private final String removeVanillaEyeComment = "Decides whether or not you can remove an ender eye from a frame";
            private final boolean CAN_REMOVE_EYE;

            private final String lootTablesIdComment = "this below is what you want to modify if you wanna change the loot tables of the eyes and their chance to appear.";
            private final String ROGUE_EYE_LOOT_TABLE_ID;
            private final String CORRUPTED_EYE_LOOT_TABLE_ID;
            private final String BLACK_EYE_LOOT_TABLE_ID;
            private final String LOST_EYE_LOOT_TABLE_ID;
            private final String OLD_EYE_LOOT_TABLE_ID;
            private final String COLD_EYE_LOOT_TABLE_ID;
            private final String NETHER_EYE_LOOT_TABLE_ID;
            private final String CURSED_EYE_LOOT_TABLE_ID;
            private final String MAGICAL_EYE_MANSION_LOOT_TABLE_ID;
            private final String MAGICAL_EYE_EVOKER_LOOT_TABLE_ID;
            private final String WITHER_EYE_LOOT_TABLE_ID;
            private final String GUARDIAN_EYE_LOOT_TABLE_ID;
            private final String WITCH_PUPIL_LOOT_TABLE_ID;
            private final String UNDEAD_SOUL_LOOT_TABLE_ID;


            private Common() {
                 ENABLE_EYE_OF_ENDER = false;
                 THROW_EYE_OF_ENDER = false;
                 FRAME_HAS_RANDOM_EYE = false;
                 EYE_BREAK_PROBABILITY = 10;
                 IS_CRYPTIC_EYE_OBTAINABLE = true;
                 IS_EVIL_EYE_OBTAINABLE = true;
                 CAN_REMOVE_EYE = false;

                 ROGUE_EYE_LOOT_TABLE_ID = "minecraft:chests/jungle_temple";
                 CORRUPTED_EYE_LOOT_TABLE_ID = "minecraft:chests/pillager_outpost";
                 BLACK_EYE_LOOT_TABLE_ID = "minecraft:chests/buried_treasure";
                 LOST_EYE_LOOT_TABLE_ID = "minecraft:chests/abandoned_mineshaft";
                 OLD_EYE_LOOT_TABLE_ID = "minecraft:chests/desert_pyramid";
                 COLD_EYE_LOOT_TABLE_ID = "minecraft:chests/igloo_chest";
                 NETHER_EYE_LOOT_TABLE_ID = "minecraft:chests/nether_bridge";
                 CURSED_EYE_LOOT_TABLE_ID = "minecraft:chests/bastion_treasure";
                 MAGICAL_EYE_MANSION_LOOT_TABLE_ID = "minecraft:chests/woodland_mansion";

                 MAGICAL_EYE_EVOKER_LOOT_TABLE_ID = "minecraft:entities/evoker";
                 WITHER_EYE_LOOT_TABLE_ID = "minecraft:entities/wither";
                 GUARDIAN_EYE_LOOT_TABLE_ID = "minecraft:entities/elder_guardian";
                 WITCH_PUPIL_LOOT_TABLE_ID = "minecraft:entities/witch";
                 UNDEAD_SOUL_LOOT_TABLE_ID = "minecraft:entities/skeleton_horse";
            }

            private Common(
                                   boolean ENABLE_EYE_OF_ENDER,
                                   boolean THROW_EYE_OF_ENDER,
                                   boolean FRAME_HAS_RANDOM_EYE,
                                   int EYE_BREAK_PROBABILITY,
                                   boolean IS_CRYPTIC_EYE_OBTAINABLE,
                                   boolean IS_EVIL_EYE_OBTAINABLE,
                                   boolean CAN_REMOVE_EYE,

                                   String ROGUE_EYE_LOOT_TABLE_ID,
                                   String CORRUPTED_EYE_LOOT_TABLE_ID,
                                   String BLACK_EYE_LOOT_TABLE_ID,
                                   String LOST_EYE_LOOT_TABLE_ID,
                                   String OLD_EYE_LOOT_TABLE_ID,
                                   String COLD_EYE_LOOT_TABLE_ID,
                                   String NETHER_EYE_LOOT_TABLE_ID,
                                   String CURSED_EYE_LOOT_TABLE_ID,
                                   String MAGICAL_EYE_MANSION_LOOT_TABLE_ID,
                                   String MAGICAL_EYE_EVOKER_LOOT_TABLE_ID,
                                   String WITHER_EYE_LOOT_TABLE_ID,
                                   String GUARDIAN_EYE_LOOT_TABLE_ID,
                                   String WITCH_PUPIL_LOOT_TABLE_ID,
                                   String UNDEAD_SOUL_LOOT_TABLE_ID)
            {
                this.ENABLE_EYE_OF_ENDER = ENABLE_EYE_OF_ENDER;
                this.THROW_EYE_OF_ENDER = THROW_EYE_OF_ENDER;
                this.FRAME_HAS_RANDOM_EYE = FRAME_HAS_RANDOM_EYE;
                this.EYE_BREAK_PROBABILITY = EYE_BREAK_PROBABILITY;
                this.IS_CRYPTIC_EYE_OBTAINABLE = IS_CRYPTIC_EYE_OBTAINABLE;
                this.IS_EVIL_EYE_OBTAINABLE = IS_EVIL_EYE_OBTAINABLE;
                this.CAN_REMOVE_EYE = CAN_REMOVE_EYE;

                this.ROGUE_EYE_LOOT_TABLE_ID = ROGUE_EYE_LOOT_TABLE_ID;
                this.CORRUPTED_EYE_LOOT_TABLE_ID = CORRUPTED_EYE_LOOT_TABLE_ID;
                this.BLACK_EYE_LOOT_TABLE_ID = BLACK_EYE_LOOT_TABLE_ID;
                this.LOST_EYE_LOOT_TABLE_ID = LOST_EYE_LOOT_TABLE_ID;
                this.OLD_EYE_LOOT_TABLE_ID = OLD_EYE_LOOT_TABLE_ID;
                this.COLD_EYE_LOOT_TABLE_ID = COLD_EYE_LOOT_TABLE_ID;
                this.NETHER_EYE_LOOT_TABLE_ID = NETHER_EYE_LOOT_TABLE_ID;
                this.CURSED_EYE_LOOT_TABLE_ID = CURSED_EYE_LOOT_TABLE_ID;
                this.MAGICAL_EYE_MANSION_LOOT_TABLE_ID = MAGICAL_EYE_MANSION_LOOT_TABLE_ID;
                this.MAGICAL_EYE_EVOKER_LOOT_TABLE_ID = MAGICAL_EYE_EVOKER_LOOT_TABLE_ID;
                this.WITHER_EYE_LOOT_TABLE_ID = WITHER_EYE_LOOT_TABLE_ID;
                this.GUARDIAN_EYE_LOOT_TABLE_ID = GUARDIAN_EYE_LOOT_TABLE_ID;
                this.WITCH_PUPIL_LOOT_TABLE_ID = WITCH_PUPIL_LOOT_TABLE_ID;
                this.UNDEAD_SOUL_LOOT_TABLE_ID = UNDEAD_SOUL_LOOT_TABLE_ID;
            }
        }
    }
}
