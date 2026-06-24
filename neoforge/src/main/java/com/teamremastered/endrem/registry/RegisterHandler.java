package com.teamremastered.endrem.registry;

import com.mojang.serialization.MapCodec;
import com.teamremastered.endrem.EndRemasteredCommon;
import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.client.EndPortalFrameRenderer;
import com.teamremastered.endrem.client.EyeDynamicBakedModel;
import com.teamremastered.endrem.client.EyeModel;
import com.teamremastered.endrem.util.EyeModelManager;
import com.teamremastered.endrem.utils.VanillaLootInjector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.*;
import net.neoforged.bus.api.IEventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterHandler {
    public static void init(IEventBus modEventBus) {
        GLMS.register(modEventBus);
        ERTabs.init(modEventBus);
        ERTrades.init(modEventBus);
        ERCommands.init(modEventBus);
        VanillaLootInjector.init(modEventBus);
        modEventBus.addListener(RegisterHandler::registerEndRemastered);
    }

    public static void initClient(IEventBus modEventBus) {
        modEventBus.addListener(RegisterHandler::registerBlockEntityRenderer);
        modEventBus.addListener(RegisterHandler::registerLayerDefinition);
        modEventBus.addListener(RegisterHandler::registerAdditionalModels);
        modEventBus.addListener(RegisterHandler::modifyBakingResult);
    }

    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLMS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Constants.MOD_ID);

    //TODO: Abstract the registries and subscribe the event inside the init function
    private static void registerEndRemastered(RegisterEvent event) {

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
        });

        event.register(Registries.DATA_COMPONENT_TYPE, registry -> {
            for (ERRegistryObject<DataComponentType<?>> registryObject : CommonDataComponentRegistry.registerDataComponent()) {
                registry.register(EndRemasteredCommon.ModResourceLocation(registryObject.id()), registryObject.object());
            }
        });

        event.register(Registries.BLOCK_ENTITY_TYPE, registry -> registry.register(EndRemasteredCommon.ModResourceLocation("end_portal_frame_block_entity"),CommonBlockRegistry.END_PORTAL_FRAME_BLOCK_ENTITY));
    }


    private static void registerBlockEntityRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(CommonBlockRegistry.END_PORTAL_FRAME_BLOCK_ENTITY, EndPortalFrameRenderer::new);
    }

    private static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CommonModelRegistry.EYE, EyeModel::createBodyLayer);
    }

    public static void registerAdditionalModels(ModelEvent.RegisterAdditional event) {
        ResourceManager manager = Minecraft.getInstance().getResourceManager();
        List<ResourceLocation> extraModels = EyeModelManager.discoverCustomTextures(manager);
        extraModels.forEach( extraModel -> event.register(ModelResourceLocation.standalone(extraModel)));
    }

    public static void modifyBakingResult(ModelEvent.ModifyBakingResult event) {
        Map<ResourceLocation, BakedModel> eyeModelCache = new HashMap<>();

        // Cache eye models — NeoForge gives you the full map directly
        event.getModels().forEach((mrl, model) -> {
            if (mrl.getVariant().equals(ModelResourceLocation.STANDALONE_VARIANT) &&
                    mrl.id().getPath().startsWith("eye/")) {
                eyeModelCache.put(mrl.id(), model);
            }
        });

        // Replace dummy_eye inventory model
        ModelResourceLocation inventoryMRL = new ModelResourceLocation(
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "dummy_eye"), "inventory"
        );

        BakedModel original = event.getModels().get(inventoryMRL);
        if (original != null) {
            event.getModels().put(inventoryMRL,
                    new EyeDynamicBakedModel(original, eyeModelCache::get));
        }
    }
}