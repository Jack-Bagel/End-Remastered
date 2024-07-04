package com.teamremastered.endrem.registry;

import com.teamremastered.endrem.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ERTabs {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);

    public static void initRegister(IEventBus modEventBus) {
        TABS.register(modEventBus);
    }

    public static final Supplier<CreativeModeTab> EYES_TAB = TABS.register("endrem_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.endrem.endrem_tab"))
                    .icon(() -> new ItemStack(CommonItemRegistry.EXOTIC_EYE))
                    .displayItems((featureFlags, output) -> {
                        output.accept(CommonItemRegistry.BLACK_EYE);
                        output.accept(CommonItemRegistry.COLD_EYE);
                        output.accept(CommonItemRegistry.CORRUPTED_EYE);
                        output.accept(CommonItemRegistry.LOST_EYE);
                        output.accept(CommonItemRegistry.NETHER_EYE);
                        output.accept(CommonItemRegistry.OLD_EYE);
                        output.accept(CommonItemRegistry.ROGUE_EYE);
                        output.accept(CommonItemRegistry.CURSED_EYE);
                        output.accept(CommonItemRegistry.EVIL_EYE);

                        output.accept(CommonItemRegistry.GUARDIAN_EYE);
                        output.accept(CommonItemRegistry.MAGICAL_EYE);
                        output.accept(CommonItemRegistry.WITHER_EYE);

                        output.accept(CommonItemRegistry.WITCH_EYE);
                        output.accept(CommonItemRegistry.UNDEAD_EYE);
                        output.accept(CommonItemRegistry.EXOTIC_EYE);

                        output.accept(CommonItemRegistry.CRYPTIC_EYE);

                        output.accept(CommonItemRegistry.WITCH_PUPIL);
                        output.accept(CommonItemRegistry.UNDEAD_SOUL);
                    } )
                    .build()
    );
}