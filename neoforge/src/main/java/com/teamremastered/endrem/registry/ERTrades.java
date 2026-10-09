package com.teamremastered.endrem.registry;

import com.teamremastered.endrem.EndRemasteredCommon;
import com.teamremastered.endrem.component.EyeDataComponent;
import com.teamremastered.endrem.util.EyeDataManager;
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
import net.neoforged.neoforge.event.village.WandererTradesEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ERTrades {

    public static void init(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.addListener(ERTrades::onVillagerTradesEvent);
        NeoForge.EVENT_BUS.addListener(ERTrades::onWandererTradesEvent);
    }

    private static void onVillagerTradesEvent(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.CLERIC) {
            event.getTrades().get(5).add(new EREyeTrade());
        }
    }

    private static void onWandererTradesEvent(WandererTradesEvent event) {
        event.getRareTrades().add(new EREyeTrade());
    }

    private static class EREyeTrade implements VillagerTrades.ItemListing {

        @Nullable
        @Override
        public MerchantOffer getOffer(Entity entity, RandomSource random) {
            ResourceLocation evilEyeID = EndRemasteredCommon.ModResourceLocation("evil_eye");
            boolean has_eye = EyeDataManager.isEyeLoaded(evilEyeID, entity.registryAccess().lookup(CommonRegistryKey.EYE_DATA));

            if (!has_eye || entity.level().isClientSide()) {
                return null;
            }

            final int maxPrice = 16;
            final int minPrice = 12;
            final int priceEmeralds = random.nextInt(maxPrice - minPrice) + minPrice;

            ItemCost firstItem = new ItemCost(Items.EMERALD, priceEmeralds);
            ItemCost secondItem = new ItemCost(Items.RABBIT_FOOT);

            ItemStack stack = new ItemStack(CommonItemRegistry.DUMMY_EYE);
            stack.set(CommonDataComponentRegistry.DATA_EYE_COMPONENT, new EyeDataComponent(evilEyeID));

            return new MerchantOffer(firstItem, Optional.of(secondItem), stack, 1, 30, 0.2F);
        }
    }
}