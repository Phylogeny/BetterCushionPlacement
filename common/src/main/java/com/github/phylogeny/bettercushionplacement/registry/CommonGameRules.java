package com.github.phylogeny.bettercushionplacement.registry;

import com.github.phylogeny.bettercushionplacement.platform.Services;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.serialization.Codec;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.world.level.gamerules.GameRuleType;
import net.minecraft.world.level.gamerules.GameRuleTypeVisitor;

public class CommonGameRules {
    public static final RegistryObj<GameRule<Boolean>> ALLOW_INNER_WALL_CUSHION_PLACEMENT =
            register("allow_inner_wall_cushion_placement", GameRuleCategory.MISC, false);

    private static RegistryObj<GameRule<Boolean>> register(
            String name,
            GameRuleCategory category,
            boolean defaultValue
    ) {
        return Services.GAME_RULES.register(
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
                ));
    }
}