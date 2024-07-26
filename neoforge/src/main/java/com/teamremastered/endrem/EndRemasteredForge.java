package com.teamremastered.endrem;


import com.teamremastered.endrem.registry.RegisterHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class EndRemasteredForge {
    public EndRemasteredForge(IEventBus modEventBus, ModContainer container) {
        RegisterHandler.init(modEventBus);
        Constants.LOGGER.info("Hello NeoForge world!");
        CommonClass.init();
    }
}