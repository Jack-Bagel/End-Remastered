package com.teamremastered.endrem.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.platform.Services;

import java.io.*;

public class ConfigOptions {
    static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final static String configPath = Services.CONFIG_HELPER.configDirectoryPath() + "/" + Services.CONFIG_HELPER.configFolderName() + "/";
    private final static String configName = Constants.MOD_ID + ".json";
    private static ConfigOptions ENDREM_CONFIG;
    public boolean USE_EYE_OF_ENDER;
    public boolean THROW_EYE_OF_ENDER;
    public boolean FRAME_HAS_RANDOM_EYE;
    public int EYE_BREAK_PROBABILITY;
    public boolean IS_CRYPTIC_EYE_OBTAINABLE;
    public boolean IS_EVIL_EYE_OBTAINABLE;
    public boolean CAN_REMOVE_EYE;
    private ConfigOptions() {
        this.USE_EYE_OF_ENDER = false;
        this.THROW_EYE_OF_ENDER = false;
        this.FRAME_HAS_RANDOM_EYE = false;
        this.EYE_BREAK_PROBABILITY = 10;
        this.IS_CRYPTIC_EYE_OBTAINABLE = true;
        this.IS_EVIL_EYE_OBTAINABLE = true;
        this.CAN_REMOVE_EYE = true;
    }

    public static void create() throws IOException {
        File configFolder = new File(configPath);

        if (!configFolder.exists()) {
            configFolder.mkdirs();
        }

        File configFile = new File(configPath + configName);
        if (!configFile.exists()) {
            try (FileWriter fw = new FileWriter(configFile.getPath())) {
                gson.toJson(new ConfigOptions(), fw);
            }
        }

        ENDREM_CONFIG = load();
    }

    public static ConfigOptions load() throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(configPath + configName));
        return gson.fromJson(bufferedReader, ConfigOptions.class);
    }

    public static ConfigOptions getERConfig() {
        return ENDREM_CONFIG;
    }
}