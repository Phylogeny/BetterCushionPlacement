package com.github.phylogeny.bettercushionplacement.config;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.nio.file.Path;

public class HoconConfig implements EarlyConfig {
    @Nullable
    private final CommentedConfigurationNode root;

    public HoconConfig(Path path) {
        root = getRoot(path);
    }

    @Nullable
    private CommentedConfigurationNode getRoot(Path path) {
        try {
            return HoconConfigurationLoader
                    .builder()
                    .path(path)
                    .build()
                    .load();
        } catch (ConfigurateException _) {
            return null;
        }
    }

    @Override
    public String getValue(String path, String defaultValue) {
        if (root == null)
            return defaultValue;

        Object[] pathArray = path.split("\\.");
        return root.node(pathArray)
                .getString(defaultValue);
    }
}
