package com.github.phylogeny.bettercushionplacement.platform.services;

public enum Environment {
    DEVELOPMENT("Development"),
    PRODUCTION("Production");

    public final String title;

    Environment(String title) {
        this.title = title;
    }
}
