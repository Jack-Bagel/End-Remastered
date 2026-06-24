package com.teamremastered.endrem.command;

import com.mojang.brigadier.context.CommandContext;
import com.teamremastered.endrem.EndRemasteredCommon;
import com.teamremastered.endrem.component.EyeDataComponent;
import com.teamremastered.endrem.item.SerializedEye;
import com.teamremastered.endrem.registry.CommonDataComponentRegistry;
import com.teamremastered.endrem.registry.CommonItemRegistry;
import com.teamremastered.endrem.util.EyeDataManager;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.List;

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

    public static int testLootTables(CommandContext<CommandSourceStack> context) {
        if (!context.getSource().getLevel().isClientSide()) {
            EyeDataManager eyeDataManager = EyeDataManager.getInstance();
            context.getSource().sendSuccess(() -> Component.literal("--Generate Eyes Loot Tables--\n"), false);
            for (var entry : eyeDataManager.getLoadedEyes().entrySet()) {
                ItemStack eyeStack = new ItemStack(CommonItemRegistry.DUMMY_EYE);
                eyeStack.set(CommonDataComponentRegistry.DATA_EYE_COMPONENT, new EyeDataComponent(entry.getKey()));
                for (ResourceLocation lootTableID : entry.getValue().lootTablesID()) {
                    ResourceKey<LootTable> lootTableKey = ResourceKey.create(Registries.LOOT_TABLE, lootTableID);

                    LootParams params = new LootParams.Builder(context.getSource().getLevel())
                            .withParameter(LootContextParams.ORIGIN, context.getSource().getPosition())
                            .create(LootContextParamSets.COMMAND);
                    LootTable lootTable = context.getSource().getLevel().getServer().reloadableRegistries().getLootTable(lootTableKey);

                    int count = 0;
                    final int total = 1000;
                    for (int i = 0; i < total; i++) {
                        List<ItemStack> stacks = lootTable.getRandomItems(params);
                        for (ItemStack stack: stacks) {
                            EyeDataComponent eyeDataComponent = stack.getOrDefault(CommonDataComponentRegistry.DATA_EYE_COMPONENT,
                                    new EyeDataComponent(ResourceLocation.withDefaultNamespace("empty")));
                            if (eyeDataComponent.id().equals(entry.getKey())) {
                                count++;
                            }
                        }
                    }

                    final float finalOdds = (float)count/(float)total;

                    Component info = Component.empty()
                            .append(Component.literal("Generated "))
                            .append(Component.literal(lootTableID.toString()).withStyle(ChatFormatting.YELLOW))
                            .append(Component.literal("\nFound "))
                            .append(Component.literal(eyeStack.getDisplayName().getString()).withStyle(ChatFormatting.GREEN))
                            .append(Component.literal(" with weight of "))
                            .append(Component.literal(finalOdds + "%").withStyle(ChatFormatting.GREEN))
                            .append(Component.literal("\n"));

                    context.getSource().sendSuccess(() -> info, false);
                }
            }
        }

        return 1;
    }
}