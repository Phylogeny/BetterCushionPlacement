package com.github.phylogeny.bettercushionplacement.config;

import com.github.phylogeny.bettercushionplacement.platform.Services;

public class Configs {
    public static final ServerConfig<?, ?> SERVER = Services.CONFIGS.getServerConfig();
    public static final ClientConfig<?, ?> CLIENT = Services.CONFIGS.getClientConfig();
    public static final CommonConfig<?, ?> COMMON = Services.CONFIGS.getCommonConfig();
}
