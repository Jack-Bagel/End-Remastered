package com.teamremastered.endrem.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EyeOfEnderEntity.class)
public interface EyeOfEnderEntityAccessorMixin {
    @Accessor("dropsItem")
    public void setDropsItem(boolean dropsItem);

}
