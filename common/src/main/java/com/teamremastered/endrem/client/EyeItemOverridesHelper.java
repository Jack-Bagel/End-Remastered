package com.teamremastered.endrem.client;

import com.teamremastered.endrem.Constants;
import com.teamremastered.endrem.component.EyeDataComponent;
import com.teamremastered.endrem.registry.CommonDataComponentRegistry;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class EyeItemOverridesHelper {
    private final BakedModel originalModel;
    private final Function<ResourceLocation, BakedModel> modelResolver;

    EyeItemOverridesHelper(BakedModel originalModel, Function<ResourceLocation, BakedModel> modelResolver) {
        this.originalModel = originalModel;
        this.modelResolver = modelResolver;
    }

    public @Nullable BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
        // Read your custom data component here
        EyeDataComponent component = stack.get(CommonDataComponentRegistry.DATA_EYE_COMPONENT);

        if (component != null) {
            ResourceLocation rawId = component.id();
            ResourceLocation targetId = ResourceLocation.fromNamespaceAndPath(rawId.getNamespace(),"eye/" + rawId.getPath());
            BakedModel dynamicMesh = modelResolver.apply(targetId);
            if (dynamicMesh != null) {
                return dynamicMesh;
            }
            Constants.LOGGER.warn("The eye texture " + targetId + " was not found.");
        }
        return originalModel;
    }
}
