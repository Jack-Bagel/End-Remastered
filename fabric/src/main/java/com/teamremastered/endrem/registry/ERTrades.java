package com.teamremastered.endrem.registry;

import com.teamremastered.endrem.config.ConfigHandler;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ERTrades {

    private static class EREyeTrade implements VillagerTrades.ItemListing {

        final int maxPrice = 10;
        final int minPrice = 6;

        @Nullable
        @Override
        public MerchantOffer getOffer(Entity entity, RandomSource randomSource) {
            int priceEmeralds = randomSource.nextInt(maxPrice - minPrice) + minPrice;
            ItemCost firstItem = new ItemCost(Items.EMERALD, priceEmeralds);
            ItemCost secondItem = new ItemCost(Items.RABBIT_FOOT);

            if (!entity.level().isClientSide()) {
                return new MerchantOffer(firstItem, Optional.of(secondItem), new ItemStack(CommonItemRegistry.EVIL_EYE), 1, 1, 1F);
            }
            return null;
        }
    }

    public static void registerVillagerTrades() {
        if (ConfigHandler.IS_EVIL_EYE_OBTAINABLE) {
            TradeOfferHelper.registerVillagerOffers(VillagerProfession.CLERIC, 5, factories -> factories.add(new EREyeTrade()));

            TradeOfferHelper.registerWanderingTraderOffers(0, factories -> factories.add(new EREyeTrade()));
        }
    }
}