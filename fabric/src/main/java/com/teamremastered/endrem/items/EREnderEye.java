package com.teamremastered.endrem.items;

import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.blocks.AncientPortalFrame;
import com.teamremastered.endrem.config.ERConfigHandler;
import com.teamremastered.endrem.mixin.accessor.EyeOfEnderEntityAccessorMixin;
import com.teamremastered.endrem.mixin.accessor.PlayerEntityAccessorMixin;
import com.teamremastered.endrem.blocks.ERFrameProperties;
import com.teamremastered.endrem.registry.ERBlocks;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class EREnderEye extends Item {
    public EREnderEye(FabricItemSettings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        String translationKey= String.format("item.%s.%s.description", Constants.MOD_ID, this.asItem());
        tooltip.add(Text.translatable(translationKey));
    }

    @Override
    @ParametersAreNonnullByDefault

    /* Action when used on a frame */
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockpos = context.getBlockPos();
        BlockState blockstate = world.getBlockState(blockpos);

        Boolean frameHasEye;

        if (blockstate.isOf(ERBlocks.ANCIENT_PORTAL_FRAME)) {
            frameHasEye = blockstate.get(AncientPortalFrame.EYE) != ERFrameProperties.EMPTY;
        } else if (blockstate.isOf(Blocks.END_PORTAL_FRAME)) {
            frameHasEye = blockstate.get(EndPortalFrameBlock.EYE);
        } else {
            return ActionResult.PASS;
        }

        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else if (!frameHasEye) {
            ERFrameProperties frameProperties = ERFrameProperties.getFramePropertyFromEye(context.getStack().getItem());
            BlockState newBlockState = ERBlocks.ANCIENT_PORTAL_FRAME.getDefaultState();
            newBlockState = newBlockState.with(HorizontalFacingBlock.FACING, blockstate.get(HorizontalFacingBlock.FACING));
            newBlockState = newBlockState.with(AncientPortalFrame.EYE, frameProperties);

            if (AncientPortalFrame.IsFrameAbsent(world, newBlockState, blockpos)) {
                Block.pushEntitiesUpBeforeBlockChange(blockstate, newBlockState, world, blockpos);
                world.setBlockState(blockpos, newBlockState, 2);
                world.updateComparators(blockpos, ERBlocks.ANCIENT_PORTAL_FRAME);
                context.getStack().decrement(1);
                world.syncWorldEvent(1503, blockpos, 0);
                BlockPattern.Result blockpattern$patternhelper = AncientPortalFrame.getPortalShape(ERFrameProperties.EMPTY, true).searchAround(world, blockpos);

                if (blockpattern$patternhelper != null && !FabricLoader.getInstance().getModContainer("immersive_portals").isPresent()) {
                    BlockPos blockpos1 = blockpattern$patternhelper.getFrontTopLeft().add(-3, 0, -3);

                    for (int i = 0; i < 3; ++i) {
                        for (int j = 0; j < 3; ++j) {
                            world.setBlockState(blockpos1.add(i, 0, j), Blocks.END_PORTAL.getDefaultState(), 2);
                        }
                    }

                    world.syncGlobalEvent(1038, blockpos1.add(1, 0, 1), 0);
                }
                //Immersive Portals Compat
                else if(blockpattern$patternhelper != null){
                    BlockPos blockpos1 = blockpattern$patternhelper.getFrontTopLeft().add(-3, 0, -3);

                    for (int x = 0; x < 3; ++x) {
                        for (int z = 0; z < 3; ++z) {
                            world.setBlockState(
                                    blockpos1.add(x, 0, z),
                                    PortalPlaceholderBlock.instance.getDefaultState().with(PortalPlaceholderBlock.AXIS, Direction.Axis.Y), 2);
                        }
                    }

                    world.syncGlobalEvent(1038, blockpos1.add(1, 0, 1), 0);

                    EndPortalEntity.onEndPortalComplete((ServerWorld) world, Vec3d.of(blockpattern$patternhelper.getFrontTopLeft()).add(-1.5, 0.5, -1.5));
                }

                return ActionResult.CONSUME;
            }

            context.getPlayer().sendMessage(Text.translatable("block.endrem.custom_eye.place"), true);
            return ActionResult.PASS;
        } else if (blockstate.isOf(Blocks.END_PORTAL_FRAME) && ERConfigHandler.CAN_REMOVE_EYE) {
            BlockState newBlockState = blockstate.with(EndPortalFrameBlock.EYE, false);
            world.setBlockState(blockpos, newBlockState, 2);
            world.spawnEntity(new ItemEntity(world, blockpos.getX(), blockpos.getY() + 1, blockpos.getZ(), new ItemStack(Items.ENDER_EYE)));
            return ActionResult.SUCCESS;
        } else {
            context.getPlayer().sendMessage(Text.translatable("block.endrem.custom_eye.frame_has_eye"), true);
            return ActionResult.PASS;
        }
    }

    /* Locate Structures */
    @Override
    public TypedActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getStackInHand(handIn);
        BlockHitResult hitResult = raycast(worldIn, playerIn, RaycastContext.FluidHandling.NONE);
        boolean lookingAtFrame = false;

        BlockState state = worldIn.getBlockState(hitResult.getBlockPos());  //IDK IF IT STILL WORKS
        if (state.isOf(ERBlocks.ANCIENT_PORTAL_FRAME)) {
            lookingAtFrame = true;
        }

        if (lookingAtFrame) {
            return TypedActionResult.pass(itemstack);
        } else {
            playerIn.setCurrentHand(handIn);
            if (worldIn instanceof ServerWorld) {
                ServerWorld serverWorld = (ServerWorld)worldIn;
                BlockPos blockpos = serverWorld.locateStructure(StructureTags.EYE_OF_ENDER_LOCATED, playerIn.getBlockPos(), 100, false);
                if (blockpos != null) {
                    EyeOfEnderEntity eyeOfEnderEntity = new EyeOfEnderEntity(worldIn, playerIn.getX(), playerIn.getBodyY(0.5D), playerIn.getZ());
                    eyeOfEnderEntity.setItem(itemstack);
                    eyeOfEnderEntity.initTargetPos(blockpos);
                    ((EyeOfEnderEntityAccessorMixin) eyeOfEnderEntity).setDropsItem(ERConfigHandler.EYE_BREAK_PROBABILITY <= playerIn.getRandom().nextInt(100));

                    worldIn.spawnEntity(eyeOfEnderEntity);
                    if (playerIn instanceof ServerPlayerEntity) {
                        Criteria.USED_ENDER_EYE.trigger((ServerPlayerEntity) playerIn, blockpos);
                    }

                    worldIn.playSound(null, playerIn.getBlockPos(), SoundEvents.ENTITY_ENDER_EYE_LAUNCH, SoundCategory.NEUTRAL, 0.5F, 0.4F / (float) (Math.random() * 0.4F + 0.8F));
                    worldIn.syncWorldEvent(null, 1003, playerIn.getBlockPos(), 0);

                    if (!((PlayerEntityAccessorMixin) playerIn).getAbilities().creativeMode) {
                        itemstack.decrement(1);
                    }

                    playerIn.incrementStat(Stats.USED.getOrCreateStat(this));
                    playerIn.swingHand(handIn, true);
                    return TypedActionResult.success(itemstack);
                }
            }
            return TypedActionResult.consume(itemstack);
        }
    }

}
