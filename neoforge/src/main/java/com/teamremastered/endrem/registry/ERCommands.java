package com.teamremastered.endrem.registry;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.command.ERTestCommands;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class ERCommands {

    @SubscribeEvent
    public static void onRegisterCommandsEvent(RegisterCommandsEvent event) {
        event.getDispatcher().register(
            LiteralArgumentBuilder.<CommandSourceStack>literal("endrem")
                    .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
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
