package com.github.phylogeny.bettercushionplacement.network;

public final class GameRuleSyncHandler {
    public static volatile boolean allowInnerWallCushionPlacement = true;

    public static void handle(GameRuleSyncPayload payload) {
        allowInnerWallCushionPlacement = payload.allowInnerWallCushionPlacement();
    }
}