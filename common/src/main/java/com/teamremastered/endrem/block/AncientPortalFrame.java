package com.teamremastered.endrem.block;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.teamremastered.endrem.item.EREnderEye;
import com.teamremastered.endrem.registry.CommonBlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class AncientPortalFrame extends Block implements EntityBlock {
    public static final BooleanProperty HAS_EYE = BlockStateProperties.EYE;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    // Declare Voxel Shapes (BASE = no eye, EYE = only eye, FULL = both)
    protected static final VoxelShape BASE_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D);
    protected static final VoxelShape EYE_SHAPE = Block.box(4.0D, 13.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    protected static final VoxelShape FULL_SHAPE = Shapes.or(BASE_SHAPE, EYE_SHAPE);

    // Shape of the portal:
    //      v   v   v
    //   >            <
    //   >            <
    //   >            <
    //     ^   ^   ^
    public static BlockPattern getCompletedPortalShape(boolean isCompleted) {
        Predicate<Object> hasEyePredicate = (property) ->  property.equals(true) || !isCompleted;
        return BlockPatternBuilder.start()
                .aisle("?vvv?", ">???<", ">???<", ">???<", "?^^^?")
                .where('?', BlockInWorld.hasState(BlockStatePredicate.ANY))

                .where('^', BlockInWorld.hasState(BlockStatePredicate.forBlock(CommonBlockRegistry.ANCIENT_PORTAL_FRAME)
                        .where(HAS_EYE, hasEyePredicate)
                        .where(FACING, Predicates.equalTo(Direction.SOUTH))
                        .or(BlockStatePredicate.forBlock(Blocks.END_PORTAL_FRAME)
                                .where(HAS_EYE, hasEyePredicate)
                                .where(FACING, Predicates.equalTo(Direction.SOUTH)))))

                .where('>', BlockInWorld.hasState(BlockStatePredicate.forBlock(CommonBlockRegistry.ANCIENT_PORTAL_FRAME)
                        .where(HAS_EYE, hasEyePredicate)
                        .where(FACING, Predicates.equalTo(Direction.WEST))
                        .or(BlockStatePredicate.forBlock(Blocks.END_PORTAL_FRAME)
                                .where(HAS_EYE, hasEyePredicate)
                                .where(FACING, Predicates.equalTo(Direction.WEST)))))

                .where('v', BlockInWorld.hasState(BlockStatePredicate.forBlock(CommonBlockRegistry.ANCIENT_PORTAL_FRAME)
                        .where(HAS_EYE, hasEyePredicate)
                        .where(FACING, Predicates.equalTo(Direction.NORTH))
                        .or(BlockStatePredicate.forBlock(Blocks.END_PORTAL_FRAME)
                                .where(HAS_EYE, hasEyePredicate)
                                .where(FACING, Predicates.equalTo(Direction.NORTH)))))

                .where('<', BlockInWorld.hasState(BlockStatePredicate.forBlock(CommonBlockRegistry.ANCIENT_PORTAL_FRAME)
                        .where(HAS_EYE, hasEyePredicate)
                        .where(FACING, Predicates.equalTo(Direction.EAST))
                        .or(BlockStatePredicate.forBlock(Blocks.END_PORTAL_FRAME)
                                .where(HAS_EYE, hasEyePredicate)
                                .where(FACING, Predicates.equalTo(Direction.EAST)))))
                .build();
    }

    public AncientPortalFrame() {
        super(Properties.of()
                .mapColor(MapColor.COLOR_GREEN)
                .instrument(NoteBlockInstrument.BASEDRUM)
                .sound(SoundType.GLASS)
                .lightLevel((p_152690_) -> 1)
                .strength(-1.0F, 3600000.0F)
                .noLootTable()
        );
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(HAS_EYE, Boolean.FALSE));
    }

    public VoxelShape getShape(BlockState state, BlockGetter blockReader, BlockPos pos, CollisionContext context) {
        return state.getValue(HAS_EYE) ? FULL_SHAPE : BASE_SHAPE;
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    public BlockState getStateForPlacement(BlockPlaceContext useContext) {
        return this.defaultBlockState().setValue(FACING, useContext.getHorizontalDirection().getOpposite()).setValue(HAS_EYE, Boolean.FALSE);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(HAS_EYE).add(FACING);
    }

    // Verify if a given frame is already present in a portal (only works if the portal is built correctly)
    //TODO: Make it a provider class
    public static boolean isFrameAbsent(Level levelIn, UseOnContext itemUse, BlockPos pos) {
        BlockPattern.BlockPatternMatch blockpattern$patternhelper = getCompletedPortalShape(false).find(levelIn, pos);
        BlockPos frontTopLeft = blockpattern$patternhelper.getFrontTopLeft().offset(-4, 0, -4);

        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                BlockPos blockPos = frontTopLeft.offset(i, 0, j);
                BlockEntity blockEntity = levelIn.getBlockEntity(blockPos);

                if (blockEntity instanceof AncientPortalFrameEntity ancientPortalFrameEntity) {
                    if (!ancientPortalFrameEntity.getEye().equals("empty") && ancientPortalFrameEntity.getEyeItem().equals(itemUse.getItemInHand().getItem())) {
                            return false;
                    }
                }
            }
        }
        return true;
    }

//                BlockState stateInPortal = levelIn.getBlockState(blockPos);
//                if (!stateInPortal.is(CommonBlockRegistry.ANCIENT_PORTAL_FRAME)) {
//                    continue;
//                }
//                else if (!stateInPortal.getValue(AncientPortalFrame.HAS_EYE)) {
//                    continue;
//                }
//
//                AncientPortalFrameEntity frameEntity = (AncientPortalFrameEntity) levelIn.getBlockEntity(posInPortal);
//                if (frameEntity == null) {
//                    Constants.LOGGER.warn("COULD NOT INSTANTIATE ANCIENT PORTAL FRAME ENTITY!");
//                    continue;
//                }
//
//                if (frameEntity.getEyeItem() == itemUse.getItemInHand().getItem()) {
//                    return false;
//                }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        AncientPortalFrameEntity ancientPortalFrameEntity = new AncientPortalFrameEntity(blockPos, blockState);
        ancientPortalFrameEntity.setEye(EREnderEye.eyePlaced);
        return ancientPortalFrameEntity;
    }
}