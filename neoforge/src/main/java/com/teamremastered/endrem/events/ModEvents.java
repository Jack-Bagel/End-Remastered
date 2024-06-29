package com.teamremastered.endrem.events;

import com.teamremastered.endrem.config.ERConfig;
import com.teamremastered.endrem.registry.ERItems;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.EnderEyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber
public class ModEvents {


    // Enable/Disable placing of vanilla Ender Eyes on end portal frame depending on configuration
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.isCancelable() && !ERConfig.USE_ENDER_EYE.getRaw()) {
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
        if (event.isCancelable() && !ERConfig.THROW_ENDER_EYE.getRaw()) {
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
            if (!entity.level().isClientSide()) {
                return new MerchantOffer(new ItemStack(Items.EMERALD, priceEmeralds), new ItemStack(Items.RABBIT_FOOT), new ItemStack(ERItems.EVIL_EYE.get()), 12, 10, 0.2F);
            }
            return null;
        }
    }
}
