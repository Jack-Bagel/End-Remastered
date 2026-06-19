package com.teamremastered.endrem.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.teamremastered.endrem.block.EndPortalFrameBlockEntity;
import com.teamremastered.endrem.config.ConfigHandler;
import com.teamremastered.endrem.util.DetectPortalFrames;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnderEyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderEyeItem.class)
public class EnderEyeItemMixin {

    private final int GETFIELD = 180;

    private EndPortalFrameBlockEntity endPortalFrameBlockEntity;

    @Inject(method = "useOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"), cancellable = true)
    private void DisableUsingEnderEyes(UseOnContext itemUse, CallbackInfoReturnable<InteractionResult> cir, @Local(ordinal = 0) BlockPos blockpos, @Local(ordinal = 0) Level level) {
        BlockState blockState = level.getBlockState(blockpos);
        if (!ConfigHandler.USE_EYE_OF_ENDER && itemUse.getItemInHand().getItem() == Items.ENDER_EYE && blockState.is(Blocks.END_PORTAL_FRAME)) {
            itemUse.getPlayer().displayClientMessage(Component.translatable("block.endrem.ender_eye.warning"), true);
            cir.setReturnValue(InteractionResult.PASS);
        }
    }

    @Inject(method = "useOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"))
    private void SetBlockEntity(UseOnContext itemUse, CallbackInfoReturnable<InteractionResult> cir, @Local(ordinal = 0) BlockPos blockpos,  @Local(ordinal = 0) Level level) {
        endPortalFrameBlockEntity = (EndPortalFrameBlockEntity) level.getBlockEntity(blockpos);
    }

    @Inject(method = "useOn", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/Level;isClientSide:Z", ordinal = 0, opcode = GETFIELD), cancellable = true)
    private void PortalHasUniqueEye(UseOnContext itemUse, CallbackInfoReturnable<InteractionResult> cir, @Local(ordinal = 0) BlockPos blockpos,  @Local(ordinal = 0) Level level) {
        if (!DetectPortalFrames.isFrameAbsent(level, itemUse, blockpos)) {
            BlockPattern.BlockPatternMatch isPortalWellBuilt = DetectPortalFrames.getCompletedPortalShape().find(level, blockpos);
            if (isPortalWellBuilt == null) {
                itemUse.getPlayer().displayClientMessage(Component.translatable("block.endrem.custom_eye.portal_not_built_well"), true);
            } else {
                itemUse.getPlayer().displayClientMessage(Component.translatable("block.endrem.custom_eye.place"), true);
            }
            cir.setReturnValue(InteractionResult.PASS);
        }
    }

    @Inject(method = "useOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
    private void UpdatePortalFrameBlockEntity(UseOnContext itemUse, CallbackInfoReturnable<InteractionResult> cir) {
        endPortalFrameBlockEntity.updateEye(itemUse.getItemInHand());
    }

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;startUsingItem(Lnet/minecraft/world/InteractionHand;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void DisableThrowingEnderEyes(Level level, Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (!ConfigHandler.THROW_EYE_OF_ENDER && itemStack.getItem() == Items.ENDER_EYE) {
            cir.setReturnValue(InteractionResultHolder.pass(itemStack));
            player.displayClientMessage(Component.translatable("block.endrem.ender_eye.warning"), true);
        }
    }
}