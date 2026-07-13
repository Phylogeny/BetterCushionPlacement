package com.github.phylogeny.bettercushionplacement.platform.registry;

import com.github.phylogeny.bettercushionplacement.platform.services.registry.IGameRuleRegistry;
import com.github.phylogeny.bettercushionplacement.registry.RegistryObj;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;

public class FabricGameRuleRegistry implements IGameRuleRegistry {
    public RegistryObj<GameRule<Boolean>> createAndRegisterBoolean(
            String name,
            boolean defaultValue,
            GameRuleCategory category
    ) {
        return FabricRegistryManager.register(
                name,
                () -> GameRuleBuilder
                        .forBoolean(defaultValue)
                        .category(category)
                        .build(),
                BuiltInRegistries.GAME_RULE
        );
    }
}