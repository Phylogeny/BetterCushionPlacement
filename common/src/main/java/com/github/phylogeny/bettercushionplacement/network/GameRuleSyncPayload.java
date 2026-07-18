package com.github.phylogeny.bettercushionplacement.network;

import com.github.phylogeny.bettercushionplacement.Constants;
import com.github.phylogeny.bettercushionplacement.registry.CommonGameRules;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;

public record GameRuleSyncPayload(boolean allowInnerWallCushionPlacement, boolean cushionsSupportEachOther)
        implements CustomPacketPayload {

    public static final Identifier ID = Constants.MOD_IDENTIFIER.withPath("game_rule_sync");

    public static final Type<GameRuleSyncPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, GameRuleSyncPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.BOOL,
                    GameRuleSyncPayload::allowInnerWallCushionPlacement,
                    ByteBufCodecs.BOOL,
                    GameRuleSyncPayload::cushionsSupportEachOther,
                    GameRuleSyncPayload::new
            );

    public GameRuleSyncPayload(MinecraftServer server) {
        this(
                CommonGameRules.ALLOW_INNER_WALL_CUSHION_PLACEMENT.get(server),
                CommonGameRules.CUSHIONS_SUPPORT_EACH_OTHER.get(server)
        );
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}