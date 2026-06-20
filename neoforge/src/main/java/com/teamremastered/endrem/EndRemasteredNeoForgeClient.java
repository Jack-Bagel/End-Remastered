package com.teamremastered.endrem;

import com.teamremastered.endrem.registry.RegisterHandler;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class EndRemasteredNeoForgeClient {
    public EndRemasteredNeoForgeClient(IEventBus modEventBus) {
        RegisterHandler.initClient(modEventBus);
    }
}