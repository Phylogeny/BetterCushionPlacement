package com.github.phylogeny.bettercushionplacement.config;

import com.github.phylogeny.bettercushionplacement.platform.services.registry.ConfigBuilder;

public class ServerConfig<S, T> extends ConfigFile<S, T> {
    public ServerConfig(ConfigBuilder<S, T> builder) {
        super(builder);
    }
}
