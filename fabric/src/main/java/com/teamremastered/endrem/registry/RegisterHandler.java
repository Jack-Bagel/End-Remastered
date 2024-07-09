package com.teamremastered.endrem.registry;

import com.teamremastered.endrem.CommonClass;
import com.teamremastered.endrem.config.ERConfigHandler;
import com.teamremastered.endrem.util.LootInjection;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.Collection;

public class RegisterHandler {

    public static void init() {
        /* Miscellaneous */
        ERTrades.registerVillagerTrades();
        ERTabs.initRegister();
        LootInjection.initRegister();

        /* Blocks & Items */
        register(BuiltInRegistries.BLOCK, CommonBlockRegistry.registerERBlocks());
        register(BuiltInRegistries.ITEM, CommonItemRegistry.registerERItems());
    }

    public static <T> void register(Registry<T> registry, Collection<ERRegistryObject<T>> objects) {
        for (ERRegistryObject<T> object : objects) {
            Registry.register(registry, CommonClass.ModResourceLocation(object.id()), object.object());
        }
    }
}