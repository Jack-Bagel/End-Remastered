package com.teamremastered.endrem.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public record SerializedEye(String rarity, ResourceLocation poolID, List<ResourceLocation> lootTablesID) {

    public static final Codec<SerializedEye> CODEC = RecordCodecBuilder.create(instance ->
            instance.group( // Define the fields within the instance
                    Codec.STRING.optionalFieldOf("rarity", "common").forGetter(SerializedEye::rarity),
                    ResourceLocation.CODEC.optionalFieldOf("pool_id", ResourceLocation.withDefaultNamespace("empty")).forGetter(SerializedEye::poolID),
                    ResourceLocation.CODEC.listOf().optionalFieldOf("loot_tables_id", new ArrayList<ResourceLocation>()).forGetter(SerializedEye::lootTablesID)
            ).apply(instance, SerializedEye::new)
    );
}