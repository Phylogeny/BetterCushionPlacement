package com.github.phylogeny.bettercushionplacement.client;

import com.github.phylogeny.bettercushionplacement.FabricNetworkRegistry;
import net.fabricmc.api.ClientModInitializer;

public class FabricClientSetup implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FabricNetworkRegistry.registerClient();
    }
}