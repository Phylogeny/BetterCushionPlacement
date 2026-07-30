package com.github.phylogeny.bettercushionplacement;

import com.github.phylogeny.bettercushionplacement.platform.registry.FabricConfigRegistry;
import com.github.phylogeny.bettercushionplacement.platform.registry.FabricRegistryManager;
import net.fabricmc.api.ModInitializer;

public class FabricBetterCushionPlacement implements ModInitializer {
    @Override
    public void onInitialize() {
        CommonClass.init();
        FabricRegistryManager.register();
        FabricConfigRegistry.register();
    }
}
