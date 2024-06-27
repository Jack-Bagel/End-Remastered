package com.teamremastered.endrem.blocks;

import com.google.common.base.Predicates;
import com.teamremastered.endrem.util.ERPortalPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.html.BlockView;

public class AncientPortalFrame extends Block {

    public static final EnumProperty<ERFrameProperties> EYE = EnumProperty.of("eye", ERFrameProperties.class);
    public static final BooleanProperty HAS_EYE = Properties.EYE;;

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    // Declare Voxel Shapes (BASE = no eye, EYE = only eye, FULL = both)
    protected static final VoxelShape BASE_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D);
    protected static final VoxelShape EYE_SHAPE = Block.createCuboidShape(4.0D, 13.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    protected static final VoxelShape FULL_SHAPE = VoxelShapes.union(BASE_SHAPE, EYE_SHAPE);

    // Shape of the portal:
    //      v   v   v
    //   >            <
    //   >            <
    //   >            <
    //     ^   ^   ^

    public static BlockPattern getPortalShape(@Nullable ERFrameProperties excludedEyeState, Boolean filled) {
        return BlockPatternBuilder.start()
       .aisle("?vvv?", ">???<", ">???<", ">???<", "?^^^?")
       .where('?', CachedBlockPosition.matchesBlockState(BlockStatePredicate.ANY))
       .where('^', CachedBlockPosition.matchesBlockState(ERPortalPredicate.facing(Direction.SOUTH).withoutEye(excludedEyeState).requireAncientFrame(filled).or((BlockStatePredicate.forBlock(Blocks.END_PORTAL_FRAME).with(HAS_EYE, Predicates.equalTo(true))))))
       .where('>', CachedBlockPosition.matchesBlockState(ERPortalPredicate.facing(Direction.WEST).withoutEye(excludedEyeState).requireAncientFrame(filled).or((BlockStatePredicate.forBlock(Blocks.END_PORTAL_FRAME).with(HAS_EYE, Predicates.equalTo(true))))))
       .where('v', CachedBlockPosition.matchesBlockState(ERPortalPredicate.facing(Direction.NORTH).withoutEye(excludedEyeState).requireAncientFrame(filled).or((BlockStatePredicate.forBlock(Blocks.END_PORTAL_FRAME).with(HAS_EYE, Predicates.equalTo(true))))))
       .where('<', CachedBlockPosition.matchesBlockState(ERPortalPredicate.facing(Direction.EAST).withoutEye(excludedEyeState).requireAncientFrame(filled).or((BlockStatePredicate.forBlock(Blocks.END_PORTAL_FRAME).with(HAS_EYE, Predicates.equalTo(true))))))
       .build();
    }

    public AncientPortalFrame(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(EYE, ERFrameProperties.EMPTY));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(EYE) == ERFrameProperties.EMPTY ? BASE_SHAPE : FULL_SHAPE;
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation((Direction)state.get(FACING)));
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)((BlockState)this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite()));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING, EYE);
    }

    // Verify if a given frame is already present in a portal (only works if the portal is built correctly)
    public static boolean IsFrameAbsent(WorldView worldIn, BlockState frameState, BlockPos pos) {
        BlockPattern.Result blockpattern$patternhelper = getPortalShape(
                frameState.get(AncientPortalFrame.EYE), false).searchAround(worldIn, pos);

        return blockpattern$patternhelper != null;
    }
}