package com.teamremastered.endrem.registers;

import com.teamremastered.endrem.blocks.AncientPortalFrame;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

@SuppressWarnings("unused")
public class ERBlocks {
    // Deferred Register
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, EndRemastered.MOD_ID);
    private static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EndRemastered.MOD_ID);

    private static final Item.Properties BASE_ITEM_PROPERTIES = new Item.Properties();

    public static void initRegister(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        BLOCK_ITEMS.register(modEventBus);
    }

    public static final RegistryObject<Block> ANCIENT_PORTAL_FRAME = BLOCKS.register("ancient_portal_frame", AncientPortalFrame::new);
    public static final RegistryObject<Item> ANCIENT_PORTAL_FRAME_ITEM = BLOCK_ITEMS.register("ancient_portal_frame", () -> new BlockItem(ANCIENT_PORTAL_FRAME.get(), BASE_ITEM_PROPERTIES));
}
