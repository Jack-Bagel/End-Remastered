package com.teamremastered.endrem.config;

public class ConfigHandler {
    private static ConfigOptions CONFIG = ConfigOptions.getERConfig();

    public static final boolean USE_EYE_OF_ENDER = CONFIG.USE_EYE_OF_ENDER;
    public static final boolean THROW_EYE_OF_ENDER = CONFIG.THROW_EYE_OF_ENDER;
    public static final boolean FRAME_HAS_RANDOM_EYE = CONFIG.FRAME_HAS_RANDOM_EYE;
    public static final int EYE_BREAK_PROBABILITY = CONFIG.EYE_BREAK_PROBABILITY;
    public static final boolean IS_CRYPTIC_EYE_OBTAINABLE = CONFIG.IS_CRYPTIC_EYE_OBTAINABLE;
    public static final boolean IS_EVIL_EYE_OBTAINABLE = CONFIG.IS_EVIL_EYE_OBTAINABLE;
    public static final boolean CAN_REMOVE_EYE = CONFIG.CAN_REMOVE_EYE;
}