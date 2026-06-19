package com.teamremastered.endrem.block;

import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.registry.CommonBlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class EndPortalFrameBlockEntity extends BlockEntity {
    private ResourceLocation eye_id = ResourceLocation.withDefaultNamespace("empty");

    public EndPortalFrameBlockEntity(BlockPos pos, BlockState state) {
        super(CommonBlockRegistry.END_PORTAL_FRAME_BLOCK_ENTITY, pos, state);
    }

    public void updateEye(ItemStack newEye) {
        this.eye_id = BuiltInRegistries.ITEM.getKey(newEye.getItem());
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putString("eye_inside", this.eye_id.toString());
        Constants.LOGGER.info("EYE SAVED: " + this.eye_id.getPath());
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.eye_id = ResourceLocation.parse(tag.getString("eye_inside"));
        Constants.LOGGER.info("EYE LOADED: " + this.eye_id);
    }

    // Sync With Client
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag nbt = super.getUpdateTag(registries);
        saveAdditional(nbt, registries);
        return nbt;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public ResourceLocation getEyeIdentificator() {
        return this.eye_id;
    }

    public Item getEyeAsItem() {
        return BuiltInRegistries.ITEM.get(getEyeIdentificator());
    }

    public boolean isEmpty() {
        return this.eye_id.equals("empty");
    }
}