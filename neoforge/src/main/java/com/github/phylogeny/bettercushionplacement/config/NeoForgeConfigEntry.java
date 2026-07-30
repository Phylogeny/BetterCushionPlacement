package com.github.phylogeny.bettercushionplacement.config;

import com.github.phylogeny.bettercushionplacement.config.entry.LoaderConfigEntry;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class NeoForgeConfigEntry<T> implements LoaderConfigEntry<T> {
    private final Supplier<T> valueGetter;
    private final Consumer<T> valueSetter;

    public NeoForgeConfigEntry(
            Supplier<T> valueGetter,
            Consumer<T> valueSetter
    ) {
        this.valueGetter = valueGetter;
        this.valueSetter = valueSetter;
    }

    @Override
    public T get() {
        return valueGetter.get();
    }

    @Override
    public void set(T value) {
        valueSetter.accept(value);
    }
}
