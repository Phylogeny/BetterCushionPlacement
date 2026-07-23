package com.github.phylogeny.bettercushionplacement.client;

import com.github.phylogeny.bettercushionplacement.FabricNetworkRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;

public class FabricClientSetup implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FabricNetworkRegistry.registerClient();
        LevelRenderEvents.AFTER_TRANSLUCENT_FEATURES.register(context -> {
            ClientHelper.renderCushionPlacementPreview(
                    context.poseStack(),
                    context.levelState(),
                    context.submitNodeCollector()
            );
        });
    }
}