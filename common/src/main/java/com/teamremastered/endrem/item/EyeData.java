package com.teamremastered.endrem.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Rarity;

import java.util.ArrayList;
import java.util.List;

public record EyeData(ResourceLocation id, Rarity rarity, List<lootInjection> injections) {

    public static final Codec<EyeData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ResourceLocation.CODEC.fieldOf("id").forGetter(EyeData::id),
                    Rarity.CODEC.optionalFieldOf("rarity", Rarity.COMMON).forGetter(EyeData::rarity),
                    lootInjection.CODEC.listOf().optionalFieldOf("injections", new ArrayList<>()).forGetter(EyeData::injections)
            ).apply(instance, EyeData::new)
    );

    public record lootInjection(ResourceLocation sourceTableID, List<ResourceLocation> targetTableIDs) {
        public static final Codec<lootInjection> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        ResourceLocation.CODEC.optionalFieldOf("source_table_id", ResourceLocation.withDefaultNamespace("empty")).forGetter(lootInjection::sourceTableID),
                        ResourceLocation.CODEC.listOf().optionalFieldOf("target_table_ids", new ArrayList<>()).forGetter(lootInjection::targetTableIDs)
                ).apply(instance, lootInjection::new)
        );
    }
}