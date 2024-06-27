package com.teamremastered.endrem.registers;

import com.teamremastered.endrem.items.EREnderEye;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;


@SuppressWarnings("unused")
public class ERItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EndRemastered.MOD_ID);

    public static void initRegister(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }

    // ==== Eyes ====


    // chests
    public static final RegistryObject<Item> BLACK_EYE = ITEMS.register("black_eye", () -> new EREnderEye(new Item.Properties().fireResistant().rarity(Rarity.COMMON).stacksTo(16)));
    public static final RegistryObject<Item> COLD_EYE = ITEMS.register("cold_eye", () -> new EREnderEye(new Item.Properties().fireResistant().rarity(Rarity.RARE).stacksTo(16)));
    public static final RegistryObject<Item> CORRUPTED_EYE = ITEMS.register("corrupted_eye", () -> new EREnderEye(new Item.Properties().fireResistant().rarity(Rarity.COMMON).stacksTo(16)));
    public static final RegistryObject<Item> LOST_EYE = ITEMS.register("lost_eye", () -> new EREnderEye(new Item.Properties().fireResistant().rarity(Rarity.COMMON).stacksTo(16)));
    public static final RegistryObject<Item> NETHER_EYE = ITEMS.register("nether_eye", () -> new EREnderEye(new Item.Properties().fireResistant().rarity(Rarity.RARE).stacksTo(16)));
    public static final RegistryObject<Item> OLD_EYE = ITEMS.register("old_eye", () -> new EREnderEye(new Item.Properties().fireResistant().rarity(Rarity.COMMON).stacksTo(16)));
    public static final RegistryObject<Item> ROGUE_EYE = ITEMS.register("rogue_eye", () -> new EREnderEye(new Item.Properties().fireResistant().rarity(Rarity.RARE).stacksTo(16)));
    public static final RegistryObject<Item> CURSED_EYE = ITEMS.register("cursed_eye", () -> new EREnderEye(new Item.Properties().fireResistant().rarity(Rarity.COMMON).stacksTo(16)));
    public static final RegistryObject<Item> EVIL_EYE = ITEMS.register("evil_eye", () -> new EREnderEye(new Item.Properties().fireResistant().rarity(Rarity.RARE).stacksTo(16)));

    // entities
    public static final RegistryObject<Item> GUARDIAN_EYE = ITEMS.register("guardian_eye", () -> new EREnderEye(new Item.Properties().fireResistant().rarity(Rarity.RARE).stacksTo(16)));
    public static final RegistryObject<Item> MAGICAL_EYE = ITEMS.register("magical_eye", () -> new EREnderEye(new Item.Properties().fireResistant().rarity(Rarity.RARE).stacksTo(16)));
    public static final RegistryObject<Item> WITHER_EYE = ITEMS.register("wither_eye", () -> new EREnderEye(new Item.Properties().fireResistant().rarity(Rarity.EPIC).stacksTo(16)));

    // crafts
    public static final RegistryObject<Item> WITCH_EYE = ITEMS.register("witch_eye", () -> new EREnderEye(new Item.Properties().fireResistant().rarity(Rarity.COMMON).stacksTo(16)));
    public static final RegistryObject<Item> UNDEAD_EYE = ITEMS.register("undead_eye", () -> new EREnderEye(new Item.Properties().fireResistant().rarity(Rarity.EPIC).stacksTo(16)));
    public static final RegistryObject<Item> EXOTIC_EYE = ITEMS.register("exotic_eye", () -> new EREnderEye(new Item.Properties().fireResistant().rarity(Rarity.RARE).stacksTo(16)));

    // ???

    public static final RegistryObject<Item> CRYPTIC_EYE = ITEMS.register("cryptic_eye", () -> new EREnderEye(new Item.Properties().fireResistant().rarity(Rarity.EPIC).stacksTo(16)));

    // ==== Other Items ====
    public static final RegistryObject<Item> WITCH_PUPIL = ITEMS.register("witch_pupil", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> UNDEAD_SOUL = ITEMS.register("undead_soul", () -> new Item(new Item.Properties()));
}
