package com.github.phylogeny.bettercushionplacement.config.entry;

import com.github.phylogeny.bettercushionplacement.config.ConfigNode;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface ConfigEntry<T> extends ConfigNode, Supplier<T>, Consumer<T> {
    @Override
    default Type type() {
        return Type.ENTRY;
    }

    T defaultValue();

    @Override
    T get();

    void set(T value);

    @Override
    default void accept(T value) {
        set(value);
    }

    boolean requiresGameRestart();
}
