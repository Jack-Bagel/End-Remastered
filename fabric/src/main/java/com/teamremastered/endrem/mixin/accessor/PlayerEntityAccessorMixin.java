package com.teamremastered.endrem.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerEntity.class)
public interface PlayerEntityAccessorMixin {
    @Accessor
    PlayerAbilities getAbilities();
}