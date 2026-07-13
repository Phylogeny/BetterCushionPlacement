package com.github.phylogeny.bettercushionplacement.platform;

import com.github.phylogeny.bettercushionplacement.Constants;
import com.github.phylogeny.bettercushionplacement.platform.services.IPlatformHelper;
import com.github.phylogeny.bettercushionplacement.platform.services.registry.IGameRuleRegistry;

import java.util.ServiceLoader;

public class Services {
    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final IGameRuleRegistry GAME_RULES = load(IGameRuleRegistry.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz, Services.class.getClassLoader())
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        Constants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}