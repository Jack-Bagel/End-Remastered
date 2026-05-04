package com.teamremastered.endrem.registry;

import com.teamremastered.endrem.command.ERTestCommands;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;

public class ERCommands {

    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("endrem")
                .then(Commands.literal("test")
                        .then(Commands.literal("portal")
                            .requires(source -> source.hasPermission(Commands.LEVEL_GAMEMASTERS))
                            .executes(ERTestCommands::testPortal))
                        .then(Commands.literal("loot_tables")
                                .requires(source -> source.hasPermission(Commands.LEVEL_GAMEMASTERS))
                                .executes(ERTestCommands::testLootTables))));

        });
    }
}