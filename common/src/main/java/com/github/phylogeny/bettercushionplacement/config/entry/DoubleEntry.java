package com.github.phylogeny.bettercushionplacement.config.entry;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record DoubleEntry(
        @Override String translationKey,
        @Override Double defaultValue,
        @Override Double min,
        @Override Double max,
        @Nullable Double increment,
        LoaderConfigEntry<Double> entry
) implements RangedEntry<Double> {
    @Override
    public Double get() {
        return entry.get();
    }

    @Override
    public void set(Double value) {
        entry.set(value);
    }

    @Override
    public Optional<Double> getIncrement() {
        return Optional.ofNullable(increment);
    }
}
