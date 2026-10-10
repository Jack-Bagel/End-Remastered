package com.teamremastered.endrem.mixin;

import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.config.ConfigHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EyeOfEnder.class)
public class EyeOfEnderMixin {

    @Shadow
    private boolean surviveAfterDeath;
    @Unique
    RandomSource random = RandomSource.create();

    @Inject(method = "signalTo", at = @At("TAIL"))
    private void modifyEyeDeathProbability(BlockPos pos, CallbackInfo ci) {
        int randomValue = random.nextInt(100);

        this.surviveAfterDeath = randomValue >= ConfigHandler.EYE_BREAK_PROBABILITY;
    }
}