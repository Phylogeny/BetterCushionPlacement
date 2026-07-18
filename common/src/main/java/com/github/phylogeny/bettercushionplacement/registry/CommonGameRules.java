package com.github.phylogeny.bettercushionplacement.registry;

import com.github.phylogeny.bettercushionplacement.network.SyncedGameRule;
import com.github.phylogeny.bettercushionplacement.platform.Services;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.serialization.Codec;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.world.level.gamerules.GameRuleType;
import net.minecraft.world.level.gamerules.GameRuleTypeVisitor;

public class CommonGameRules {
    public static final SyncedGameRule<Boolean> ALLOW_INNER_WALL_CUSHION_PLACEMENT =
            registerSyncedBoolean("allow_inner_wall_cushion_placement", GameRuleCategory.MISC, false);
    public static final SyncedGameRule<Boolean> CUSHIONS_SUPPORT_EACH_OTHER =
            registerSyncedBoolean("cushions_support_each_other", GameRuleCategory.MISC, false);

    private static SyncedGameRule<Boolean> registerSyncedBoolean(
            String name,
            GameRuleCategory category,
            boolean defaultValue
    ) {
        return new SyncedGameRule<>(Services.GAME_RULES.register(
                name,
                () -> new GameRule<>(
                        category,
                        GameRuleType.BOOL,
                        BoolArgumentType.bool(),
                        GameRuleTypeVisitor::visitBoolean,
                        Codec.BOOL,
                        b -> (Boolean) b ? 1 : 0,
                        defaultValue,
                        FeatureFlagSet.of()
                ))
        );
    }
}