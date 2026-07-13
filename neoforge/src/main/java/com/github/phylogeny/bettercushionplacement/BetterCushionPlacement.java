package com.github.phylogeny.bettercushionplacement;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class BetterCushionPlacement {
    public BetterCushionPlacement(IEventBus eventBus) {
        CommonClass.init();
    }
}