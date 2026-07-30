package com.github.phylogeny.bettercushionplacement.platform.services.registry;

import com.github.phylogeny.bettercushionplacement.config.ConfigExtension;
import com.github.phylogeny.bettercushionplacement.config.entry.*;
import org.jetbrains.annotations.Nullable;

public interface ConfigBuilder<S, T> {
    ConfigExtension getExtension();

    T getType();

    S build();

    void save();

    void push(
            String name,
            String comment,
            String translationKey
    );

    void pop();

    BooleanEntry define(
            String name,
            boolean defaultValue,
            boolean requiresGameRestart,
            boolean useTickBox,
            String comment,
            String translationKey
    );

    IntegerEntry defineInRange(
            String name,
            int defaultValue,
            boolean requiresGameRestart,
            int min,
            int max,
            @Nullable Integer increment,
            String comment,
            String translationKey
    );

    FloatEntry defineInRange(
            String name,
            float defaultValue,
            boolean requiresGameRestart,
            float min,
            float max,
            @Nullable Float increment,
            String comment,
            String translationKey
    );

    DoubleEntry defineInRange(
            String name,
            double defaultValue,
            boolean requiresGameRestart,
            double min,
            double max,
            @Nullable Double increment,
            String comment,
            String translationKey
    );

    <V extends Enum<V>> EnumEntry<V> defineEnum(
            String name,
            V defaultValue,
            boolean requiresGameRestart,
            String comment,
            String translationKey
    );
}
