package com.teamremastered.endrem;

import com.teamremastered.endrem.registry.RegisterHandler;
import com.teamremastered.endrem.util.LootInjection;
import net.fabricmc.api.ModInitializer;

public class EndRemasteredFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        LootInjection.register();
        EndRemasteredCommon.init();
        RegisterHandler.init();
    }
}