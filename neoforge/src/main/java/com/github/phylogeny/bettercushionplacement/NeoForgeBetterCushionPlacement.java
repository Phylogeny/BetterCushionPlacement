package com.github.phylogeny.bettercushionplacement;

import com.github.phylogeny.bettercushionplacement.platform.registry.NeoForgeGameRuleRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class NeoForgeBetterCushionPlacement {
    public NeoForgeBetterCushionPlacement(IEventBus eventBus) {
        CommonClass.init();
        NeoForgeGameRuleRegistry.GAME_RULES.register(eventBus);
    }
}