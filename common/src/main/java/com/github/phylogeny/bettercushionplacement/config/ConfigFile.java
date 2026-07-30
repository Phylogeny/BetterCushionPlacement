package com.github.phylogeny.bettercushionplacement.config;

import com.github.phylogeny.bettercushionplacement.platform.services.registry.ConfigBuilder;
import com.github.phylogeny.bettercushionplacement.util.ConfigUtil;

public class ConfigFile<S, T> extends ConfigFolder<S, T> {
    @FunctionalInterface
    public interface Registrar<T, S> {
        void register(T type, S configSpec, String path);
    }

    public ConfigFile(ConfigBuilder<S, T> builder) {
        super(builder);
    }

    @Override
    public Type type() {
        return ConfigNode.Type.FILE;
    }

    public void register(Registrar<T, S> registrar) {
        registrar.register(
                builder.getType(),
                builder.build(),
                ConfigUtil.getConfigName(builder.getType(), builder.getExtension())
        );
    }
}
