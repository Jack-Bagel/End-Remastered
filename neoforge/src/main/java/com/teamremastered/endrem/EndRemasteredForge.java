package com.teamremastered.endrem;


import com.teamremastered.endrem.config.ERConfig;
import com.teamremastered.endrem.registry.RegisterHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class EndRemasteredForge {
    public static final String CONFIG_FILE = String.format("%s.toml", Constants.MOD_ID);

    public EndRemasteredForge(IEventBus modEventBus, ModContainer container) {
        ERConfig config = new ERConfig(container);
        config.load();
        RegisterHandler.init(modEventBus);
        Constants.LOGGER.info("Hello NeoForge world!");
        CommonClass.init();
    }
}