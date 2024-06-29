package com.teamremastered.endrem.registry;

import com.teamremastered.endrem.CommonClass;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
public class ERTabs {

    public static final ResourceKey<CreativeModeTab> ITEM_GROUP = ResourceKey.create(Registries.CREATIVE_MODE_TAB, CommonClass.ModResourceLocation("endrem_tab"));
    public static void initRegister() {
    Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ITEM_GROUP, FabricItemGroup.builder()
        .title(Component.translatable("itemGroup.endrem.endrem_tab"))
        .icon(() -> new ItemStack(CommonItemRegistry.COLD_EYE))
        .displayItems((enabledFeatures, entries) -> {
            entries.accept(CommonItemRegistry.BLACK_EYE);
            entries.accept(CommonItemRegistry.COLD_EYE);
            entries.accept(CommonItemRegistry.CORRUPTED_EYE);
            entries.accept(CommonItemRegistry.LOST_EYE);
            entries.accept(CommonItemRegistry.NETHER_EYE);
            entries.accept(CommonItemRegistry.OLD_EYE);
            entries.accept(CommonItemRegistry.ROGUE_EYE);
            entries.accept(CommonItemRegistry.CURSED_EYE);
            entries.accept(CommonItemRegistry.EVIL_EYE);

            entries.accept(CommonItemRegistry.GUARDIAN_EYE);
            entries.accept(CommonItemRegistry.MAGICAL_EYE);
            entries.accept(CommonItemRegistry.WITHER_EYE);

            entries.accept(CommonItemRegistry.WITCH_EYE);
            entries.accept(CommonItemRegistry.UNDEAD_EYE);
            entries.accept(CommonItemRegistry.EXOTIC_EYE);

            entries.accept(CommonItemRegistry.CRYPTIC_EYE);

            entries.accept(CommonItemRegistry.WITCH_PUPIL);
            entries.accept(CommonItemRegistry.UNDEAD_SOUL);
        }).build());
    }
}
