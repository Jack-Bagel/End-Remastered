package com.teamremastered.endrem;

import com.teamremastered.endrem.registry.RegisterHandlerClient;
import net.fabricmc.api.ClientModInitializer;

public class EndRemasteredFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        RegisterHandlerClient.init();
    }
}