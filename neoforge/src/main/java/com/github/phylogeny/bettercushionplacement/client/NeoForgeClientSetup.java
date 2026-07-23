package com.github.phylogeny.bettercushionplacement.client;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.SubmitCustomGeometryEvent;

@EventBusSubscriber
public class NeoForgeClientSetup {

    @SubscribeEvent
    public static void renderCushionPlacementPreview(SubmitCustomGeometryEvent event) {
        ClientHelper.renderCushionPlacementPreview(
                event.getPoseStack(),
                event.getLevelRenderState(),
                event.getSubmitNodeCollector()
        );
    }
}
