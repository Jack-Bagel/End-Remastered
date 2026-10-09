package com.teamremastered.endrem.mixin;

import com.teamremastered.endrem.EndRemasteredCommon;
import com.teamremastered.endrem.component.EyeDataComponent;
import com.teamremastered.endrem.item.EyeData;
import com.teamremastered.endrem.registry.CommonDataComponentRegistry;
import com.teamremastered.endrem.registry.CommonItemRegistry;
import com.teamremastered.endrem.registry.CommonRegistryKey;
import com.teamremastered.endrem.util.EyeDataManager;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.Random;

@Mixin(EnchantmentMenu.class)
public class PlayerEnchantMixin {

    @Inject(method = "clickMenuButton", at = @At(value = "RETURN", ordinal = 2))
    private void isEnchanting(Player player, int id, CallbackInfoReturnable<Boolean> info) {
        Random random = new Random();
        int maxValue = 2;
        int randomNumber = random.nextInt(maxValue);

        // The eye could be deleted from the datapack, so we make sure it exists.
        if (player != null && !player.level().isClientSide()) {
            Optional<HolderLookup.RegistryLookup<EyeData>> lookup = player.registryAccess().lookup(CommonRegistryKey.EYE_DATA);
            ResourceLocation crypticEyeID = EndRemasteredCommon.ModResourceLocation("cryptic_eye");
            boolean has_eye = EyeDataManager.isEyeLoaded(crypticEyeID, lookup);

           if (has_eye && randomNumber == maxValue - 1) {
               ItemStack stack = new ItemStack(CommonItemRegistry.DUMMY_EYE);
               stack.set(CommonDataComponentRegistry.DATA_EYE_COMPONENT, new EyeDataComponent(crypticEyeID));

               if(!player.addItem(stack)) {
                   player.drop(stack, false);
               }
           }
        }
    }
}