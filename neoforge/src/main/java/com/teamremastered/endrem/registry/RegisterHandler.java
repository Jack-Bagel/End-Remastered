package com.teamremastered.endrem.registry;

import com.mojang.serialization.MapCodec;
import com.teamremastered.endrem.EndRemasteredCommon;
import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.client.EndPortalFrameRenderer;
import com.teamremastered.endrem.client.EyeModel;
import com.teamremastered.endrem.utils.VanillaLootInjector;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.*;
import net.neoforged.bus.api.IEventBus;

public class RegisterHandler {
    public static void init(IEventBus modEventBus) {
        GLMS.register(modEventBus);
        ERTabs.init(modEventBus);
        ERTrades.init(modEventBus);
        ERCommands.init(modEventBus);
        VanillaLootInjector.init(modEventBus);
        modEventBus.addListener(RegisterHandler::registerEndRemastered);

        // Server only
        if (FMLEnvironment.dist.isDedicatedServer()) {
        }

        // Client only
        if (FMLEnvironment.dist.isClient()) {
            modEventBus.addListener(RegisterHandler::registerBlockEntityRenderer);
            modEventBus.addListener(RegisterHandler::registerLayerDefinition);
        }
    }

    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLMS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Constants.MOD_ID);

    //TODO: Abstract the registries and subscribe the event inside the init function
    public static void registerEndRemastered(RegisterEvent event) {

        event.register(Registries.BLOCK, registry -> {
            for (ERRegistryObject<Block> registryObject : CommonBlockRegistry.registerERBlocks()) {
                registry.register(EndRemasteredCommon.ModResourceLocation(registryObject.id()), registryObject.object());
            }
        });

        event.register(Registries.ITEM, registry -> {
            /* Pre Register */
            CommonItemRegistry.registerEyes();

            /* Register */
            for (ERRegistryObject<Item> registryObject : CommonItemRegistry.registerERItems()) {
                registry.register(EndRemasteredCommon.ModResourceLocation(registryObject.id()), registryObject.object());
            }

            /* Post Register */
            CommonItemRegistry.initializeEyes();
        });

        event.register(Registries.BLOCK_ENTITY_TYPE, registry -> registry.register(EndRemasteredCommon.ModResourceLocation("end_portal_frame_block_entity"),CommonBlockRegistry.END_PORTAL_FRAME_BLOCK_ENTITY));
    }


    public static void registerBlockEntityRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(CommonBlockRegistry.END_PORTAL_FRAME_BLOCK_ENTITY, EndPortalFrameRenderer::new);
    }

    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CommonModelRegistry.EYE, EyeModel::createBodyLayer);
    }
}