package com.teamremastered.endrem.platform;

import com.teamremastered.endrem.config.ERConfigHandler;
import com.teamremastered.endrem.platform.services.IConfigHelper;

public class FabricConfigHelper implements IConfigHelper {

    @Override
    public boolean throwEnderEye() {
        return ERConfigHandler.THROW_EYE_OF_ENDER;
    }

    @Override
    public boolean useEnderEye() {
        return ERConfigHandler.USE_EYE_OF_ENDER;
    }

    @Override
    public boolean frameHasEye() {
        return ERConfigHandler.FRAME_HAS_RANDOM_EYE;
    }

    @Override
    public int eyeBreakChance() {
        return ERConfigHandler.EYE_BREAK_PROBABILITY;
    }

    @Override
    public boolean canRemoveEye() {
        return ERConfigHandler.CAN_REMOVE_EYE;
    }

    @Override
    public boolean isCrypticObtainable() {
        return ERConfigHandler.IS_CRYPTIC_EYE_OBTAINABLE;
    }

    @Override
    public boolean isEvilObtainable() {
        return  ERConfigHandler.IS_EVIL_EYE_OBTAINABLE;
    }
}
