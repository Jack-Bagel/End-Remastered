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
    private static String configPath = Services.CONFIG_HELPER.configDirectoryPath() + "/" + Services.CONFIG_HELPER.configFolderName() + "/Eyes" + "/";
    private static ArrayList<JsonEye> EYES_TO_REGISTER = new ArrayList<JsonEye>();

    private static final JsonEye[] END_REMASTERED_EYES =
            {
                    new JsonEye("black_eye", Rarity.COMMON.getSerializedName(),
                            "endrem:minecraft/chests/buried_treasure", FileUtils.createStringArrayList("minecraft:chests/buried_treasure")),
                    new JsonEye("cold_eye", Rarity.RARE.getSerializedName(),
                            "endrem:minecraft/chests/igloo_chest", FileUtils.createStringArrayList("minecraft:chests/igloo_chest")),
                    new JsonEye("corrupted_eye", Rarity.COMMON.getSerializedName(),
                            "endrem:minecraft/chests/pillager_outpost", FileUtils.createStringArrayList("minecraft:chests/pillager_outpost")),
                    new JsonEye( "cursed_eye", Rarity.COMMON.getSerializedName(),
                            "endrem:minecraft/chests/bastion_treasure", FileUtils.createStringArrayList("minecraft:chests/bastion_treasure")),
                    new JsonEye("guardian_eye", Rarity.RARE.getSerializedName(),
                            "endrem:minecraft/entities/elder_guardian", FileUtils.createStringArrayList("minecraft:entities/elder_guardian")),
                    new JsonEye("lost_eye", Rarity.COMMON.getSerializedName(),
                            "endrem:minecraft/chests/abandoned_mineshaft", FileUtils.createStringArrayList("minecraft:chests/abandoned_mineshaft")),
                    new JsonEye("magical_eye", Rarity.RARE.getSerializedName(),
                            "endrem:minecraft/entities/evoker", FileUtils.createStringArrayList("minecraft:entities/evoker", "minecraft:chests/woodland_mansion")),
                    new JsonEye("nether_eye", Rarity.RARE.getSerializedName(),
                            "endrem:minecraft/chests/nether_bridge", FileUtils.createStringArrayList("minecraft:chests/nether_bridge")),
                    new JsonEye("old_eye", Rarity.COMMON.getSerializedName(),
                            "endrem:minecraft/chests/desert_pyramid", FileUtils.createStringArrayList("minecraft:chests/desert_pyramid")),
                    new JsonEye( "rogue_eye", Rarity.RARE.getSerializedName(),
                            "endrem:minecraft/chests/jungle_temple", FileUtils.createStringArrayList("minecraft:chests/jungle_temple")),
                    new JsonEye("evil_eye", Rarity.RARE.getSerializedName()),
                    new JsonEye("cryptic_eye", Rarity.EPIC.getSerializedName()),
                    new JsonEye("guardian_eye", Rarity.RARE.getSerializedName(),
                            "endrem:minecraft/entities/elder_guardian", FileUtils.createStringArrayList("minecraft:entities/elder_guardian")),
                    new JsonEye("magical_eye", Rarity.RARE.getSerializedName(),
                            "endrem:minecraft/entities/evoker", FileUtils.createStringArrayList("minecraft:entities/evoker")),
                    new JsonEye("wither_eye", Rarity.EPIC.getSerializedName(),
                            "endrem:minecraft/entities/wither", FileUtils.createStringArrayList("minecraft:entities/wither")),
                    new JsonEye("witch_eye", Rarity.COMMON.getSerializedName()),
                    new JsonEye("undead_eye", Rarity.EPIC.getSerializedName()),
                    new JsonEye("exotic_eye", Rarity.RARE.getSerializedName())};
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private String id;
    private String rarity;
    private String loot_to_inject_id;
    private ArrayList<String> loot_tables_id;

    public JsonEye(String id, String rarity, String loot_to_inject_id, ArrayList<String> loot_tables_id) {
        this.id = id;
        this.rarity = rarity;
        this.loot_to_inject_id = loot_to_inject_id;
        this.loot_tables_id = loot_tables_id;
    }

    public JsonEye(String id, String rarity) {
        this(id, rarity, "minecraft:empty", FileUtils.createStringArrayList());
    }

    //TODO: Fix loot_tables_id only writing the first element of the array when created
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
        EYES_TO_REGISTER = load();
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

    public ArrayList<ResourceLocation> getLootTablesID() {
        ArrayList<ResourceLocation> result = new ArrayList<>();
        for (String resource : this.loot_tables_id) {
            result.add(ResourceLocation.parse(resource));
        }
        return result;
    }

    public ResourceLocation getLootToInjectID() {
        return ResourceLocation.parse(this.loot_to_inject_id);
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
        return EYES_TO_REGISTER;
    }
}
