package com.teamremastered.endrem.registry;

import com.teamremastered.endrem.blocks.AncientPortalFrame;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CommonBlockRegistry {
    private static final List<ERRegistryObject<Block>> BLOCKS = new ArrayList<>();
    public static final Block ANCIENT_PORTAL_FRAME = createBlock(new AncientPortalFrame(), "ancient_portal_frame");

    public static Block createBlock(Block block, String id) {
        BLOCKS.add(new ERRegistryObject<>(block, id));
        return block;
    }

    public static Collection<ERRegistryObject<Block>> registerERBlocks() {
        return BLOCKS;
    }

    public static void init() {}
}
