package com.teamremastered.endrem.mixin;

import com.teamremastered.endrem.block.EndPortalFrameBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EndPortalFrameBlock.class)
@Implements(@Interface(iface = EntityBlock.class, prefix = "eb$"))
public abstract class EndPortalFrameBlockMixin {

    public BlockEntity eb$newBlockEntity(BlockPos pos, BlockState state) {
        return new EndPortalFrameBlockEntity(pos, state);
    }
}