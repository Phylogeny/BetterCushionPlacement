package com.github.phylogeny.bettercushionplacement.platform;

import com.github.phylogeny.bettercushionplacement.platform.services.IPlatformHelper;
import com.github.phylogeny.bettercushionplacement.platform.services.Platform;
import net.fabricmc.loader.api.FabricLoader;

public class FabricPlatformHelper implements IPlatformHelper {
    @Override
    public Platform getPlatform() {
        return Platform.FABRIC;
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}