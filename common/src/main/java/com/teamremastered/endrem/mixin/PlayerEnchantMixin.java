package com.teamremastered.endrem.mixin;

import com.teamremastered.endrem.component.EyeDataComponent;
import com.teamremastered.endrem.registry.CommonDataComponentRegistry;
import com.teamremastered.endrem.registry.CommonItemRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(EnchantmentMenu.class)
public class PlayerEnchantMixin {

    @Inject(method = "clickMenuButton", at = @At(value = "RETURN", ordinal = 2))
    private void isEnchanting(Player player, int id, CallbackInfoReturnable<Boolean> info) {
        Random random = new Random();
        int maxValue = 120;
        int randomNumber = random.nextInt(maxValue);

        if (player != null && !player.level().isClientSide()) {
            if (randomNumber == maxValue - 1) {
                ItemStack stack = new ItemStack(CommonItemRegistry.DUMMY_EYE);
                stack.set(CommonDataComponentRegistry.DATA_EYE_COMPONENT,
                        new EyeDataComponent(ResourceLocation.parse("endrem:cryptic_eye")));
                player.getInventory().add(stack);
            }
        }
    }
}