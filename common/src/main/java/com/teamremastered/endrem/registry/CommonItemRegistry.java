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
    public static final Item DUMMY_EYE = createItem(new EREnderEye(new Item.Properties().rarity(Rarity.COMMON)), "dummy_eye");

    public static Item createItem(Item item, String id) {
        ITEMS.add(new ERRegistryObject<>(item, id));
        return item;
    }

    public static Collection<ERRegistryObject<Item>> registerERItems() {
        return ITEMS;
    }
}