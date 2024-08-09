package com.teamremastered.endrem.block;

import com.teamremastered.endrem.EndRemasteredCommon;
import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.registry.CommonBlockRegistry;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AncientPortalFrameEntity  extends BlockEntity {
    private String eye = "empty";
    public Material eyeTexture;

    public AncientPortalFrameEntity(BlockPos pos, BlockState state) {
        super(CommonBlockRegistry.ANCIENT_PORTAL_FRAME_ENTITY, pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.saveAdditional(nbt, provider);
        nbt.putString("eye_inside", this.eye);
        Constants.LOGGER.info("EYE SAVED: " + this.eye);
    }

    @Override
    protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);
        this.eye = nbt.getString("eye_inside");
        Constants.LOGGER.info("EYE LOADED: " + this.eye);
    }

    // Sync With Client
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag nbt = super.getUpdateTag(registries);
        saveAdditional(nbt, registries);
        return nbt;
    }

    //TODO: Make isEmpty() func

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void setEye(String eye) {
        this.eye = eye;
    }

    public String getEye() {
        return this.eye;
    }

    public ResourceLocation getEyeID() {
        return EndRemasteredCommon.ModResourceLocation(this.eye);
    }

    public Item getEyeItem() {
        return BuiltInRegistries.ITEM.get(getEyeID());
    }
}