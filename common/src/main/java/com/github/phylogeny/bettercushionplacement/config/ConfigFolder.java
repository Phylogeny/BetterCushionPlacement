package com.github.phylogeny.bettercushionplacement.config;

import com.github.phylogeny.bettercushionplacement.config.entry.BooleanEntry;
import com.github.phylogeny.bettercushionplacement.config.entry.EnumEntry;
import com.github.phylogeny.bettercushionplacement.config.entry.FloatEntry;
import com.github.phylogeny.bettercushionplacement.config.entry.IntegerEntry;
import com.github.phylogeny.bettercushionplacement.platform.services.registry.ConfigBuilder;
import com.github.phylogeny.bettercushionplacement.util.ConfigUtil;
import com.github.phylogeny.bettercushionplacement.util.LangUtil;
import com.google.common.collect.Lists;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class ConfigFolder<S, T> extends ConfigFolderBase {
    protected final ConfigBuilder<S, T> builder;
    private final String folderPath;

    public ConfigFolder(ConfigBuilder<S, T> builder) {
        this.builder = builder;
        folderPath = "";
        translationKey = LangUtil.addConfigFile(
                ConfigUtil.toLowerCase(builder.getType())
        );
    }

    public ConfigFolder(
            ConfigFolder<S, T> configFolder,
            String folderName,
            String folderTooltip
    ) {
        builder = configFolder.builder;
        String path = configFolder.folderPath + folderName;
        translationKey = LangUtil.addConfigFolder(
                path,
                folderTooltip,
                builder.getType()
        );
        folderPath = path + ".";
        builder.push(
                folderName,
                folderTooltip,
                translationKey
        );
    }

    public void save() {
        builder.save();
    }

    private String getTranslationKey(String name, String comment) {
        return LangUtil.addConfigEntry(
                folderPath + name,
                comment,
                builder.getType()
        );
    }

    protected BooleanEntry define(
            String name,
            boolean defaultValue,
            boolean useTickBox,
            String comment
    ) {
        return addNode(builder.define(
                name,
                defaultValue,
                useTickBox,
                comment,
                getTranslationKey(name, comment)
        ));
    }

    protected BooleanEntry define(
            String name,
            boolean defaultValue,
            String comment
    ) {
        return addNode(builder.define(
                name,
                defaultValue,
                false,
                comment,
                getTranslationKey(name, comment)
        ));
    }

    protected IntegerEntry defineInRange(
            String name,
            int defaultValue,
            int min,
            int max,
            @Nullable Integer increment,
            String comment
    ) {
        return addNode(builder.defineInRange(
                name,
                defaultValue,
                min,
                max,
                increment,
                comment,
                getTranslationKey(name, comment)
        ));
    }

    protected IntegerEntry defineInRange(
            String name,
            int defaultValue,
            int min,
            int max,
            String comment
    ) {
        return defineInRange(
                name,
                defaultValue,
                min,
                max,
                (Integer) null,
                comment
        );
    }

    protected FloatEntry defineInRange(
            String name,
            float defaultValue,
            float min,
            float max,
            @Nullable Float increment,
            String comment
    ) {
        return addNode(builder.defineInRange(
                name,
                defaultValue,
                min,
                max,
                increment,
                comment,
                getTranslationKey(name, comment)
        ));
    }

    protected FloatEntry defineInRange(
            String name,
            float defaultValue,
            float min,
            float max,
            String comment
    ) {
        return defineInRange(
                name,
                defaultValue,
                min,
                max,
                (Float) null,
                comment
        );
    }

    protected Supplier<Double> defineInRange(
            String name,
            double defaultValue,
            double min,
            double max,
            @Nullable Double increment,
            String comment
    ) {
        return addNode(builder.defineInRange(
                name,
                defaultValue,
                min,
                max,
                increment,
                comment,
                getTranslationKey(name, comment)
        ));
    }

    protected Supplier<Double> defineInRange(
            String name,
            double defaultValue,
            double min,
            double max,
            String comment
    ) {
        return defineInRange(
                name,
                defaultValue,
                min,
                max,
                (Double) null,
                comment
        );
    }

    protected <V extends Enum<V>> EnumEntry<V> defineEnum(
            String name,
            V defaultValue,
            String comment
    ) {
        return addNode(builder.defineEnum(
                name,
                defaultValue,
                comment,
                getTranslationKey(name, comment)
        ));
    }

    protected void closeFolder() {
        builder.pop();
    }
}
