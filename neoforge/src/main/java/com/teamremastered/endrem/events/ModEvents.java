package com.teamremastered.endrem.events;

import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.config.ERConfig;
import com.teamremastered.endrem.registry.CommonItemRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.EnderEyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {

    // Enable/Disable placing of vanilla Ender Eyes on end portal frame depending on configuration
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.isCanceled() && !ERConfig.USE_ENDER_EYE.getRaw()) {
            if (event.getEntity().getInventory().getSelected().getItem() instanceof EnderEyeItem) {
                if (event.getLevel().getBlockState(event.getPos()).getBlock() == Blocks.END_PORTAL_FRAME) {
                    event.setCanceled(true);
                    event.getEntity().displayClientMessage(Component.translatable("block.endrem.ender_eye.use_warning"), true);
                }
            }
        }
    }

    // Enable/Disable throwing of vanilla Ender Eyes depending on configuration
    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (event.isCanceled() && !ERConfig.THROW_ENDER_EYE.getRaw()) {
            if (event.getEntity().getInventory().getSelected().getItem() instanceof EnderEyeItem) {
                event.setCanceled(true);
                if (event.getLevel().getBlockState(event.getPos()).getBlock() != Blocks.END_PORTAL_FRAME)
                    event.getEntity().displayClientMessage(Component.translatable("block.endrem.ender_eye.throw_warning"), true);
            }
        }
    }

    @SubscribeEvent
    public static void onVillagerTradesEvent(VillagerTradesEvent event) {
        if (ERConfig.IS_EVIL_EYE_OBTAINABLE.getRaw() && event.getType() == VillagerProfession.CLERIC) {
            event.getTrades().get(5).add(new EREyeTrade());
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