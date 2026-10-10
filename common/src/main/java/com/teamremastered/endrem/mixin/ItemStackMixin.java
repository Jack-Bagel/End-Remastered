package com.teamremastered.endrem.mixin;

import com.teamremastered.endrem.component.EyeDataComponent;
import com.teamremastered.endrem.item.EyeData;
import com.teamremastered.endrem.registry.CommonDataComponentRegistry;
import com.teamremastered.endrem.registry.CommonRegistryKey;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Optional;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @ModifyVariable(method = "getRarity", at = @At(value = "STORE", ordinal = 0), ordinal = 0)
    private Rarity modifyEyeRarity(Rarity rarity) {
        ItemStack self = (ItemStack) (Object) this;

        EyeDataComponent eyeDataComponent = self.get(CommonDataComponentRegistry.DATA_EYE_COMPONENT);
        ClientLevel clientLevel = Minecraft.getInstance().level;
        if (eyeDataComponent == null || clientLevel == null) {
            return rarity;
        }

        ResourceLocation id = eyeDataComponent.id();
        Optional<Registry<EyeData>> registry = clientLevel.registryAccess().registry(CommonRegistryKey.EYE_DATA);

            if (registry.isPresent()) {
                EyeData eye = registry.get().get(id);
                if (eye != null && eye.rarity() != null) {
                    return eye.rarity();
                }
            }

        return rarity;
    }

}
