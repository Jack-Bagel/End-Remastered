package com.teamremastered.endrem.block;

import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.EndRemasteredCommon;
import com.teamremastered.endrem.registry.CommonBlockRegistry;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class AncientPortalFrameEntity  extends BlockEntity {
    private String eye = "empty";

    public AncientPortalFrameEntity(BlockPos pos, BlockState state) {
        super(CommonBlockRegistry.ANCIENT_PORTAL_FRAME_ENTITY, pos, state);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putString("eye_inside", this.eye);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.eye = input.getString("eye_inside").orElse("");
    }

    // Sync With Client
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        try (ProblemReporter.ScopedCollector problemreporter = new ProblemReporter.ScopedCollector(this.problemPath(), Constants.LOGGER)) {
            TagValueOutput valueOutput = TagValueOutput.createWithContext(problemreporter, registries);
            saveAdditional(valueOutput);
            return valueOutput.buildResult();
        }
    }

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
        return BuiltInRegistries.ITEM.get(getEyeID()).get().value();
    }

    public boolean isEmpty() {
        return this.eye.equals("empty");
    }
}