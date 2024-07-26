package com.teamremastered.endrem.config;

public class ConfigHandler {
    private static ConfigOptions CONFIG = ConfigOptions.getERConfig();

    public static boolean USE_EYE_OF_ENDER = CONFIG.USE_EYE_OF_ENDER;
    public static boolean THROW_EYE_OF_ENDER = CONFIG.THROW_EYE_OF_ENDER;
    public static boolean FRAME_HAS_RANDOM_EYE = CONFIG.FRAME_HAS_RANDOM_EYE;
    public static int EYE_BREAK_PROBABILITY = CONFIG.EYE_BREAK_PROBABILITY;
    public static boolean IS_CRYPTIC_EYE_OBTAINABLE = CONFIG.IS_CRYPTIC_EYE_OBTAINABLE;
    public static boolean IS_EVIL_EYE_OBTAINABLE = CONFIG.IS_EVIL_EYE_OBTAINABLE;
    public static boolean CAN_REMOVE_EYE = CONFIG.CAN_REMOVE_EYE;

}