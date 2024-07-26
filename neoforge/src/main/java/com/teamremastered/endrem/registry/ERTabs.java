package com.teamremastered.endrem.registry;

import com.teamremastered.endrem.CommonClass;
import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.item.JsonEye;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
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
                        for (JsonEye eye : JsonEye.getEyes()) {
                            output.accept(BuiltInRegistries.ITEM.get(CommonClass.ModResourceLocation(eye.getID().getPath())));
                        }
                        output.accept(CommonItemRegistry.WITCH_PUPIL);
                        output.accept(CommonItemRegistry.UNDEAD_SOUL);

                    }).build()
    );
}