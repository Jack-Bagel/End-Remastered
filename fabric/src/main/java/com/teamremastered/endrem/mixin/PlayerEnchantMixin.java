package com.teamremastered.endrem.mixin;

import com.teamremastered.endrem.config.ERConfigHandler;
import com.teamremastered.endrem.registry.ERItems;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(EnchantmentScreenHandler.class)
public class PlayerEnchantMixin {

    @Inject(method = "onButtonClick", at = @At(value = "RETURN", ordinal = 2))
    private void isEnchanting(final PlayerEntity player, final int id, final CallbackInfoReturnable<Boolean> info) {
        Random random = new Random();
        int maxValue = 120;
        int randomNumber = random.nextInt(maxValue);
        if (!player.getWorld().isClient && player != null) {
            if (ERConfigHandler.IS_CRYPTIC_EYE_OBTAINABLE && randomNumber == maxValue - 1) {
        player.getInventory().insertStack(new ItemStack(ERItems.CRYPTIC_EYE));
            }
        }
    }
}
