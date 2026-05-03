package com.teamremastered.endrem.command;

import com.mojang.brigadier.context.CommandContext;
import com.teamremastered.endrem.registry.CommonItemRegistry;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class ERTestCommands {

    public static int testPortal(CommandContext<CommandSourceStack> context) {
        BlockPos playerPos = context.getSource().getPlayer().getOnPos();
        BlockPos portalPos = playerPos.offset(2, 1, 0);

        BlockState endPortalFrameState = Blocks.END_PORTAL_FRAME.defaultBlockState().setValue(EndPortalFrameBlock.FACING,  Direction.SOUTH);
        for (int i = 0; i < 3; i++) {
            portalPos = portalPos.offset(1, 0, 0);
            context.getSource().getLevel().setBlock(portalPos, endPortalFrameState, 2);
        }

        endPortalFrameState = endPortalFrameState.setValue(EndPortalFrameBlock.FACING,  Direction.WEST);
        portalPos = portalPos.offset(1, 0, 0);
        for (int i = 0; i < 3; i++) {
            portalPos = portalPos.offset(0, 0, 1);
            context.getSource().getLevel().setBlock(portalPos, endPortalFrameState, 2);
        }

        endPortalFrameState = endPortalFrameState.setValue(EndPortalFrameBlock.FACING,  Direction.NORTH);
        portalPos = portalPos.offset(0, 0, 1);
        for (int i = 0; i < 3; i++) {
            portalPos = portalPos.offset(-1, 0, 0);
            context.getSource().getLevel().setBlock(portalPos, endPortalFrameState, 2);
        }

        endPortalFrameState = endPortalFrameState.setValue(EndPortalFrameBlock.FACING,  Direction.EAST);
        portalPos = portalPos.offset(-1, 0, 0);
        for (int i = 0; i < 3; i++) {
            portalPos = portalPos.offset(0, 0, -1);
            context.getSource().getLevel().setBlock(portalPos, endPortalFrameState, 2);
        }

        CommonItemRegistry.ITEMS.stream()
                .filter(item -> item.id().contains("eye"))
                .forEach(eye -> context.getSource().getPlayer().addItem(new ItemStack(eye.object(), 2)));

        context.getSource().sendSuccess(() -> Component.literal("Created Portal"), false);
        return 1;
    }

//    public static int testLootTables(CommandContext<CommandSourceStack> context) {
//        if (!context.getSource().getLevel().isClientSide) {
//            BlockPos playerPos = context.getSource().getPlayer().getOnPos();
//            BlockPos chestPos = playerPos.offset(0, 1, 2);
//            ChestBlockEntity chestBlockEntity = new ChestBlockEntity(chestPos, Blocks.CHEST.defaultBlockState());
//
//            // TODO: Get all eyes inside the config, get the loot table and add it to the chests
//            for (int i = 0; i < 10; i++) {
//                chestBlockEntity.setLootTable(BuiltInLootTables.IGLOO_CHEST);
//                context.getSource().getLevel().setBlock(chestPos, Blocks.CHEST.defaultBlockState(), 2);
//                context.getSource().getLevel().setBlockEntity(chestBlockEntity);
//            }
//
//
//
//            context.getSource().sendSuccess(() -> Component.literal("Generated Loot Tables"), false);
//        }
//        return 1;
//    }
}