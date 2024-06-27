package com.teamremastered.endrem.mixin;

import com.teamremastered.endrem.config.ERConfigHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.EnderEyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(EnderEyeItem.class)
@SuppressWarnings("unused")

//TODO: Add a config option for the methods
public class EnderEyeItemMixin {
    @Inject(method = "useOnBlock",
        at = @At(value = "HEAD"),
        cancellable = true)
    private void DisableUsingEnderEyes(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        if (!ERConfigHandler.USE_EYE_OF_ENDER) {
            World world = context.getWorld();
            BlockPos blockPos = context.getBlockPos();
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.isOf(Blocks.END_PORTAL_FRAME)) {
                cir.setReturnValue(ActionResult.SUCCESS);
                context.getPlayer().sendMessage(Text.translatable("block.endrem.ender_eye.warning"), true);
            }
        }
    }

    @Inject(method = "use",
        at = @At(value = "HEAD"),
        cancellable = true)
    private void DisableThrowingEnderEyes(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if (!ERConfigHandler.THROW_EYE_OF_ENDER) {
            ItemStack itemStack = user.getStackInHand(hand);
            cir.setReturnValue(TypedActionResult.success(itemStack));
            user.sendMessage(Text.translatable("block.endrem.ender_eye.warning"), true);
        }
    }
}
