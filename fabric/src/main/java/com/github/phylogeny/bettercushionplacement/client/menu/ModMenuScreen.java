package com.github.phylogeny.bettercushionplacement.client.menu;

import com.github.phylogeny.bettercushionplacement.Constants;
import com.github.phylogeny.bettercushionplacement.YaclConfigScreen;
import com.github.phylogeny.bettercushionplacement.platform.Services;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuScreen implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            if (Services.PLATFORM.isModLoaded(Constants.YACL_MOD_ID))
                return YaclConfigScreen.create(parent);

            return null;
        };
    }
}
