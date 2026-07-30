package com.github.phylogeny.bettercushionplacement.client;

import com.github.phylogeny.bettercushionplacement.Constants;
import com.github.phylogeny.bettercushionplacement.YaclConfigScreen;
import com.github.phylogeny.bettercushionplacement.platform.Services;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.SubmitCustomGeometryEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(Dist.CLIENT)
public class NeoForgeClientSetup {
    public NeoForgeClientSetup(ModContainer container) {
        container.registerExtensionPoint(
                IConfigScreenFactory.class,
                (cont, parent) -> Services.PLATFORM.isModLoaded(Constants.YACL_MOD_ID)
                        ? YaclConfigScreen.create(parent)
                        : new ConfigurationScreen(cont, parent)
        );
    }

    @SubscribeEvent
    public static void renderCushionPlacementPreview(SubmitCustomGeometryEvent event) {
        ClientUtil.renderCushionPlacementPreview(
                event.getPoseStack(),
                event.getLevelRenderState(),
                event.getSubmitNodeCollector()
        );
    }
}
