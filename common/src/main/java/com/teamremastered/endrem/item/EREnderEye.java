package com.teamremastered.endrem.item;

import com.teamremastered.endrem.Constants;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;

import java.util.List;

@MethodsReturnNonnullByDefault
public class EREnderEye extends EnderEyeItem {
    public EREnderEye(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext world, List<Component> tooltip, TooltipFlag tooltipContext) {
        String translationKey = String.format("item.%s.%s.description", Constants.MOD_ID, BuiltInRegistries.ITEM.getKey(this).getPath());
        tooltip.add(Component.translatable(translationKey));
    }
}