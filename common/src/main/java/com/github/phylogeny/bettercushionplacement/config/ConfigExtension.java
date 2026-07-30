package com.github.phylogeny.bettercushionplacement.config;

public enum ConfigExtension {
    HOCON("conf"),
    TOML("toml");

    private final String extension;

    ConfigExtension(String extension) {
        this.extension = extension;
    }

    public String get() {
        return extension;
    }

    @Override
    public String toString() {
        return extension;
    }

    public String getFile(String name) {
        return name + "." + extension;
    }
}
