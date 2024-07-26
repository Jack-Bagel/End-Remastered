package com.teamremastered.endrem.item;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.teamremastered.endrem.platform.Services;
import com.teamremastered.endrem.util.FileUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Rarity;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class JsonEye {
    private static String configPath = Services.CONFIG_HELPER.configDirectoryPath() + "/EndRemastered/Eyes/";
    private static ArrayList<JsonEye> eyesToRegister = new ArrayList<JsonEye>();
    private static final JsonEye[] END_REMASTERED_EYES =
            {
               new JsonEye("black_eye", Rarity.COMMON.getSerializedName()),
                    new JsonEye("cold_eye", Rarity.RARE.getSerializedName()),
                    new JsonEye("corrupted_eye", Rarity.COMMON.getSerializedName()),
                    new JsonEye("lost_eye", Rarity.COMMON.getSerializedName()),
                    new JsonEye("nether_eye", Rarity.RARE.getSerializedName()),
                    new JsonEye("old_eye", Rarity.COMMON.getSerializedName()),
                    new JsonEye( "rogue_eye", Rarity.RARE.getSerializedName()),
                    new JsonEye( "cursed_eye", Rarity.COMMON.getSerializedName()),
                    new JsonEye("evil_eye", Rarity.RARE.getSerializedName()),
                    new JsonEye("cryptic_eye", Rarity.EPIC.getSerializedName()),

                    new JsonEye("guardian_eye", Rarity.RARE.getSerializedName()),
                    new JsonEye("magical_eye", Rarity.RARE.getSerializedName()),
                    new JsonEye("wither_eye", Rarity.EPIC.getSerializedName()),

                    new JsonEye("witch_eye", Rarity.COMMON.getSerializedName()),
                    new JsonEye("undead_eye", Rarity.EPIC.getSerializedName()),
                    new JsonEye("exotic_eye", Rarity.RARE.getSerializedName())
            };
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private String id;
    private String rarity;

    public JsonEye(String id, String rarity) {
        this.id = id;
        this.rarity = rarity;
    }

    public JsonEye(String id) {
        this(id,"common");
    }

    public static void create() throws IOException {
        File configFolder = new File(configPath);
        if (!configFolder.exists() || Objects.requireNonNull(configFolder.listFiles()).length == 0) {

            if (!configFolder.exists()) {
                configFolder.mkdirs();
            }

            for (JsonEye eye : END_REMASTERED_EYES) {
                try (FileWriter fw = new FileWriter(configPath + eye.getID().getPath() + ".json")) {
                    gson.toJson(eye, fw);
                }
            }
        }
        eyesToRegister = load();
    }

    private static ArrayList<JsonEye> load() throws FileNotFoundException {
        ArrayList<JsonEye> eyes = new ArrayList<>();
        ArrayList<String> fileNames = FileUtils.getFilesFromDirectory(configPath, ".json");
        for (String fileName : fileNames) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(configPath + fileName));
            JsonEye eye = gson.fromJson(bufferedReader, JsonEye.class);
            eyes.add(eye);
        }

        return eyes;
    }

    public ResourceLocation getID() {
        return ResourceLocation.parse(this.id);
    }

    public Rarity getRarity() {
        Rarity result = switch (this.rarity) {
            case "common" -> Rarity.COMMON;
            case "uncommon" -> Rarity.UNCOMMON;
            case "rare" -> Rarity.RARE;
            case "epic" -> Rarity.EPIC;
            default -> Rarity.COMMON;
        };

        return result;
    }

    public static ArrayList<JsonEye> getEyes() {
        return eyesToRegister;
    }


}
