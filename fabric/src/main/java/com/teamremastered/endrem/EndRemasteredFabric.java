package com.teamremastered.endrem;

import com.teamremastered.endrem.registry.RegisterHandler;
import com.teamremastered.endrem.util.LootInjection;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;

public class EndRemasteredFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        LootInjection.register();
        CommonClass.init();
        RegisterHandler.init();
    }
}