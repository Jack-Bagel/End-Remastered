package com.teamremastered.endrem.registry;

import com.mojang.serialization.Codec;
import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.utils.LootInjector;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.bus.api.IEventBus;

import java.util.Collection;
import java.util.function.Supplier;

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

    private static <T extends IForgeRegistryEntry<T>> void register(Class clazz, IEventBus eventBus, Supplier<Collection<ERRegistryObject<T>>> registryObjectsSupplier) {
        eventBus.addGenericListener(clazz, (RegistryEvent.Register<T> event) -> {
            Collection<ERRegistryObject<T>> registryObjects = registryObjectsSupplier.get();
            IForgeRegistry<T> registry = event.getRegistry();
            for (ERRegistryObject<T> registryObject : registryObjects) {
                registryObject.object().setRegistryName(ER.createLocation(registryObject.id()));
                registry.register(registryObject.object());
            }
        });
    }
}