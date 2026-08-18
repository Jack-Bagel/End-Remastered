package com.teamremastered.endrem.registry;

import com.teamremastered.endrem.EndRemasteredCommon;
import com.teamremastered.endrem.client.AncientPortalRenderer;
import com.teamremastered.endrem.client.EyeModel;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.fabricmc.fabric.impl.client.rendering.BlockEntityRendererRegistryImpl;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.Collection;

public class RegisterHandler {

    public static void init() {
        /* Blocks & Items */
        register(BuiltInRegistries.BLOCK, CommonBlockRegistry.registerERBlocks());
        CommonItemRegistry.registerEyes();
        register(BuiltInRegistries.ITEM, CommonItemRegistry.registerERItems());
        CommonItemRegistry.initializeEyes();
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, EndRemasteredCommon.ModIdentifier("ancient_portal_frame_entity"), CommonBlockRegistry.ANCIENT_PORTAL_FRAME_ENTITY);

        /* Miscellaneous */
        ERTabs.init();
        ERCommands.init();
    }

    public static void clientInit() {
        ModelLayerRegistry.registerModelLayer(CommonModelRegistry.EYE, EyeModel::createBodyLayer);

        BlockEntityRendererRegistryImpl.register(CommonBlockRegistry.ANCIENT_PORTAL_FRAME_ENTITY, AncientPortalRenderer::new);
    }

    private static <T> void register(Registry<T> registry, Collection<ERRegistryObject<T>> objects) {
        for (ERRegistryObject<T> object : objects) {
            Registry.register(registry, EndRemasteredCommon.ModIdentifier(object.id()), object.object());
        }
    }
}