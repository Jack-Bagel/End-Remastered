package com.teamremastered.endrem.mixin;

import com.teamremastered.endrem.config.ERConfig;
import net.minecraft.world.level.levelgen.structure.structures.StrongholdPieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@SuppressWarnings("unused")
@Mixin(StrongholdPieces.PortalRoom.class)
public class StrongholdPiecesMixin {
    @ModifyConstant(method = "postProcess", constant = @Constant(floatValue = 0.9F))
    private float frameHasEyeOdds(float originalValue) {

        //Game checks if a random number between 0.0 and 1.0 is greater than our value "odd", if true, add an eye to the frame.
        float newValue = 1.1F; //Set impossible value to reach
        return ERConfig.FRAME_HAS_EYE.getRaw() ? originalValue : newValue;
    }
}