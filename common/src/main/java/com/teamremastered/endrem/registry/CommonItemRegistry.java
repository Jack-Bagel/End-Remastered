package com.teamremastered.endrem.registry;

import com.teamremastered.endrem.CommonClass;
import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.items.EREnderEye;
import com.teamremastered.endrem.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
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

    public static void registerDataDrivenEyes() {
        for (String eye_id : Services.CONFIG_HELPER.eyesID()) {
            Item item = new EREnderEye(new Item.Properties());
            ITEMS.add(new ERRegistryObject<>(item, eye_id));
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
        BLACK_EYE = BuiltInRegistries.ITEM.get(CommonClass.ModResourceLocation("black_eye"));
        COLD_EYE = BuiltInRegistries.ITEM.get(CommonClass.ModResourceLocation("cold_eye"));
        CORRUPTED_EYE = BuiltInRegistries.ITEM.get(CommonClass.ModResourceLocation("corrupted_eye"));
        LOST_EYE = BuiltInRegistries.ITEM.get(CommonClass.ModResourceLocation("lost_eye"));
        NETHER_EYE = BuiltInRegistries.ITEM.get(CommonClass.ModResourceLocation("nether_eye"));
        OLD_EYE = BuiltInRegistries.ITEM.get(CommonClass.ModResourceLocation("old_eye"));
        ROGUE_EYE = BuiltInRegistries.ITEM.get(CommonClass.ModResourceLocation("rogue_eye"));
        CURSED_EYE = BuiltInRegistries.ITEM.get(CommonClass.ModResourceLocation("cursed_eye"));
        EVIL_EYE = BuiltInRegistries.ITEM.get(CommonClass.ModResourceLocation("evil_eye"));
        CRYPTIC_EYE = BuiltInRegistries.ITEM.get(CommonClass.ModResourceLocation("cryptic_eye"));
        GUARDIAN_EYE = BuiltInRegistries.ITEM.get(CommonClass.ModResourceLocation("guardian_eye"));
        MAGICAL_EYE = BuiltInRegistries.ITEM.get(CommonClass.ModResourceLocation("magical_eye"));
        WITHER_EYE = BuiltInRegistries.ITEM.get(CommonClass.ModResourceLocation("wither_eye"));
        WITCH_EYE = BuiltInRegistries.ITEM.get(CommonClass.ModResourceLocation("witch_eye"));
        UNDEAD_EYE = BuiltInRegistries.ITEM.get(CommonClass.ModResourceLocation("undead_eye"));
        EXOTIC_EYE = BuiltInRegistries.ITEM.get(CommonClass.ModResourceLocation("exotic_eye"));
    }
}