package com.teamremastered.endrem.registry;

import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.config.ERConfig;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ERTrades {

    @SubscribeEvent
    public static void onVillagerTradesEvent(VillagerTradesEvent event) {
        if (ERConfig.IS_EVIL_EYE_OBTAINABLE.getRaw() && event.getType() == VillagerProfession.CLERIC) {
            event.getTrades().get(5).add(new ERTrades.EREyeTrade());
        }
    }

    public static class EREyeTrade implements VillagerTrades.ItemListing {

        @Nullable
        @Override
        public MerchantOffer getOffer(Entity entity, RandomSource random) {
            int maxPrice = 50;
            int minPrice = 30;
            int priceEmeralds = random.nextInt(maxPrice - minPrice) + minPrice;
            ItemCost firstItem = new ItemCost(Items.EMERALD, priceEmeralds);
            ItemCost secondItem = new ItemCost(Items.RABBIT_FOOT);


            if (!entity.level().isClientSide()) {
                return new MerchantOffer(firstItem, Optional.of(secondItem), new ItemStack(CommonItemRegistry.EVIL_EYE), 12, 10, 0.2F);
            }
            return null;
        }
    }
}
