package com.teamremastered.endrem.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public record EyeData(ResourceLocation id, String rarity, ResourceLocation poolID, List<ResourceLocation> lootTablesID) {

    public static final Codec<EyeData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ResourceLocation.CODEC.fieldOf("id").forGetter(EyeData::id),
                    Codec.STRING.optionalFieldOf("rarity", "common").forGetter(EyeData::rarity),
                    ResourceLocation.CODEC.optionalFieldOf("pool_id", ResourceLocation.withDefaultNamespace("empty")).forGetter(EyeData::poolID),
                    ResourceLocation.CODEC.listOf().optionalFieldOf("loot_tables_id", new ArrayList<ResourceLocation>()).forGetter(EyeData::lootTablesID)
            ).apply(instance, EyeData::new)
    );
}