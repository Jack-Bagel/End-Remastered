package com.teamremastered.endrem.mixin;

import com.teamremastered.endrem.config.ERConfigHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnderEyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderEyeItem.class)
@SuppressWarnings("unused")

//TODO: make a callback
public class EnderEyeItemMixin {
    @Inject(method = "useOn",
        at = @At(value = "HEAD"),
        cancellable = true)
    private void DisableUsingEnderEyes(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        if (!ERConfigHandler.USE_EYE_OF_ENDER) {
            Level level = context.getLevel();
            BlockPos blockPos = context.getClickedPos();
            BlockState blockState = level.getBlockState(blockPos);
            if (blockState.is(Blocks.END_PORTAL_FRAME)) {
                cir.setReturnValue(InteractionResult.SUCCESS);
                context.getPlayer().displayClientMessage(Component.translatable("block.endrem.ender_eye.warning"), true);
            }
        }
    }

    @Inject(method = "use",
        at = @At(value = "HEAD"),
        cancellable = true)
    private void DisableThrowingEnderEyes(Level level, Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        if (!ERConfigHandler.THROW_EYE_OF_ENDER) {
            ItemStack itemStack = player.getItemInHand(interactionHand);
            cir.setReturnValue(InteractionResultHolder.success(itemStack));
            player.displayClientMessage(Component.translatable("block.endrem.ender_eye.warning"), true);
        }
    }
}