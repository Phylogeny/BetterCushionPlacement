package com.github.phylogeny.bettercushionplacement.platform.services.registry;

import com.github.phylogeny.bettercushionplacement.registry.RegistryObj;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;

public interface IGameRuleRegistry {
    RegistryObj<GameRule<Boolean>> createAndRegisterBoolean(
            String name,
            boolean defaultValue,
            GameRuleCategory category
    );
}