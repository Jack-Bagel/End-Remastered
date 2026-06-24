package com.teamremastered.endrem.client;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public class EyeDynamicBakedModel implements BakedModel {
    private final BakedModel originalModel;
    private final ItemOverrides overrides;

    public EyeDynamicBakedModel(BakedModel originalModel, ItemOverrides overrides) {
        this.originalModel = originalModel;
        this.overrides = overrides;
    }

    @Override
    public ItemOverrides getOverrides() {
        return overrides;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction direction, RandomSource random) {
        return originalModel.getQuads(state, direction, random);
    }

    @Override public boolean useAmbientOcclusion() {
        return originalModel.useAmbientOcclusion();
    }

    @Override public boolean isGui3d() {
        return originalModel.isGui3d();
    }

    @Override public boolean usesBlockLight() {
        return originalModel.usesBlockLight();
    }

    @Override public boolean isCustomRenderer() {
        return originalModel.isCustomRenderer();
    }

    @Override public net.minecraft.client.renderer.texture.TextureAtlasSprite getParticleIcon() {
        return originalModel.getParticleIcon();
    }

    @Override public net.minecraft.client.renderer.block.model.ItemTransforms getTransforms() {
        return originalModel.getTransforms();
    }
}
