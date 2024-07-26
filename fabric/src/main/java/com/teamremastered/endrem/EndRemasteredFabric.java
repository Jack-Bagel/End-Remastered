package com.teamremastered.endrem;

import com.teamremastered.endrem.config.ERConfigHandler;
import com.teamremastered.endrem.registry.RegisterHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class EndRemasteredFabric implements ModInitializer {
    @Override
    public void onInitialize() {

        RegisterHandler.init();

        // Register Config
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            ERConfigHandler.load();
            Constants.LOGGER.info("PREPARING FOR RELOAD!");
        });
        CommonClass.init();
    }
}