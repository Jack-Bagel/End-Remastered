package com.teamremastered.endrem.registry;

import com.teamremastered.endrem.items.EREnderEye;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ERItems {

    public static void registerItem(String itemName, Item item) {
        Registry.register(Registries.ITEM, EndRemastered.createIdentifier(itemName), item);
    }

    // ==== Eyes ====

    /* Chests */
    public static final Item BLACK_EYE = new EREnderEye(new FabricItemSettings().fireproof().maxCount(16).rarity(Rarity.COMMON));
    public static final Item COLD_EYE = new EREnderEye(new FabricItemSettings().fireproof().maxCount(16).rarity(Rarity.RARE));
    public static final Item CORRUPTED_EYE = new EREnderEye(new FabricItemSettings().fireproof().maxCount(16).rarity(Rarity.COMMON));
    public static final Item LOST_EYE = new EREnderEye(new FabricItemSettings().fireproof().maxCount(16).rarity(Rarity.COMMON));
    public static final Item NETHER_EYE = new EREnderEye(new FabricItemSettings().fireproof().maxCount(16).rarity(Rarity.RARE));
    public static final Item OLD_EYE = new EREnderEye(new FabricItemSettings().fireproof().maxCount(16).rarity(Rarity.COMMON));
    public static final Item ROGUE_EYE = new EREnderEye(new FabricItemSettings().fireproof().maxCount(16).rarity(Rarity.RARE));
    public static final Item CURSED_EYE = new EREnderEye(new FabricItemSettings().fireproof().maxCount(16).rarity(Rarity.COMMON));
    public static final Item EVIL_EYE = new EREnderEye(new FabricItemSettings().fireproof().maxCount(16).rarity(Rarity.RARE));





    /* Entities */
    public static final Item GUARDIAN_EYE = new EREnderEye(new FabricItemSettings().fireproof().maxCount(16).rarity(Rarity.RARE));
    public static final Item MAGICAL_EYE = new EREnderEye(new FabricItemSettings().fireproof().maxCount(16).rarity(Rarity.RARE));
    public static final Item WITHER_EYE = new EREnderEye(new FabricItemSettings().fireproof().maxCount(16).rarity(Rarity.EPIC));

    /* Craftable */
    public static final Item WITCH_EYE = new EREnderEye(new FabricItemSettings().fireproof().maxCount(16).rarity(Rarity.COMMON));
    public static final Item UNDEAD_EYE = new EREnderEye(new FabricItemSettings().fireproof().maxCount(16).rarity(Rarity.EPIC));
    public static final Item EXOTIC_EYE = new EREnderEye(new FabricItemSettings().fireproof().maxCount(16).rarity(Rarity.RARE));

    /* ??? */

    public static final Item CRYPTIC_EYE = new EREnderEye(new FabricItemSettings().fireproof().maxCount(16).rarity(Rarity.EPIC));

    // === Miscellaneous ===
    public static final Item WITCH_PUPIL = new Item(new FabricItemSettings());
    public static final Item UNDEAD_SOUL = new Item(new FabricItemSettings());


    public static void initRegister() {
        /* Chests Eyes */
        registerItem("black_eye", BLACK_EYE);
        registerItem("cold_eye", COLD_EYE);
        registerItem("corrupted_eye", CORRUPTED_EYE);
        registerItem("lost_eye", LOST_EYE);
        registerItem("nether_eye", NETHER_EYE);
        registerItem("old_eye", OLD_EYE);
        registerItem("rogue_eye", ROGUE_EYE);
        registerItem("cursed_eye", CURSED_EYE);
        registerItem("evil_eye", EVIL_EYE);

        /* Entities Eyes */
        registerItem("guardian_eye", GUARDIAN_EYE);
        registerItem("magical_eye", MAGICAL_EYE);
        registerItem("wither_eye", WITHER_EYE);

        /* Craftable Eyes */
        registerItem("witch_eye", WITCH_EYE);
        registerItem("undead_eye", UNDEAD_EYE);
        registerItem("exotic_eye", EXOTIC_EYE);

        /* ??? */
        registerItem("cryptic_eye", CRYPTIC_EYE);

        /* Miscellaneous */
        registerItem("witch_pupil", WITCH_PUPIL);
        registerItem("undead_soul", UNDEAD_SOUL);
    }
}
