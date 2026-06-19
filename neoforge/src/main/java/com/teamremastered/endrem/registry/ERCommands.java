package com.teamremastered.endrem.registry;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.teamremastered.endrem.command.ERTestCommands;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public class ERCommands {

    public static void init(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.addListener(ERCommands::onRegisterCommandsEvent);
    }

    public static void onRegisterCommandsEvent(RegisterCommandsEvent event) {
        event.getDispatcher().register(
            LiteralArgumentBuilder.<CommandSourceStack>literal("endrem")
                    .requires(cs -> cs.hasPermission(Commands.LEVEL_GAMEMASTERS))
                    .then(Commands.literal("test")
                    .then(Commands.literal("portal")
                            .executes(ERTestCommands::testPortal)
                    )
                    .then(Commands.literal("loot_tables")
                            .executes(ERTestCommands::testLootTables)
                    )
                )
        );
    }
}