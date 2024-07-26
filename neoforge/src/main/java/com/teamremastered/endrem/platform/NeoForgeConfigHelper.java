package com.teamremastered.endrem.platform;

import com.teamremastered.endrem.platform.services.IConfigHelper;
import net.neoforged.fml.loading.FMLPaths;

public class NeoForgeConfigHelper implements IConfigHelper {

    @Override
    public String configDirectoryPath() {
        return FMLPaths.CONFIGDIR.get().toString();
    }

}