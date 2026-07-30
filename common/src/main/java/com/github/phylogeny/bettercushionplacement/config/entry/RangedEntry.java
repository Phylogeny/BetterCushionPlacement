package com.github.phylogeny.bettercushionplacement.config.entry;

import java.util.Optional;

public interface RangedEntry<N extends Number> extends ConfigEntry<N> {
    N min();

    N max();

    Optional<N> getIncrement();
}
