package com.github.phylogeny.bettercushionplacement.platform.services.registry;

import com.github.phylogeny.bettercushionplacement.config.ClientConfig;
import com.github.phylogeny.bettercushionplacement.config.CommonConfig;
import com.github.phylogeny.bettercushionplacement.config.ServerConfig;

public interface ConfigRegistry {
    ServerConfig<?, ?> getServerConfig();

    ClientConfig<?, ?> getClientConfig();

    CommonConfig<?, ?> getCommonConfig();
}
