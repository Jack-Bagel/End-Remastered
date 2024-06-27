package com.teamremastered.endrem.registry;

import com.teamremastered.endrem.blocks.AncientPortalFrame;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ERBlocks {

    public static void registerBlock(String blockName, Block block) {
        Registry.register(Registries.BLOCK, EndRemastered.createIdentifier(blockName), block);
    }

    public static void registerBlockItem(String blockItemName, Item blockItem) {
        Registry.register(Registries.ITEM, EndRemastered.createIdentifier(blockItemName), blockItem);
    }

    // === Blocks ===

    public static final Block ANCIENT_PORTAL_FRAME = new AncientPortalFrame(FabricBlockSettings.of().mapColor(MapColor.GREEN).instrument(Instrument.BASEDRUM) .strength(-1.0F, 3600000.0F).dropsNothing());

    public static void initRegister() {
        registerBlock("ancient_portal_frame", ANCIENT_PORTAL_FRAME);
        registerBlockItem("ancient_portal_frame", new BlockItem(ANCIENT_PORTAL_FRAME, new FabricItemSettings()));
    }
}