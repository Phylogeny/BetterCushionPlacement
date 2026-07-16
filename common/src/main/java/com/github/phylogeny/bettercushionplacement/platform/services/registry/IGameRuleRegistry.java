package com.github.phylogeny.bettercushionplacement.platform.services.registry;

import com.github.phylogeny.bettercushionplacement.registry.RegistryObj;
import net.minecraft.world.level.gamerules.GameRule;

import java.util.function.Supplier;

public interface IGameRuleRegistry {
    <T> RegistryObj<GameRule<T>> register(
            String name,
            Supplier<GameRule<T>> factory
    );
}