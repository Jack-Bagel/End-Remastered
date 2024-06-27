package com.teamremastered.endrem.registers;

import com.mojang.serialization.Codec;
import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.utils.LootInjector;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.bus.api.IEventBus;
@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegisterHandler {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLMS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Constants.MOD_ID);

    public static void init() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ERBlocks.initRegister(modEventBus);
        ERItems.initRegister(modEventBus);
        ERTabs.initRegister(modEventBus);
        GLMS.register(modEventBus);
    }

    private static final RegistryObject<Codec<LootInjector.LootInjectorModifier>> LOOT_INJECTION = GLMS.register("loot_injection", LootInjector.LootInjectorModifier.CODEC);
}