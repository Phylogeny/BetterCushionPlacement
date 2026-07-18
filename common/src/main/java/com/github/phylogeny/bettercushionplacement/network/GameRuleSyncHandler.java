package com.github.phylogeny.bettercushionplacement.network;

import com.github.phylogeny.bettercushionplacement.registry.CommonGameRules;

public final class GameRuleSyncHandler {
    public static void handle(GameRuleSyncPayload payload) {
        CommonGameRules.ALLOW_INNER_WALL_CUSHION_PLACEMENT
                .setClientValue(payload.allowInnerWallCushionPlacement());
        CommonGameRules.CUSHIONS_SUPPORT_EACH_OTHER
                .setClientValue(payload.cushionsSupportEachOther());
    }
}