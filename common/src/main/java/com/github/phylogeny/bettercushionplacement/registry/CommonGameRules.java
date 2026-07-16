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
    public static final RegistryObj<GameRule<Boolean>> SNAP_CUSHION_ELEVATION_TO_PIXEL_GRID =
            Services.GAME_RULES.register(
                    "snap_cushion_elevation_to_pixel_grid",
                    () -> new GameRule<>(
                            GameRuleCategory.MISC,
                            GameRuleType.BOOL,
                            BoolArgumentType.bool(),
                            GameRuleTypeVisitor::visitBoolean,
                            Codec.BOOL,
                            b -> (Boolean) b ? 1 : 0,
                            false,
                            FeatureFlagSet.of()
                    ));
}