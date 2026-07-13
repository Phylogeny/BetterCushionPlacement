package com.github.phylogeny.bettercushionplacement.platform.services;

public interface IPlatformHelper {

    /**
     * Gets the current platform.
     *
     * @return The current platform.
     */
    Platform getPlatform();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the current environment type.
     *
     * @return The current environment type.
     */
    default Environment getEnvironment() {

        return isDevelopmentEnvironment() ? Environment.DEVELOPMENT : Environment.PRODUCTION;
    }
}