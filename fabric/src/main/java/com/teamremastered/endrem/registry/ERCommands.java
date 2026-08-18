package com.teamremastered.endrem.registry;

import com.teamremastered.endrem.command.ERTestCommands;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.server.permissions.Permissions;

public class ERCommands {

    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("endrem")
                    .requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_GAMEMASTER))
                    .then(Commands.literal("test")
                        .then(Commands.literal("portal")
                            .executes(ERTestCommands::testPortal))
                        .then(Commands.literal("loot_tables")
                                .executes(ERTestCommands::testLootTables))));
        });
    }
}