package com.teamremastered.endrem.platform;

import com.teamremastered.endrem.config.ERConfig;
import com.teamremastered.endrem.platform.services.IConfigHelper;
import com.teamremastered.endrem.platform.services.IPlatformHelper;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

public class NeoForgeConfigHelper implements IConfigHelper {

    @Override
    public boolean throwEnderEye() {
        return ERConfig.THROW_ENDER_EYE.getRaw();
    }

    @Override
    public boolean useEnderEye() {
        return ERConfig.USE_ENDER_EYE.getRaw();
    }

    @Override
    public boolean frameHasEye() {
        return ERConfig.FRAME_HAS_EYE.getRaw();
    }

    @Override
    public int eyeBreakChance() {
        return ERConfig.EYE_BREAK_CHANCE.getRaw();
    }

    @Override
    public boolean canRemoveEye() {
        return ERConfig.CAN_REMOVE_EYE.getRaw();
    }

    @Override
    public boolean isCrypticObtainable() {
        return ERConfig.IS_CRYPTIC_EYE_OBTAINABLE.getRaw();
    }

    @Override
    public boolean isEvilObtainable() {
        return  ERConfig.IS_EVIL_EYE_OBTAINABLE.getRaw();
    }
}