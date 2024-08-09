package com.teamremastered.endrem.utils;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamremastered.endrem.registry.RegisterHandler;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

public class LootInjector {

    public static class LootInjectorModifier extends LootModifier {
        public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<LootInjectorModifier>> CODEC = RegisterHandler.GLMS.register("loot_injection_codec",
                () -> RecordCodecBuilder.mapCodec(
                inst -> LootModifier.codecStart(inst).and(
                inst.group(
                ResourceLocation.CODEC.fieldOf("loot_table_id").forGetter(m -> m.table),
                Codec.BOOL.fieldOf("allow_recursive_glms").forGetter(m -> m.allowRecursiveGlms)
                )).apply(inst, LootInjectorModifier::new)));

        private final boolean allowRecursiveGlms;
        private final ResourceLocation table;
        public LootInjectorModifier(LootItemCondition[] conditionsIn, ResourceLocation tableIn, boolean recursiveGlms) {
            super(conditionsIn);
            this.table = tableIn;
            this.allowRecursiveGlms = recursiveGlms;
        }

        @Override
        @NotNull
        protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
            LootParams.Builder builder = (new LootParams.Builder(context.getLevel()));
            LootTable loottable = context.getLevel().getServer().reloadableRegistries().getLootTable(ResourceKey.create(Registries.LOOT_TABLE, table));
            generatedLoot.addAll(loottable.getRandomItems(builder.create(LootContextParamSets.EMPTY)));
            return generatedLoot;
        }

        @Override
        public MapCodec<? extends IGlobalLootModifier> codec() {
            return CODEC.value();
        }
    }
}