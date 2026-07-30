package com.github.phylogeny.bettercushionplacement.platform.registry;

import com.github.phylogeny.bettercushionplacement.platform.services.registry.IGameRuleRegistry;
import com.github.phylogeny.bettercushionplacement.registry.RegistryObj;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.gamerules.GameRule;

import java.util.function.Supplier;

public class FabricGameRuleRegistry implements IGameRuleRegistry {
    @Override
    public <T> RegistryObj<GameRule<T>> register(
            String name,
            Supplier<GameRule<T>> factory
    ) {
        return FabricRegistryManager.register(
                name,
                factory,
                BuiltInRegistries.GAME_RULE
        );
    }
}
