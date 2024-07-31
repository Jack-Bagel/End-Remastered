package com.teamremastered.endrem;

import com.teamremastered.endrem.registry.RegisterHandler;
import net.fabricmc.api.ClientModInitializer;

public class EndRemasteredFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        RegisterHandler.clientInit();
    }
}