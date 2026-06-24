package com.teamremastered.endrem.registry;

import com.teamremastered.endrem.component.EyeDataComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@SuppressWarnings("unused")
public class ERTrades {

    public static void init(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.addListener(ERTrades::onVillagerTradesEvent);
    }

    private static void onVillagerTradesEvent(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.CLERIC) {
            event.getTrades().get(5).add(new ERTrades.EREyeTrade());
        }
    }

    public static class EREyeTrade implements VillagerTrades.ItemListing {

        @Nullable
        @Override
        public MerchantOffer getOffer(Entity entity, RandomSource random) {
            int maxPrice = 16;
            int minPrice = 12;
            int priceEmeralds = random.nextInt(maxPrice - minPrice) + minPrice;
            ItemCost firstItem = new ItemCost(Items.EMERALD, priceEmeralds);
            ItemCost secondItem = new ItemCost(Items.RABBIT_FOOT);


            if (!entity.level().isClientSide()) {
                ItemStack stack = new ItemStack(CommonItemRegistry.DUMMY_EYE);
                stack.set(CommonDataComponentRegistry.DATA_EYE_COMPONENT,
                        new EyeDataComponent(ResourceLocation.parse("endrem:evil_eye")));
                return new MerchantOffer(firstItem, Optional.of(secondItem), stack, 1, 1, 1F);
            }
            return null;
        }
    }
}
