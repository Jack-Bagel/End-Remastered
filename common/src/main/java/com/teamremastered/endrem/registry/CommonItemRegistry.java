package com.teamremastered.endrem.registry;

import com.teamremastered.endrem.item.EREnderEye;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CommonItemRegistry {
    public static final List<ERRegistryObject<Item>> ITEMS = new ArrayList<>();

    public static final Item WITCH_PUPIL = createItem(new Item(new Item.Properties()),"witch_pupil");
    public static final Item UNDEAD_SOUL = createItem(new Item(new Item.Properties()),"undead_soul");
    public static final Item DUMMY_EYE = new EREnderEye(new Item.Properties().rarity(Rarity.COMMON));

    public static Item createItem(Item item, String id) {
        ITEMS.add(new ERRegistryObject<>(item, id));
        return item;
    }

    //TODO: Handle wrong eye ID
    public static void registerEyes() {
        ITEMS.add(new ERRegistryObject<>(DUMMY_EYE, "dummy_eye"));
    }

    public static Collection<ERRegistryObject<Item>> registerERItems() {
        return ITEMS;
    }
}