package com.teamremastered.endrem.registry;

import com.teamremastered.endrem.component.EyeDataComponent;
import net.minecraft.core.component.DataComponentType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CommonDataComponentRegistry {
    private static final List<ERRegistryObject<DataComponentType<?>>> DATA_COMPONENTS = new ArrayList<>();

    public static final DataComponentType<EyeDataComponent> DATA_EYE_COMPONENT = DataComponentType.<EyeDataComponent>builder().persistent(EyeDataComponent.CODEC).build();

    public static Collection<ERRegistryObject<DataComponentType<?>>> registerDataComponent() {
        DATA_COMPONENTS.add(new ERRegistryObject<>(DATA_EYE_COMPONENT, "eye_modifier"));

        return DATA_COMPONENTS;
    }
}