package com.teamremastered.endrem.mixin.accessor;

import net.minecraft.world.entity.projectile.EyeOfEnder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EyeOfEnder.class)
public interface EyeOfEnderEntityAccessorMixin {
    @Accessor("surviveAfterDeath")
    public void setSurviveAfterDeath(boolean dropsItem);

}