package com.teamremastered.endrem.item;

import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.EndRemasteredCommon;
import com.teamremastered.endrem.component.EyeDataComponent;
import com.teamremastered.endrem.config.ConfigHandler;
import com.teamremastered.endrem.registry.CommonDataComponentRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;

import java.util.List;

@MethodsReturnNonnullByDefault
public class EREnderEye extends EnderEyeItem {

    public EREnderEye(Properties properties) {
        super(properties.component(CommonDataComponentRegistry.DATA_EYE_COMPONENT, new EyeDataComponent(
                EndRemasteredCommon.ModResourceLocation("dummy_eye"))));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipContext) {
        if (ConfigHandler.HIDE_DESCRIPTION) {
            return;
        }

        EyeDataComponent dataComponent = stack.get(CommonDataComponentRegistry.DATA_EYE_COMPONENT);

        if (dataComponent == null) {
            Constants.LOGGER.error(getName(stack).getString() + " does not have a data component. The eye is invalid");
            return;
        }

        String translationKey = String.format("item.%s.%s.description", dataComponent.id().getNamespace(), dataComponent.id().getPath());
        tooltip.add(Component.translatable(translationKey));
    }

    @Override
    public Component getName(ItemStack stack) {
        EyeDataComponent dataComponent = stack.getOrDefault(CommonDataComponentRegistry.DATA_EYE_COMPONENT,
                new EyeDataComponent(EndRemasteredCommon.ModResourceLocation("empty")));
        String translationKey = String.format("item.%s.%s", dataComponent.id().getNamespace(), dataComponent.id().getPath());
        return Component.translatable(translationKey);
    }
}