package com.teamremastered.endrem.registry;

import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.config.ConfigHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.entity.npc.villager.VillagerTrades;
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
@EventBusSubscriber(modid = Constants.MOD_ID)
public class ERTrades {

    @SubscribeEvent
    public static void onVillagerTradesEvent(VillagerTradesEvent event) {
        if (ConfigHandler.IS_EVIL_EYE_OBTAINABLE && event.getType() == VillagerProfession.CLERIC) {
            event.getTrades().get(5).add(new ERTrades.EREyeTrade());
        }
    }

    public static class EREyeTrade implements VillagerTrades.ItemListing {

        @Nullable
        @Override
        public MerchantOffer getOffer(ServerLevel serverLevel, Entity entity, RandomSource random) {
            int maxPrice = 16;
            int minPrice = 12;
            int priceEmeralds = random.nextInt(maxPrice - minPrice) + minPrice;
            ItemCost firstItem = new ItemCost(Items.EMERALD, priceEmeralds);
            ItemCost secondItem = new ItemCost(Items.RABBIT_FOOT);


            if (!serverLevel.isClientSide()) {
                return new MerchantOffer(firstItem, Optional.of(secondItem), new ItemStack(CommonItemRegistry.EVIL_EYE), 1, 1, 1F);
            }
            return null;
        }
    }
}
