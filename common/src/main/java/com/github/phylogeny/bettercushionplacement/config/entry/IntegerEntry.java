package com.github.phylogeny.bettercushionplacement.config.entry;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record IntegerEntry(
        @Override String translationKey,
        @Override Integer defaultValue,
        @Override Integer min,
        @Override Integer max,
        @Nullable Integer increment,
        LoaderConfigEntry<Integer> entry
) implements RangedEntry<Integer> {
    @Override
    public Integer get() {
        return entry.get();
    }

    @Override
    public void set(Integer value) {
        entry.set(value);
    }

    @Override
    public Optional<Integer> getIncrement() {
        return Optional.ofNullable(increment);
    }
}
