package com.teamremastered.endrem.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.teamremastered.endrem.block.EndPortalFrameBlockEntity;
import com.teamremastered.endrem.component.EyeDataComponent;
import com.teamremastered.endrem.config.ConfigHandler;
import com.teamremastered.endrem.item.EREnderEye;
import com.teamremastered.endrem.registry.CommonDataComponentRegistry;
import com.teamremastered.endrem.util.DetectPortalFrames;
import com.teamremastered.endrem.util.EyeDataManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
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
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(EnderEyeItem.class)
public class EnderEyeItemMixin {

    @Unique
    private final int endrem$GETFIELD = 180;

    @Inject(method = "useOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"), cancellable = true)
    private void DisableUsingEnderEyes(UseOnContext itemUse, CallbackInfoReturnable<InteractionResult> cir, @Local(ordinal = 0) BlockPos blockpos, @Local(ordinal = 0) Level level) {
        BlockState blockState = level.getBlockState(blockpos);
        if (!ConfigHandler.USE_EYE_OF_ENDER && itemUse.getItemInHand().getItem() == Items.ENDER_EYE && blockState.is(Blocks.END_PORTAL_FRAME)) {
            itemUse.getPlayer().displayClientMessage(Component.translatable("block.endrem.ender_eye.warning"), true);
            cir.setReturnValue(InteractionResult.PASS);
        }
    }

    @Inject(method = "useOn", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/Level;isClientSide:Z", ordinal = 0, opcode = endrem$GETFIELD), cancellable = true)
    private void PortalHasUniqueEye(UseOnContext itemUse, CallbackInfoReturnable<InteractionResult> cir, @Local(ordinal = 0) BlockPos blockpos,  @Local(ordinal = 0) Level level) {
        ItemStack usedEye = itemUse.getItemInHand();
        Optional<EyeDataComponent> component = Optional.ofNullable(usedEye.get(CommonDataComponentRegistry.DATA_EYE_COMPONENT));
        ResourceLocation componentID;

        if (component.isPresent()) {
            componentID = component.get().id();
        }
        else if (usedEye.getItem().equals(Items.ENDER_EYE)) {
            componentID = BuiltInRegistries.ITEM.getKey(usedEye.getItem());
        }
        else {
            return;
        }

        if (!DetectPortalFrames.isFrameAbsent(level, componentID, blockpos)) {
            BlockPattern.BlockPatternMatch isPortalWellBuilt = DetectPortalFrames.getCompletedPortalShape().find(level, blockpos);
            Optional<Player> player = Optional.ofNullable(itemUse.getPlayer());
            if (isPortalWellBuilt == null) {
                player.ifPresent(p -> p.displayClientMessage(Component.translatable("block.endrem.custom_eye.portal_not_built_well"), true));
            } else {
                player.ifPresent(p -> p.displayClientMessage(Component.translatable("block.endrem.custom_eye.place"), true));
            }
            cir.setReturnValue(InteractionResult.PASS);
        }
    }

    @Inject(method = "useOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
    private void UpdatePortalFrameBlockEntity(UseOnContext itemUse, CallbackInfoReturnable<InteractionResult> cir, @Local(ordinal = 0) BlockPos pos, @Local(ordinal = 0) Level level)
    {
        if (!(level.getBlockEntity(pos) instanceof EndPortalFrameBlockEntity frame)) {
            return;
        }

        ItemStack stack = itemUse.getItemInHand();
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());

        if (stack.has(CommonDataComponentRegistry.DATA_EYE_COMPONENT)) {
            frame.updateEye(stack);
        }

        else if (ConfigHandler.USE_EYE_OF_ENDER && id.equals(ResourceLocation.withDefaultNamespace("ender_eye"))) {
            if(!stack.has(CommonDataComponentRegistry.DATA_EYE_COMPONENT)) {
                stack.set(CommonDataComponentRegistry.DATA_EYE_COMPONENT, new EyeDataComponent(ResourceLocation.withDefaultNamespace("ender_eye")));
            }

            frame.updateEye(stack);
        }
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