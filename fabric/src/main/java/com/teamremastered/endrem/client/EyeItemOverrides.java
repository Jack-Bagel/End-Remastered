package com.teamremastered.endrem.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class EyeItemOverrides extends ItemOverrides {
    private final EyeItemOverridesHelper helper;

    public EyeItemOverrides(BakedModel originalModel, Function<ResourceLocation, BakedModel> modelResolver) {
        super();
        helper = new EyeItemOverridesHelper(originalModel, modelResolver);
    }

    @Override
    public @Nullable BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
        return helper.resolve(model, stack, level, entity, seed);
    }
}
