package com.teamremastered.endrem.mixin;

import com.teamremastered.endrem.util.EyeDataManager;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(ReloadableServerRegistries.class)
public class ReloadableServerRegistriesMixin {

    @Inject(method = "reload", at = @At(value = "RETURN"))
    private static void loadDataEye(LayeredRegistryAccess<RegistryLayer> registries, ResourceManager resourceManager, Executor backgroundExecutor, CallbackInfoReturnable<CompletableFuture<LayeredRegistryAccess<RegistryLayer>>> cir) {
        EyeDataManager.getInstance().loadEyes(resourceManager);
    }
}
