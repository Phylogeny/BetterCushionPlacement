package com.github.phylogeny.bettercushionplacement.registry;

import com.github.phylogeny.bettercushionplacement.platform.Services;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;

public class CommonGameRules {
    public static final RegistryObj<GameRule<Boolean>> SNAP_CUSHION_ELEVATION_TO_PIXEL_GRID =
            Services.GAME_RULES.createAndRegisterBoolean(
                    "snap_cushion_elevation_to_pixel_grid",
                    false,
                    GameRuleCategory.MISC
            );
}