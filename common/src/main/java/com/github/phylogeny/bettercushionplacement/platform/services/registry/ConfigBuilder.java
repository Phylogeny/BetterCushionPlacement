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
            boolean useTickBox,
            String comment,
            String translationKey
    );

    IntegerEntry defineInRange(
            String name,
            int defaultValue,
            int min,
            int max,
            @Nullable Integer increment,
            String comment,
            String translationKey
    );

    FloatEntry defineInRange(
            String name,
            float defaultValue,
            float min,
            float max,
            @Nullable Float increment,
            String comment,
            String translationKey
    );

    DoubleEntry defineInRange(
            String name,
            double defaultValue,
            double min,
            double max,
            @Nullable Double increment,
            String comment,
            String translationKey
    );

    <V extends Enum<V>> EnumEntry<V> defineEnum(
            String name,
            V defaultValue,
            String comment,
            String translationKey
    );
}
