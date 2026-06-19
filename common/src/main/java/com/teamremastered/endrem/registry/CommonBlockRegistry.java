package com.teamremastered.endrem.registry;

import com.teamremastered.endrem.block.EndPortalFrameBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CommonBlockRegistry {
    private static final List<ERRegistryObject<Block>> BLOCKS = new ArrayList<>();

    public static final BlockEntityType<EndPortalFrameBlockEntity> END_PORTAL_FRAME_BLOCK_ENTITY =
            BlockEntityType.Builder.of(EndPortalFrameBlockEntity::new, Blocks.END_PORTAL_FRAME).build(null);

    public static Block createBlock(Block block, String id) {
        BLOCKS.add(new ERRegistryObject<>(block, id));
        return block;
    }

    public static Collection<ERRegistryObject<Block>> registerERBlocks() {
        return BLOCKS;
    }
}