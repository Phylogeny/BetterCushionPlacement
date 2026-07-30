package com.github.phylogeny.bettercushionplacement.config.entry;

public record EnumEntry<E extends Enum<E>>(
        @Override String translationKey,
        @Override E defaultValue,
        LoaderConfigEntry<E> entry
) implements ConfigEntry<E> {
    public E get() {
        return entry.get();
    }

    public void set(E value) {
        entry.set(value);
    }
}
