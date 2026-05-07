package com.teamremastered.endrem.registry;

import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.EndRemasteredCommon;
import com.teamremastered.endrem.item.JsonEye;
import com.teamremastered.endrem.item.EREnderEye;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CommonItemRegistry {
    public static final List<ERRegistryObject<Item>> ITEMS = new ArrayList<>();

    public static Item BLACK_EYE;
    public static Item COLD_EYE;
    public static Item CORRUPTED_EYE;
    public static Item LOST_EYE;
    public static Item NETHER_EYE;
    public static Item OLD_EYE;
    public static Item ROGUE_EYE;
    public static Item CURSED_EYE;
    public static Item EVIL_EYE;
    public static Item CRYPTIC_EYE;
    public static Item GUARDIAN_EYE;
    public static Item MAGICAL_EYE;
    public static Item WITHER_EYE;
    public static Item WITCH_EYE;
    public static Item UNDEAD_EYE;
    public static Item EXOTIC_EYE;

    public static final Item WITCH_PUPIL = createItem(new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("endrem", "witch_pupil")))),"witch_pupil");
    public static final Item UNDEAD_SOUL = createItem(new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("endrem", "undead_soul" )))),"undead_soul");
    public static final Item ANCIENT_PORTAL_FRAME = createItem(new BlockItem(CommonBlockRegistry.ANCIENT_PORTAL_FRAME, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Constants.MOD_ID, "ancient_portal_frame")))), "ancient_portal_frame");

    public static Item createItem(Item item, String id) {
        ITEMS.add(new ERRegistryObject<>(item, id));
        return item;
    }

    //TODO: Handle wrong eye ID
    public static void registerEyes() {
        for (JsonEye eye : JsonEye.getEyes()) {
            Item item = new EREnderEye(new Item.Properties().rarity(eye.getRarity()).setId(ResourceKey.create(Registries.ITEM, EndRemasteredCommon.ModIdentifier(eye.getID()))));
            ITEMS.add(new ERRegistryObject<>(item, eye.getID()));
        }
    }

    public static Collection<ERRegistryObject<Item>> registerERItems() {
        return ITEMS;
    }

    public static void initializeEyes() {
        BLACK_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModIdentifier("black_eye")).get().value();
        COLD_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModIdentifier("cold_eye")).get().value();
        CORRUPTED_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModIdentifier("corrupted_eye")).get().value();
        LOST_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModIdentifier("lost_eye")).get().value();
        NETHER_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModIdentifier("nether_eye")).get().value();
        OLD_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModIdentifier("old_eye")).get().value();
        ROGUE_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModIdentifier("rogue_eye")).get().value();
        CURSED_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModIdentifier("cursed_eye")).get().value();
        EVIL_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModIdentifier("evil_eye")).get().value();
        CRYPTIC_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModIdentifier("cryptic_eye")).get().value();
        GUARDIAN_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModIdentifier("guardian_eye")).get().value();
        MAGICAL_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModIdentifier("magical_eye")).get().value();
        WITHER_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModIdentifier("wither_eye")).get().value();
        WITCH_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModIdentifier("witch_eye")).get().value();
        UNDEAD_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModIdentifier("undead_eye")).get().value();
        EXOTIC_EYE = BuiltInRegistries.ITEM.get(EndRemasteredCommon.ModIdentifier("exotic_eye")).get().value();
    }
}