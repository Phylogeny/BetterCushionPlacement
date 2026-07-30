package com.github.phylogeny.bettercushionplacement.config;

public interface ConfigNode {
    enum Type {
        FILE(".title"),
        FOLDER(".tooltip"),
        ENTRY(".tooltip");

        private final String suffix;

        Type(String suffix) {
            this.suffix = suffix;
        }

        public String getDescriptionKeySuffix() {
            return suffix;
        }
    }

    Type type();

    String translationKey();

    default String descriptionTranslationKey() {
        return translationKey() + type().getDescriptionKeySuffix();
    }
}
