package com.github.phylogeny.bettercushionplacement;

import com.github.phylogeny.bettercushionplacement.network.GameRuleSyncHandler;
import com.github.phylogeny.bettercushionplacement.network.GameRuleSyncPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.GameRuleChangedEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@EventBusSubscriber
public class NeoForgeNetworkRegistry {
    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        event.registrar("1")
                .playToClient(
                        GameRuleSyncPayload.TYPE,
                        GameRuleSyncPayload.STREAM_CODEC,
                        (payload, context) ->
                                context.enqueueWork(() ->
                                        GameRuleSyncHandler.handle(payload))
                );
    }

    @SubscribeEvent
    public static void onGameRuleChanged(GameRuleChangedEvent event) {
        PacketDistributor.sendToAllPlayers(new GameRuleSyncPayload(event.getServer()));
    }

    @SubscribeEvent
    public static void onPlayerJoinServer(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer)
            PacketDistributor.sendToPlayer(serverPlayer, new GameRuleSyncPayload(serverPlayer.level().getServer()));
    }
}
