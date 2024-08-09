package com.teamremastered.endrem;


import com.teamremastered.endrem.registry.RegisterHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class EndRemasteredNeoForge {
    public EndRemasteredNeoForge(IEventBus modEventBus) {
        EndRemasteredCommon.init();
        RegisterHandler.init(modEventBus);
    }
}