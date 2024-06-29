package com.teamremastered.endrem.registry;

import com.teamremastered.endrem.items.EREnderEye;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CommonItemRegistry {
    private static final List<ERRegistryObject<Item>> ITEMS = new ArrayList<>();
    public static final Item BLACK_EYE = createItem(new EREnderEye(new Item.Properties()), "black_eye");
    public static final Item COLD_EYE = createItem(new EREnderEye(new Item.Properties()), "cold_eye");
    public static final Item CORRUPTED_EYE = createItem(new EREnderEye(new Item.Properties()), "corrupted_eye");
    public static final Item LOST_EYE = createItem(new EREnderEye(new Item.Properties()), "lost_eye");
    public static final Item NETHER_EYE = createItem(new EREnderEye(new Item.Properties()), "nether_eye");
    public static final Item OLD_EYE = createItem(new EREnderEye(new Item.Properties()), "old_eye");
    public static final Item ROGUE_EYE = createItem(new EREnderEye(new Item.Properties()), "rogue_eye");
    public static final Item CURSED_EYE = createItem(new EREnderEye(new Item.Properties()), "cursed_eye");
    public static final Item EVIL_EYE = createItem(new EREnderEye(new Item.Properties()), "evil_eye");
    public static final Item CRYPTIC_EYE = createItem(new EREnderEye(new Item.Properties()), "cryptic_eye");

    public static final Item GUARDIAN_EYE = createItem(new EREnderEye(new Item.Properties()), "guardian_eye");
    public static final Item MAGICAL_EYE = createItem(new EREnderEye(new Item.Properties()), "magical_eye");
    public static final Item WITHER_EYE = createItem(new EREnderEye(new Item.Properties()), "wither_eye");

    public static final Item WITCH_EYE = createItem(new EREnderEye(new Item.Properties()), "witch_eye");
    public static final Item UNDEAD_EYE = createItem(new EREnderEye(new Item.Properties()), "undead_eye");
    public static final Item EXOTIC_EYE = createItem(new EREnderEye(new Item.Properties()), "exotic_eye");

    public static final Item WITCH_PUPIL = createItem(new EREnderEye(new Item.Properties()), "witch_pupil");
    public static final Item UNDEAD_SOUL = createItem(new EREnderEye(new Item.Properties()), "undead_soul");
    public static final Item ANCIENT_PORTAL_FRAME = createItem(new BlockItem(CommonBlockRegistry.ANCIENT_PORTAL_FRAME, new Item.Properties()), "ancient_portal_frame");

    public static Item createItem(Item item, String id) {
        ITEMS.add(new ERRegistryObject<>(item, id));
        return item;
    }

    public static Collection<ERRegistryObject<Item>> registerERItems() {
        return ITEMS;
    }
}
