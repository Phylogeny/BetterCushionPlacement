package com.github.phylogeny.bettercushionplacement.platform.services;

import java.nio.file.Path;

public interface IPlatformHelper {
    Platform getPlatform();

    boolean isModLoaded(String modId);

    boolean isDevelopmentEnvironment();

    default Environment getEnvironment() {
        return isDevelopmentEnvironment() ? Environment.DEVELOPMENT : Environment.PRODUCTION;
    }

    Path getConfigDir();
}
