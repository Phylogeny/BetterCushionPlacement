package com.github.phylogeny.bettercushionplacement.config;

import com.moandjiezana.toml.Toml;

import java.nio.file.Path;
import java.util.Optional;

public class TomlConfig implements EarlyConfig {
    private final Toml config;

    public TomlConfig(Path path) {
        config = new Toml().read(path.toFile());
    }

    @Override
    public String getValue(String path, String defaultValue) {
        return Optional
                .ofNullable(config.getString(path))
                .orElse(defaultValue);
    }
}
