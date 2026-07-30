package com.github.phylogeny.bettercushionplacement.platform.registry;

import com.github.phylogeny.bettercushionplacement.Constants;
import com.github.phylogeny.bettercushionplacement.platform.services.registry.IGameRuleRegistry;
import com.github.phylogeny.bettercushionplacement.registry.RegistryObj;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.gamerules.GameRule;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class NeoForgeGameRuleRegistry implements IGameRuleRegistry {
    public static final DeferredRegister<GameRule<?>> GAME_RULES = DeferredRegister.create(Registries.GAME_RULE, Constants.MOD_ID);

    @Override
    public <T> RegistryObj<GameRule<T>> register(
            String name,
            Supplier<GameRule<T>> factory
    ) {
        return RegistryObj.register(name, factory, GAME_RULES::register);
    }
}
