package com.github.phylogeny.bettercushionplacement.registry;

import com.github.phylogeny.bettercushionplacement.mixin.MixinConfigPlugin;
import com.github.phylogeny.bettercushionplacement.network.ImmutableSyncedGameRule;
import com.github.phylogeny.bettercushionplacement.network.MutableSyncedGameRule;
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
            registerSyncedBoolean(
                    MixinConfigPlugin.Mixin.INNER_WALL_CUSHION_PLACEMENT,
                    GameRuleCategory.MISC,
                    false
            );
    public static final SyncedGameRule<Boolean> CUSHIONS_SUPPORT_EACH_OTHER =
            registerSyncedBoolean(
                    MixinConfigPlugin.Mixin.CUSHIONS_SUPPORT_EACH_OTHER,
                    GameRuleCategory.MISC,
                    false
            );

    private static SyncedGameRule<Boolean> registerSyncedBoolean(
            MixinConfigPlugin.Mixin mixin,
            GameRuleCategory category,
            boolean defaultValue
    ) {
        if (mixin.getMode() != MixinConfigPlugin.MixinMode.GAME_RULE)
            return new ImmutableSyncedGameRule<>(mixin.getMode() == MixinConfigPlugin.MixinMode.ENABLED);
 
        return new MutableSyncedGameRule<>(Services.GAME_RULES.register(
                mixin.registryName,
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