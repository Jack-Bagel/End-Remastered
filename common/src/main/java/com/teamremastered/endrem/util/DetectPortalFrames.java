package com.teamremastered.endrem.util;

import com.google.common.base.Predicates;
import com.teamremastered.endrem.block.EndPortalFrameBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;

public class DetectPortalFrames {

    public static boolean isFrameAbsent(Level levelIn, UseOnContext itemUse, BlockPos pos) {
        BlockPattern.BlockPatternMatch blockpattern$patternhelper = getCompletedPortalShape().find(levelIn, pos);

        if (blockpattern$patternhelper != null) {
            BlockPos frontTopLeft = blockpattern$patternhelper.getFrontTopLeft().offset(-4, 0, -4);

            for (int i = 0; i < 5; ++i) {
                for (int j = 0; j < 5; ++j) {
                    BlockPos blockPos = frontTopLeft.offset(i, 0, j);
                    BlockEntity blockEntity = levelIn.getBlockEntity(blockPos);

                    if (blockEntity instanceof EndPortalFrameBlockEntity endPortalFrameBlockEntity) {
                        if (!endPortalFrameBlockEntity.isEmpty() && endPortalFrameBlockEntity.getEyeAsItem().equals(itemUse.getItemInHand().getItem())) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }

        return false;
    }

    public static BlockPattern getCompletedPortalShape() {
        return BlockPatternBuilder.start()
            .aisle("?vvv?", ">???<", ">???<", ">???<", "?^^^?")
            .where('?', BlockInWorld.hasState(BlockStatePredicate.ANY))

            .where(
                    '^', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.END_PORTAL_FRAME)
                    .where(EndPortalFrameBlock.FACING, Predicates.equalTo(Direction.SOUTH)))
            )
            .where('>', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.END_PORTAL_FRAME)
                    .where(EndPortalFrameBlock.FACING, Predicates.equalTo(Direction.WEST)))
            )
            .where('v', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.END_PORTAL_FRAME)
                    .where(EndPortalFrameBlock.FACING, Predicates.equalTo(Direction.NORTH)))
            )
            .where('<', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.END_PORTAL_FRAME)
                    .where(EndPortalFrameBlock.FACING, Predicates.equalTo(Direction.EAST)))
            )
            .build();
    }
}
