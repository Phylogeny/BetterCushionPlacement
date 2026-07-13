package com.github.phylogeny.bettercushionplacement;

import com.github.phylogeny.bettercushionplacement.platform.registry.FabricRegistryManager;
import net.fabricmc.api.ModInitializer;

public class BetterCushionPlacement implements ModInitializer {
    @Override
    public void onInitialize() {
        CommonClass.init();
        FabricRegistryManager.register();
    }
}