package com.github.phylogeny.bettercushionplacement.platform;

import com.github.phylogeny.bettercushionplacement.platform.services.IPlatformHelper;
import com.github.phylogeny.bettercushionplacement.platform.services.Platform;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

public class NeoForgePlatformHelper implements IPlatformHelper {
    @Override
    public Platform getPlatform() {
        return Platform.NEO_FORGE;
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.getCurrent().isProduction();
    }
}