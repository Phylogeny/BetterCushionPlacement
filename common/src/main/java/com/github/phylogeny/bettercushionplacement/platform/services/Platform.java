package com.github.phylogeny.bettercushionplacement.platform.services;

public enum Platform {
    NEO_FORGE("NeoForge"),
    FORGE("Forge"),
    FABRIC("Fabric");

    public final String title;

    Platform(String title) {
        this.title = title;
    }
}