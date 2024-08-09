package com.teamremastered.endrem.item;

import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.block.AncientPortalFrame;
import com.teamremastered.endrem.config.ConfigHandler;
import com.teamremastered.endrem.mixin.accessor.EyeOfEnderEntityAccessorMixin;
import com.teamremastered.endrem.registry.CommonBlockRegistry;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@MethodsReturnNonnullByDefault
public class EREnderEye extends Item {
    public EREnderEye(Properties properties) {
        super(properties);
    }
    public static String eyePlaced = "empty";

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext world, List<Component> tooltip, TooltipFlag tooltipContext) {
        String translationKey= String.format("item.%s.%s.description", Constants.MOD_ID, this.getId());
        tooltip.add(Component.translatable(translationKey));
    }

    //Fill the Ancient Portal Frame
    @Override
    public InteractionResult useOn(UseOnContext itemUse) {
        Level level = itemUse.getLevel();
        BlockPos blockpos = itemUse.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        boolean frameHasEye;

        // Check if the frame already has an eye inside
        if (blockstate.is(CommonBlockRegistry.ANCIENT_PORTAL_FRAME)) {
            frameHasEye = blockstate.getValue(AncientPortalFrame.HAS_EYE);
        } else if (blockstate.is(Blocks.END_PORTAL_FRAME)) {
            frameHasEye = blockstate.getValue(BlockStateProperties.EYE);
        } else {
            return InteractionResult.PASS;
        }

        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        else if (!frameHasEye) {
            BlockState newBlockState = CommonBlockRegistry.ANCIENT_PORTAL_FRAME.defaultBlockState();
            newBlockState = newBlockState.setValue(HorizontalDirectionalBlock.FACING, blockstate.getValue(HorizontalDirectionalBlock.FACING));
            newBlockState = newBlockState.setValue(AncientPortalFrame.HAS_EYE, Boolean.TRUE);
            eyePlaced = this.getId(); // Setup the eye to render inside the block entity
            if (AncientPortalFrame.isFrameAbsent(level, itemUse, blockpos)) {
                Block.pushEntitiesUp(blockstate, newBlockState, level, blockpos);
                level.setBlock(blockpos, newBlockState, 2);
                level.updateNeighbourForOutputSignal(blockpos, CommonBlockRegistry.ANCIENT_PORTAL_FRAME);
                itemUse.getItemInHand().shrink(1);
                level.levelEvent(1503, blockpos, 0);
                BlockPattern.BlockPatternMatch blockpattern$patternhelper = AncientPortalFrame.getCompletedPortalShape(true).find(level, blockpos);
                if (blockpattern$patternhelper != null) {
                    BlockPos blockpos1 = blockpattern$patternhelper.getFrontTopLeft().offset(-3, 0, -3);

                    for (int i = 0; i < 3; ++i) {
                        for (int j = 0; j < 3; ++j) {
                            level.setBlock(blockpos1.offset(i, 0, j), Blocks.END_PORTAL.defaultBlockState(), 2);
                        }
                    }

                    level.globalLevelEvent(1038, blockpos1.offset(1, 0, 1), 0);
                }
                return InteractionResult.CONSUME;
            }
            itemUse.getPlayer().displayClientMessage(Component.translatable("block.endrem.custom_eye.place"), true);
            return InteractionResult.PASS;
        }
        else if (blockstate.is(Blocks.END_PORTAL_FRAME) && ConfigHandler.CAN_REMOVE_EYE) {
            BlockState newBlockState = blockstate.setValue(BlockStateProperties.EYE, false);
            level.setBlock(blockpos, newBlockState, 2);
            level.addFreshEntity(new ItemEntity(level, blockpos.getX(), blockpos.getY() + 1, blockpos.getZ(), new ItemStack(Items.ENDER_EYE)));
            return InteractionResult.SUCCESS;
        }
        else {
            itemUse.getPlayer().displayClientMessage(Component.translatable("block.endrem.custom_eye.frame_has_eye"), true);
            return InteractionResult.PASS;
        }
    }

    @Override

    //Locate Structures
    public InteractionResultHolder<ItemStack> use(Level levelIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        BlockHitResult raytraceResult = getPlayerPOVHitResult(levelIn, playerIn, ClipContext.Fluid.NONE);
        boolean lookingAtFrame = false;


        BlockState state = levelIn.getBlockState(raytraceResult.getBlockPos());
        if (state.is(CommonBlockRegistry.ANCIENT_PORTAL_FRAME)) {
            lookingAtFrame = true;
        }

        if (lookingAtFrame) {
            return InteractionResultHolder.pass(itemstack);
        } else {
            playerIn.startUsingItem(handIn);
            if (levelIn instanceof ServerLevel test) {
                BlockPos blockpos = ((ServerLevel) levelIn).findNearestMapStructure(StructureTags.EYE_OF_ENDER_LOCATED, playerIn.blockPosition(), 100, false);
                if (blockpos != null) {
                    EyeOfEnder eyeofenderentity = new EyeOfEnder(levelIn, playerIn.getX(), playerIn.getY(0.5D), playerIn.getZ());
                    eyeofenderentity.setItem(itemstack);
                    eyeofenderentity.signalTo(blockpos);
                    ((EyeOfEnderEntityAccessorMixin) eyeofenderentity).setSurviveAfterDeath(ConfigHandler.EYE_BREAK_PROBABILITY <= playerIn.getRandom().nextInt(100));

                    levelIn.addFreshEntity(eyeofenderentity);
                    if (playerIn instanceof ServerPlayer) {
                        CriteriaTriggers.USED_ENDER_EYE.trigger((ServerPlayer) playerIn, blockpos);
                    }

                    levelIn.playSound(null, playerIn.blockPosition(), SoundEvents.ENDER_EYE_LAUNCH, SoundSource.NEUTRAL, 0.5F, 0.4F / (levelIn.getRandom().nextFloat() * 0.4F + 0.8F));
                    levelIn.levelEvent(null, 1003, playerIn.blockPosition(), 0);

                    if (!playerIn.isCreative()) {
                        itemstack.shrink(1);
                    }

                    playerIn.awardStat(Stats.ITEM_USED.get(this));
                    playerIn.swing(handIn, true);
                    return InteractionResultHolder.success(itemstack);
                }
            }
            return InteractionResultHolder.consume(itemstack);
        }
    }

    //TODO: Forge specific, find a common implementation
//    @Override
//    public boolean isDamageable(ItemStack stack) {
//        return !this.asItem().canBeDepleted();
//    }

//    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level levelIn, List<Component> tooltip, TooltipFlag flagIn) {
        String translationKey = String.format("item.%s.%s.description", Constants.MOD_ID, this.asItem());
        tooltip.add(Component.translatable(translationKey));
    }

    public String getId() {
        String[] eyeID = this.toString().split(":");
        return eyeID[1];
    }
}