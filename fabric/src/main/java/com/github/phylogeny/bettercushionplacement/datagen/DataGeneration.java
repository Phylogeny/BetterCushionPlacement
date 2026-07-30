package com.github.phylogeny.bettercushionplacement.datagen;

import com.github.phylogeny.bettercushionplacement.platform.Services;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DataGeneration implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        if (!Services.PLATFORM.isDevelopmentEnvironment())
            return;

        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ModLanguageProvider::new);
        pack.addProvider(ModBlockTagsProvider::new);
    }
}
