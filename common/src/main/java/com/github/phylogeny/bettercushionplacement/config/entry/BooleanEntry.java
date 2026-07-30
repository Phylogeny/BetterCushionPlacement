package com.github.phylogeny.bettercushionplacement.config.entry;

public record BooleanEntry(
        @Override String translationKey,
        @Override Boolean defaultValue,
        @Override boolean requiresGameRestart,
        boolean useTickBox,
        LoaderConfigEntry<Boolean> entry
) implements ConfigEntry<Boolean> {
    @Override
    public Boolean get() {
        return entry.get();
    }

    @Override
    public void set(Boolean value) {
        entry.set(value);
    }
}
