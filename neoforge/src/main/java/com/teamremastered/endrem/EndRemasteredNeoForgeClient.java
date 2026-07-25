package com.teamremastered.endrem;

import com.teamremastered.endrem.registry.RegisterHandlerClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class EndRemasteredNeoForgeClient {
    public EndRemasteredNeoForgeClient(IEventBus modEventBus) {
        RegisterHandlerClient.init(modEventBus);
    }
}