package com.github.phylogeny.bettercushionplacement.platform.registry;

import com.github.phylogeny.bettercushionplacement.config.*;
import com.github.phylogeny.bettercushionplacement.platform.services.registry.ConfigRegistry;
import org.spongepowered.configurate.CommentedConfigurationNode;

public class FabricConfigRegistry implements ConfigRegistry {
    public static ServerConfig<CommentedConfigurationNode, ConfigType> SERVER =
            new ServerConfig<>(
                    new FabricConfigBuilder(ConfigType.SERVER)
            );
    public static ClientConfig<CommentedConfigurationNode, ConfigType> CLIENT =
            new ClientConfig<>(
                    new FabricConfigBuilder(ConfigType.CLIENT)
            );
    public static CommonConfig<CommentedConfigurationNode, ConfigType> COMMON =
            new CommonConfig<>(
                    new FabricConfigBuilder(ConfigType.COMMON)
            );

    @Override
    public ServerConfig<?, ?> getServerConfig() {
        return SERVER;
    }

    @Override
    public ClientConfig<?, ?> getClientConfig() {
        return CLIENT;
    }

    @Override
    public CommonConfig<?, ?> getCommonConfig() {
        return COMMON;
    }

    public static void register() {
//        register(SERVER);
        register(CLIENT);
        register(COMMON);
    }

    private static void register(ConfigFile<CommentedConfigurationNode, ConfigType> file) {
        file.register((_, _, _) -> {});
    }
}
