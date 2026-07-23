package com.github.phylogeny.bettercushionplacement.datagen;

import com.github.phylogeny.bettercushionplacement.Constants;
import com.github.phylogeny.bettercushionplacement.mixin.MixinConfigPlugin;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ModLanguageProvider extends FabricLanguageProvider {
    protected ModLanguageProvider(
            FabricPackOutput packOutput,
            CompletableFuture<HolderLookup.Provider> registryLookup
    ) {
        super(packOutput, registryLookup);
    }

    @Override
    public void generateTranslations(
            HolderLookup.Provider registryLookup,
            TranslationBuilder translationBuilder
    ) {
        String modMenuKey = "modmenu.%s." + Constants.MOD_ID;
        translationBuilder.add(
                modMenuKey.formatted("nameTranslation"),
                Constants.MOD_DISPLAY_NAME
        );
        translationBuilder.add(
                modMenuKey.formatted("descriptionTranslation"),
                Constants.MOD_DESCRIPTION
        );
        translationBuilder.add(
                modMenuKey.formatted("summaryTranslation"),
                "Block/pixel grid snapping, placement previews, cushion stacking, & more."
        );
        for (MixinConfigPlugin.Mixin mixin : MixinConfigPlugin.Mixin.values()) {
            String key = "gamerule.%s.%s".formatted(Constants.MOD_ID, mixin.registryName);
            String name = Arrays
                    .stream(mixin.registryName.split("_"))
                    .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                    .collect(Collectors.joining(" "));
            translationBuilder.add(key, name);
            key += ".description";
            translationBuilder.add(key, mixin.comment);
        }
    }
}