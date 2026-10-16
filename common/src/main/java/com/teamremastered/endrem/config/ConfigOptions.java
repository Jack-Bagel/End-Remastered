package com.teamremastered.endrem.config;

import com.google.gson.*;
import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.platform.Services;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public class ConfigOptions {
    final static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final static String configPath = Services.CONFIG_HELPER.configDirectoryPath() + "/" + Services.CONFIG_HELPER.configFolderName() + "/";
    private final static String configName = Constants.MOD_ID + ".json";
    public static boolean loadedWithErrors = false;
    private static ConfigOptions ENDREM_CONFIG;
    public final boolean USE_EYE_OF_ENDER;
    public final boolean THROW_EYE_OF_ENDER;
    public final boolean FRAME_HAS_RANDOM_EYE;
    public final boolean HIDE_DESCRIPTION;
    public final int EYE_BREAK_PROBABILITY;

    private ConfigOptions() {
        this.USE_EYE_OF_ENDER = false;
        this.THROW_EYE_OF_ENDER = false;
        this.FRAME_HAS_RANDOM_EYE = false;
        this.HIDE_DESCRIPTION = false;
        this.EYE_BREAK_PROBABILITY = 10;
    }

    public static void create() {
        try {
            File configFolder = new File(configPath);

            if (!configFolder.exists()) {
                configFolder.mkdirs();
            }

            File configFile = new File(configPath + configName);
            if (!configFile.exists()) {
                writeConfig(configFile);
            }

            ENDREM_CONFIG = load();

        }
        catch (IOException e) {
            loadedWithErrors = true;
            Constants.LOGGER.error("Could not create or access the config file, using default values", e);
            ENDREM_CONFIG = new ConfigOptions();
        }

        if (loadedWithErrors) {
            Constants.LOGGER.info("Make sure you have a valid endrem.json config. Delete the file to create a fresh working config if needed");
        } else {
            Constants.LOGGER.info("End Remastered config finished loading successfully");
        }
    }

    public static ConfigOptions load() {
        Optional<JsonObject> jsonConfigOptional;
        try (BufferedReader reader = new BufferedReader(new FileReader(configPath + configName))) {
            jsonConfigOptional = readJsonObject(reader);
        } catch (IOException e) {
            loadedWithErrors = true;
            Constants.LOGGER.warn("Something went wrong when reading the config, using default config instead");
            return new ConfigOptions();
        }

        if (jsonConfigOptional.isEmpty()) {
            loadedWithErrors = true;
            Constants.LOGGER.warn("Something went wrong when parsing the config, using default config instead");
            return new ConfigOptions();
        }

        JsonObject jsonConfig = jsonConfigOptional.get();
        ConfigOptions defaultConfig = new ConfigOptions();
        List<Field> classFields = Arrays.stream(defaultConfig.getClass().getDeclaredFields()).toList();
        List<Field> configFields = classFields.stream().filter(field -> !Modifier.isStatic(field.getModifiers())).toList();
        List<String> configFieldNames = configFields.stream().map(Field::getName).toList();

        // Missing fields
        configFieldNames.stream().filter(field -> !jsonConfig.has(field))
                .forEach(missingField -> {
                    Constants.LOGGER.warn("Could not find field '{}' when parsing the config, using default value instead", missingField);
                });

        //Unknown fields
        List<String> unknownFields = jsonConfig.keySet().stream().filter(key -> !configFieldNames.contains(key)).toList();
        unknownFields.forEach(unknownField -> {
            Constants.LOGGER.warn("Unknown config field '{}', ignoring it", unknownField);
        });

        //Wrong type
        final Map<Class<?>, Predicate<JsonElement>> TYPE_CHECKS = Map.of(
                boolean.class, v -> v.isJsonPrimitive() && v.getAsJsonPrimitive().isBoolean(),
                int.class, v -> v.isJsonPrimitive() && v.getAsJsonPrimitive().isNumber()
        );

        List<Field> invalidTypeFields = configFields.stream()
                .filter(field -> jsonConfig.has(field.getName()))
                .filter(field -> !TYPE_CHECKS.getOrDefault(field.getType(), v -> true).test(jsonConfig.get(field.getName()))).toList();

        invalidTypeFields.forEach(field -> {
            Constants.LOGGER.warn("Config field '{}' has an invalid value {}, expected {}",
                    field.getName(), jsonConfig.get(field.getName()), field.getType().getSimpleName());
            jsonConfig.remove(field.getName());
        });

        try {
            return gson.fromJson(jsonConfig, ConfigOptions.class);
        } catch (Exception e) {
            loadedWithErrors = true;
            Constants.LOGGER.warn("An error happened when loading the config, using default config instead: {}", e.getMessage());
            return new ConfigOptions();
        }
    }

    public static ConfigOptions getERConfig() {
        return ENDREM_CONFIG;
    }

    private static Optional<JsonObject> readJsonObject(BufferedReader reader) {
        try {
            JsonElement element = JsonParser.parseReader(reader);
            return element.isJsonObject() ? Optional.of(element.getAsJsonObject()) : Optional.empty();
        } catch (JsonParseException e) {
            Constants.LOGGER.warn("Config file is not valid a JSON file: {}", e.getMessage());
            return Optional.empty();
        }
    }

    private static void writeConfig(File configFile) throws IOException {
        try (FileWriter fw = new FileWriter(configFile.getPath())) {
            gson.toJson(new ConfigOptions(), fw);
        }
    }
}