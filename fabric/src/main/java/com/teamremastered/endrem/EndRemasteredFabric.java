package com.teamremastered.endrem;

import com.teamremastered.endrem.registry.RegisterHandler;
import com.teamremastered.endrem.util.EyeDataManager;
import com.teamremastered.endrem.util.LootInjection;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class EndRemasteredFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        LootInjection.register();
        EndRemasteredCommon.init();
        RegisterHandler.init();


    }
}