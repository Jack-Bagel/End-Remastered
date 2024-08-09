package com.teamremastered.endrem.registry;

import com.mojang.serialization.MapCodec;
import com.teamremastered.endrem.EndRemasteredCommon;
import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.client.AncientPortalRenderer;
import com.teamremastered.endrem.client.EyeModel;
import com.teamremastered.endrem.utils.LootInjector;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.*;
import net.neoforged.bus.api.IEventBus;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class RegisterHandler {
    public static void init(IEventBus modEventBus) {
        GLMS.register(modEventBus);
        ERTabs.initRegister(modEventBus);
    }

    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLMS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Constants.MOD_ID);
    private static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<LootInjector.LootInjectorModifier>> LOOT_INJECTION = GLMS.register("loot_injection", LootInjector.LootInjectorModifier.CODEC);

    //TODO: Abstract the registries and subscribe the event inside the init function
    @SubscribeEvent
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

        event.register(Registries.BLOCK_ENTITY_TYPE, registry -> registry.register(EndRemasteredCommon.ModResourceLocation("ancient_portal_frame_entity"),CommonBlockRegistry.ANCIENT_PORTAL_FRAME_ENTITY));
    }


    @SubscribeEvent
    public static void registerBlockEntityRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(CommonBlockRegistry.ANCIENT_PORTAL_FRAME_ENTITY, AncientPortalRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CommonModelRegistry.EYE, EyeModel::createBodyLayer);
    }
}