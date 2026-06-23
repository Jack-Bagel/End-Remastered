package com.teamremastered.endrem.item;

import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.component.EyeDataComponent;
import com.teamremastered.endrem.registry.CommonDataComponentRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CubicSpline;
import net.minecraft.world.item.*;

import java.util.ArrayList;
import java.util.List;

@MethodsReturnNonnullByDefault
public class EREnderEye extends EnderEyeItem {

    public EREnderEye(Properties properties) {
        super(properties.component(CommonDataComponentRegistry.DATA_EYE_COMPONENT, new EyeDataComponent(
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID,"black_eye"))));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext world, List<Component> tooltip, TooltipFlag tooltipContext) {
        EyeDataComponent dataComponent = itemStack.get(CommonDataComponentRegistry.DATA_EYE_COMPONENT);

        String translationKey = String.format("item.%s.%s.description", dataComponent.id().getNamespace(), dataComponent.id().getPath());
        tooltip.add(Component.translatable(translationKey));
        tooltip.add(Component.translatable("Id: %s", dataComponent.id()).withStyle(ChatFormatting.GOLD));
    }

    @Override
    public Component getName(ItemStack stack) {
        EyeDataComponent dataComponent = stack.get(CommonDataComponentRegistry.DATA_EYE_COMPONENT);
        String translationKey = String.format("item.%s.%s", dataComponent.id().getNamespace(), dataComponent.id().getPath());
        return Component.translatable(translationKey);
    }
}