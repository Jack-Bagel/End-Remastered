package com.teamremastered.endrem.registry;

import com.teamremastered.endrem.command.ERTestCommands;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;

public class ERCommands {

    public static void init() {

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("endrem_test")
                    .requires(source -> source.hasPermission(2))
                    .then(Commands.literal("portal")
                        .executes(ERTestCommands::testPortal)));
        });

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("endrem_test")
                    .requires(source -> source.hasPermission(2))
                    .then(Commands.literal("loot_tables")
                            .executes(ERTestCommands::testLootTables)));
        });
    }
}
