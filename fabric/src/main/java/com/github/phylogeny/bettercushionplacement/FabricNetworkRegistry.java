package com.github.phylogeny.bettercushionplacement;

import com.github.phylogeny.bettercushionplacement.network.GameRuleSyncHandler;
import com.github.phylogeny.bettercushionplacement.network.GameRuleSyncPayload;
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
        GameRuleEvents
                .changeCallback(CommonGameRules.ALLOW_INNER_WALL_CUSHION_PLACEMENT.get())
                .register((value, server) -> {
                    GameRuleSyncPayload payload = new GameRuleSyncPayload(value);
                    for (ServerPlayer player : server.getPlayerList().getPlayers())
                        ServerPlayNetworking.send(player, payload);
                });
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayNetworking.send(handler.player, new GameRuleSyncPayload(server));
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