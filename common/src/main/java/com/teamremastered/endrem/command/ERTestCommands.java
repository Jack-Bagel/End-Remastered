package com.teamremastered.endrem.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.datafixers.util.Pair;
import com.teamremastered.endrem.EndRemasteredCommon;
import com.teamremastered.endrem.component.EyeDataComponent;
import com.teamremastered.endrem.item.EyeData;
import com.teamremastered.endrem.registry.CommonDataComponentRegistry;
import com.teamremastered.endrem.registry.CommonItemRegistry;
import com.teamremastered.endrem.registry.CommonRegistryKey;
import com.teamremastered.endrem.util.EyeDataManager;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ERTestCommands {

    public static int testPortal(CommandContext<CommandSourceStack> context) {
        Optional<HolderLookup.RegistryLookup<EyeData>> registryLookup = context.getSource().getServer().registryAccess().lookup(CommonRegistryKey.EYE_DATA);
        Optional<ServerPlayer> player = Optional.ofNullable(context.getSource().getPlayer());
        BlockPos portalPos = player.orElseThrow().getOnPos().offset(2, 1, 0);
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

        // Give all the eyes to the player
        EyeDataManager.getDynamicEyes(registryLookup).forEach(eye -> {
            ItemStack stack = new ItemStack(CommonItemRegistry.DUMMY_EYE);
            stack.set(CommonDataComponentRegistry.DATA_EYE_COMPONENT, new EyeDataComponent(eye.id()));
            stack.setCount(2);
            context.getSource().getPlayer().addItem(stack);
        });

        context.getSource().sendSuccess(() -> Component.literal("Created Portal"), false);
        return 1;
    }

    public static int testEyesLootTables(CommandContext<CommandSourceStack> context) {
        Optional<HolderLookup.RegistryLookup<EyeData>> registryLookup = context.getSource().getServer().registryAccess().lookup(CommonRegistryKey.EYE_DATA);
        if (!context.getSource().getLevel().isClientSide()) {
            ArrayList<Pair<ResourceLocation, ResourceKey<LootTable>>> lootTablesIDs = makeLootTableIDs(registryLookup);
            LootParams params = new LootParams.Builder(context.getSource().getLevel())
                    .withParameter(LootContextParams.ORIGIN, context.getSource().getPosition())
                    .create(LootContextParamSets.COMMAND);

                for (var pairKeyId : lootTablesIDs) {
                    LootTable lootTable = context.getSource().getLevel().getServer().reloadableRegistries().getLootTable(pairKeyId.getSecond());

                    int count = 0;
                    final int total = 1000;
                    for (int i = 0; i < total; i++) {
                        List<ItemStack> generatedLoot = lootTable.getRandomItems(params);

                       boolean isEyeFoundInLootTable = generatedLoot.stream().anyMatch(generatedStack -> {
                            EyeDataComponent generatedStackComponent = generatedStack.getOrDefault(CommonDataComponentRegistry.DATA_EYE_COMPONENT,
                                    new EyeDataComponent(ResourceLocation.withDefaultNamespace("empty")));

                            if (generatedStackComponent.id().equals(ResourceLocation.withDefaultNamespace("empty"))) {
                                return false;
                            }

                            return pairKeyId.getFirst().equals(generatedStackComponent.id());
                        });

                       if (isEyeFoundInLootTable) {
                           count++;
                       }

                        // Check if our item ID matches the generated stack. Since pairKeyId saves the component id of the eyes we dont have to filter out dummy_eye.
                        boolean isItemFoundInLootTable = generatedLoot.stream().anyMatch(itemStack -> {
                                      return BuiltInRegistries.ITEM.getKey(itemStack.getItem()).equals(pairKeyId.getFirst());
                       });

                        if (isItemFoundInLootTable) {
                            count++;
                        }
                    }

                    final float finalOdds = (float)count/(float)total;
                    String fmtFinalOdds = String.format("%.2f", finalOdds) + "%";
                    Component itemName = getItemName(pairKeyId.getFirst())
                            .copy()
                            .withStyle(ChatFormatting.GREEN)
                            .withStyle(style -> style.withHoverEvent(
                                    new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                            Component.literal("ID: ").withStyle(ChatFormatting.GRAY)
                                                    .append(Component.literal(pairKeyId.getFirst().toString()).withStyle(ChatFormatting.AQUA)
                                                    .append(Component.literal("\nLoot table: ").withStyle(ChatFormatting.GRAY)
                                                    .append(Component.literal(pairKeyId.getSecond().location().toString()).withStyle(ChatFormatting.AQUA)))
                                    ))));

                    Component info = Component.empty()
                            .append(Component.literal("Found "))
                            .append(itemName)
                            .append(Component.literal(" with weight of "))
                            .append(Component.literal(fmtFinalOdds).withStyle(ChatFormatting.GREEN));
                    context.getSource().sendSuccess(() -> info, false);
                }
            }

        return 1;
    }

    private static ArrayList<Pair<ResourceLocation, ResourceKey<LootTable>>> makeLootTableIDs(Optional<HolderLookup.RegistryLookup<EyeData>> registryLookup) {
        ArrayList<Pair<ResourceLocation, ResourceKey<LootTable>>> namedIdentifiers = new ArrayList<>();

        for (EyeData eye : EyeDataManager.getDynamicEyes(registryLookup)) {
            ItemStack eyeStack = new ItemStack(CommonItemRegistry.DUMMY_EYE);
            eyeStack.set(CommonDataComponentRegistry.DATA_EYE_COMPONENT, new EyeDataComponent(eye.id()));

            for (ResourceLocation lootTableID : eye.lootTablesID()) {
                ResourceKey<LootTable> lootTableKey = ResourceKey.create(Registries.LOOT_TABLE, lootTableID);
                namedIdentifiers.add(new Pair<>(eye.id(), lootTableKey));
            }
        }

        ResourceLocation undeadSoulLootID = ResourceLocation.withDefaultNamespace("entities/skeleton_horse");
        ResourceLocation pupilLootID = ResourceLocation.withDefaultNamespace("entities/witch");
        ResourceKey<LootTable> undeadLootTableKey = ResourceKey.create(Registries.LOOT_TABLE, undeadSoulLootID);
        ResourceKey<LootTable> pupilLootTableKey = ResourceKey.create(Registries.LOOT_TABLE, pupilLootID);

        namedIdentifiers.add(new Pair<>(EndRemasteredCommon.ModResourceLocation("undead_soul"), undeadLootTableKey));
        namedIdentifiers.add(new Pair<>(EndRemasteredCommon.ModResourceLocation("witch_pupil"), pupilLootTableKey));

        return namedIdentifiers;
    }

    private static Component getItemName(ResourceLocation id) {
        // Normal item: use its registry name
        Optional<Item> item = BuiltInRegistries.ITEM.getOptional(id);
        if (item.isPresent()) {
            return item.get().getDescription();
        }

        // Not a registry item -> treat as dummy eye component id
        String key = String.format("item.%s.%s", id.getNamespace(), id.getPath().replace('/', '.'));
        return Component.translatableWithFallback(key, id.toString());
    }

}