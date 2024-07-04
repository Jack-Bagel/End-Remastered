package com.teamremastered.endrem.util;

import com.google.common.collect.ImmutableList;
import com.teamremastered.endrem.blocks.AncientPortalFrame;
import com.teamremastered.endrem.blocks.ERFrameProperties;
import com.teamremastered.endrem.registry.CommonBlockRegistry;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;

public class ERPortalPredicate implements Predicate<BlockState> {
    private final ImmutableList<Block> VALID_BLOCKS = ImmutableList.of(
            Blocks.END_PORTAL_FRAME,
            CommonBlockRegistry.ANCIENT_PORTAL_FRAME
    );

    private final Direction DIRECTION;
    private ERFrameProperties EXCLUDED_EYE;
    private boolean REQUIRE_ANCIENT_FRAME = false;

    private ERPortalPredicate(Direction directionIn) {
        this.DIRECTION = directionIn;
    }

    public boolean test(BlockState blockstate) {
        if (blockstate != null && this.VALID_BLOCKS.contains(blockstate.getBlock())) {
            if (blockstate.getBlock() != CommonBlockRegistry.ANCIENT_PORTAL_FRAME && this.REQUIRE_ANCIENT_FRAME) {
                return false;
            }

            if (blockstate.getBlock().equals(CommonBlockRegistry.ANCIENT_PORTAL_FRAME) && this.EXCLUDED_EYE != null) {
                if (blockstate.getValue(AncientPortalFrame.EYE).equals(EXCLUDED_EYE)) {
                    return false;
                }
            }

            return blockstate.getValue(HorizontalDirectionalBlock.FACING) == this.DIRECTION;
        } else {
            return false;
        }
    }

    public static ERPortalPredicate facing(Direction direction) {
        return new ERPortalPredicate(direction);
    }

    public ERPortalPredicate withoutEye(ERFrameProperties eye) {
        this.EXCLUDED_EYE = eye;
        return this;
    }

    public ERPortalPredicate requireAncientFrame(boolean var) {
        this.REQUIRE_ANCIENT_FRAME = var;
        return this;
    }
}