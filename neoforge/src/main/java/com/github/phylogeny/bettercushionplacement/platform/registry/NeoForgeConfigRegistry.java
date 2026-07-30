package com.github.phylogeny.bettercushionplacement.platform.registry;

import com.github.phylogeny.bettercushionplacement.config.ClientConfig;
import com.github.phylogeny.bettercushionplacement.config.CommonConfig;
import com.github.phylogeny.bettercushionplacement.config.ServerConfig;
import com.github.phylogeny.bettercushionplacement.platform.services.registry.ConfigRegistry;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class NeoForgeConfigRegistry implements ConfigRegistry {
    public static final ServerConfig<ModConfigSpec, ModConfig.Type> SERVER =
            new ServerConfig<>(
                    new NeoForgeConfigBuilder(ModConfig.Type.SERVER)
            );
    public static final ClientConfig<ModConfigSpec, ModConfig.Type> CLIENT =
            new ClientConfig<>(
                    new NeoForgeConfigBuilder(ModConfig.Type.CLIENT)
            );
    public static final CommonConfig<ModConfigSpec, ModConfig.Type> COMMON =
            new CommonConfig<>(
                    new NeoForgeConfigBuilder(ModConfig.Type.COMMON)
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

    public static void register(ModContainer modContainer) {
//        SERVER.register(modContainer::registerConfig);
        CLIENT.register(modContainer::registerConfig);
        COMMON.register(modContainer::registerConfig);
    }
}
