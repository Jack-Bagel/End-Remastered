package com.teamremastered.endrem.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record EyeDataComponent(ResourceLocation id) {

    public static final Codec<EyeDataComponent> CODEC = RecordCodecBuilder.create(builder -> {
        return builder.group(
                ResourceLocation.CODEC.fieldOf("id").forGetter(EyeDataComponent::id)
        ).apply(builder, EyeDataComponent::new);
    });
}