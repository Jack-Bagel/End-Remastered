package com.teamremastered.endrem.registry;

import com.mojang.serialization.MapCodec;
import com.teamremastered.endrem.EndRemasteredCommon;
import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.item.EyeData;
import com.teamremastered.endrem.utils.VanillaLootInjector;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.*;
import net.neoforged.bus.api.IEventBus;

public class RegisterHandler {
    public static void init(IEventBus modEventBus) {
        GLMS.register(modEventBus);
        ERTrades.init(modEventBus);
        ERCommands.init(modEventBus);
        VanillaLootInjector.init(modEventBus);
        ERTabs.init(modEventBus);

        modEventBus.addListener(RegisterHandler::registerEndRemastered);
        modEventBus.addListener(RegisterHandler::registerEndRemasteredDataPack);
    }

    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLMS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Constants.MOD_ID);

    private static void registerEndRemastered(RegisterEvent event) {

        event.register(Registries.BLOCK, registry -> {
            for (ERRegistryObject<Block> registryObject : CommonBlockRegistry.registerERBlocks()) {
                registry.register(EndRemasteredCommon.ModResourceLocation(registryObject.id()), registryObject.object());
            }
        });

        event.register(Registries.ITEM, registry -> {

            /* Register */
            for (ERRegistryObject<Item> registryObject : CommonItemRegistry.registerERItems()) {
                registry.register(EndRemasteredCommon.ModResourceLocation(registryObject.id()), registryObject.object());
            }
        });


        event.register(Registries.DATA_COMPONENT_TYPE, registry -> {
            for (ERRegistryObject<DataComponentType<?>> registryObject : CommonDataComponentRegistry.registerDataComponent()) {
                registry.register(EndRemasteredCommon.ModResourceLocation(registryObject.id()), registryObject.object());
            }
        });

        event.register(Registries.BLOCK_ENTITY_TYPE, registry -> registry.register(EndRemasteredCommon.ModResourceLocation("end_portal_frame_block_entity"),CommonBlockRegistry.END_PORTAL_FRAME_BLOCK_ENTITY));
    }

    private static void registerEndRemasteredDataPack(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(CommonRegistryKey.EYE_DATA, EyeData.CODEC, EyeData.CODEC);
    }

}