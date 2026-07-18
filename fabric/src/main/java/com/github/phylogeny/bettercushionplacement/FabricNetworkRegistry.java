package com.github.phylogeny.bettercushionplacement;

import com.github.phylogeny.bettercushionplacement.network.GameRuleSyncHandler;
import com.github.phylogeny.bettercushionplacement.network.GameRuleSyncPayload;
import com.github.phylogeny.bettercushionplacement.network.SyncedGameRule;
import com.github.phylogeny.bettercushionplacement.registry.CommonGameRules;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;

public class FabricNetworkRegistry {
    public static void registerCommon() {
        PayloadTypeRegistry.clientboundPlay().register(
                GameRuleSyncPayload.TYPE,
                GameRuleSyncPayload.STREAM_CODEC
        );
        registerGameRuleChangeSync(CommonGameRules.ALLOW_INNER_WALL_CUSHION_PLACEMENT);
        registerGameRuleChangeSync(CommonGameRules.CUSHIONS_SUPPORT_EACH_OTHER);
        ServerPlayConnectionEvents.JOIN.register((handler, _, server) ->
                ServerPlayNetworking.send(handler.player, new GameRuleSyncPayload(server))
        );
    }

    private static void registerGameRuleChangeSync(SyncedGameRule<Boolean> syncedRule) {
        GameRuleEvents
                .changeCallback(syncedRule.rule())
                .register((_, server) -> {
                    GameRuleSyncPayload payload = new GameRuleSyncPayload(server);
                    for (ServerPlayer player : server.getPlayerList().getPlayers())
                        ServerPlayNetworking.send(player, payload);
                });
    }

    public static void registerClient() {
        ClientPlayNetworking.registerGlobalReceiver(
                GameRuleSyncPayload.TYPE,
                (payload, context) ->
                        context.client().execute(() ->
                                GameRuleSyncHandler.handle(payload))
        );
    }
}