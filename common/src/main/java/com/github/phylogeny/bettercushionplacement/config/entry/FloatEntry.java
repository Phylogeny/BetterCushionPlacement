package com.github.phylogeny.bettercushionplacement.config.entry;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record FloatEntry(
        @Override String translationKey,
        @Override Float defaultValue,
        @Override boolean requiresGameRestart,
        @Override Float min,
        @Override Float max,
        @Nullable Float increment,
        LoaderConfigEntry<Float> entry
) implements RangedEntry<Float> {
    @Override
    public Float get() {
        return entry.get();
    }

    @Override
    public void set(Float value) {
        entry.set(value);
    }

    @Override
    public Optional<Float> getIncrement() {
        return Optional.ofNullable(increment);
    }
}
