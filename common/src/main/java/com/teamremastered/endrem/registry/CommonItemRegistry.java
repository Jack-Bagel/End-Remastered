package com.teamremastered.endrem.registry;

import com.teamremastered.endrem.EndRemasteredCommon;
import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.item.JsonEye;
import com.teamremastered.endrem.item.EREnderEye;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CommonItemRegistry {
    private static final List<ERRegistryObject<Item>> ITEMS = new ArrayList<>();

    public static  Item BLACK_EYE;
    public static  Item COLD_EYE;
    public static  Item CORRUPTED_EYE;
    public static  Item LOST_EYE;
    public static  Item NETHER_EYE;
    public static  Item OLD_EYE;
    public static  Item ROGUE_EYE;
    public static  Item CURSED_EYE;
    public static Item EVIL_EYE;
    public static Item CRYPTIC_EYE;
    public static  Item GUARDIAN_EYE;
    public static  Item MAGICAL_EYE;
    public static  Item WITHER_EYE;
    public static  Item WITCH_EYE;
    public static  Item UNDEAD_EYE;
    public static  Item EXOTIC_EYE;

    public static final Item WITCH_PUPIL = createItem(new Item(new Item.Properties()),"witch_pupil");
    public static final Item UNDEAD_SOUL = createItem(new Item(new Item.Properties()),"undead_soul");
    public static final Item ANCIENT_PORTAL_FRAME = createItem(new BlockItem(CommonBlockRegistry.ANCIENT_PORTAL_FRAME, new Item.Properties()), "ancient_portal_frame");

    public static Item createItem(Item item, String id) {
        ITEMS.add(new ERRegistryObject<>(item, id));
        return item;
    }

    //TODO: Handle wrong eye ID
    public static void registerEyes() {
        for (JsonEye eye : JsonEye.getEyes()) {
            Item item = new EREnderEye(new Item.Properties().rarity(eye.getRarity()));
            ITEMS.add(new ERRegistryObject<>(item, eye.getID().getPath()));
        }
    }

    public static Collection<ERRegistryObject<Item>> registerERItems() {
        Constants.LOGGER.info("ELEMENTS INSIDE ITEMS");
        for (ERRegistryObject<Item> item : ITEMS) {
            Constants.LOGGER.info(item.id());
        }
        return ITEMS;
    }

    public static void initializeEyes() {
        BLACK_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModResourceLocation("black_eye"));
        COLD_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModResourceLocation("cold_eye"));
        CORRUPTED_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModResourceLocation("corrupted_eye"));
        LOST_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModResourceLocation("lost_eye"));
        NETHER_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModResourceLocation("nether_eye"));
        OLD_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModResourceLocation("old_eye"));
        ROGUE_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModResourceLocation("rogue_eye"));
        CURSED_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModResourceLocation("cursed_eye"));
        EVIL_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModResourceLocation("evil_eye"));
        CRYPTIC_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModResourceLocation("cryptic_eye"));
        GUARDIAN_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModResourceLocation("guardian_eye"));
        MAGICAL_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModResourceLocation("magical_eye"));
        WITHER_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModResourceLocation("wither_eye"));
        WITCH_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModResourceLocation("witch_eye"));
        UNDEAD_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModResourceLocation("undead_eye"));
        EXOTIC_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModResourceLocation("exotic_eye"));
    }
}