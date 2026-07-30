package com.github.phylogeny.bettercushionplacement.config.entry;

public interface LoaderConfigEntry<T> {
    T get();

    void set(T value);
}
