package com.teamremastered.endrem.registry;

import com.teamremastered.endrem.EndRemasteredCommon;
import com.teamremastered.endrem.component.EyeDataComponent;
import com.teamremastered.endrem.item.EyeData;
import com.teamremastered.endrem.util.EyeDataManager;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
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

        @Override
        public MerchantOffer getOffer(Entity entity, RandomSource randomSource) {
            ResourceLocation evilEyeID = EndRemasteredCommon.ModResourceLocation("evil_eye");
            boolean has_eye = EyeDataManager.isEyeLoaded(evilEyeID, entity.registryAccess().lookup(CommonRegistryKey.EYE_DATA));

            if (!has_eye || entity.level().isClientSide()) {
                return null;
            }

            final int maxPrice = 16;
            final int minPrice = 12;
            final int priceEmeralds = randomSource.nextInt(maxPrice - minPrice) + minPrice;

            ItemCost firstItem = new ItemCost(Items.EMERALD, priceEmeralds);
            ItemCost secondItem = new ItemCost(Items.RABBIT_FOOT);

            ItemStack stack = new ItemStack(CommonItemRegistry.DUMMY_EYE);
            stack.set(CommonDataComponentRegistry.DATA_EYE_COMPONENT, new EyeDataComponent(evilEyeID));

            return new MerchantOffer(firstItem, Optional.of(secondItem), stack, 1, 30, 0.2F);
        }
    }

    public static void registerVillagerTrades() {
            TradeOfferHelper.registerVillagerOffers(VillagerProfession.CLERIC, 5, factories -> {
                factories.add(new EREyeTrade());
            });

            TradeOfferHelper.registerWanderingTraderOffers(2, factories -> {
                factories.add(new EREyeTrade());
            });
    }
}