package com.github.phylogeny.bettercushionplacement.network;

import com.github.phylogeny.bettercushionplacement.Constants;
import com.github.phylogeny.bettercushionplacement.registry.CommonGameRules;
import com.github.phylogeny.bettercushionplacement.registry.RegistryObj;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.gamerules.GameRule;

public record GameRuleSyncPayload(boolean allowInnerWallCushionPlacement)
        implements CustomPacketPayload {

    public static final Identifier ID = Constants.MOD_IDENTIFIER.withPath("game_rule_sync");

    public static final Type<GameRuleSyncPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, GameRuleSyncPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.BOOL,
                    GameRuleSyncPayload::allowInnerWallCushionPlacement,
                    GameRuleSyncPayload::new
            );

    public GameRuleSyncPayload(MinecraftServer server) {
        this(getValue(server, CommonGameRules.ALLOW_INNER_WALL_CUSHION_PLACEMENT));
    }

    private static boolean getValue(MinecraftServer server, RegistryObj<GameRule<Boolean>> rule) {
        return server.getGameRules().get(rule.get());
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}