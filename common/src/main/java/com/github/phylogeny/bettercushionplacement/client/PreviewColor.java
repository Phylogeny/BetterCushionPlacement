package com.github.phylogeny.bettercushionplacement.client;

import com.github.phylogeny.bettercushionplacement.DyedAABB;
import com.github.phylogeny.bettercushionplacement.config.Configs;
import com.github.phylogeny.bettercushionplacement.util.LangUtil;
import net.minecraft.util.ARGB;

public enum PreviewColor {
    CUSHION_COLOR,
    COLOR_OVERRIDE,
    DYE_COLOR_OVERRIDE;

    public int getColor(DyedAABB spawnAABB, int alpha) {
        int rgb = switch (this) {
            case COLOR_OVERRIDE ->
                    Configs.CLIENT.placementPreview.colorOverride.get();
            case DYE_COLOR_OVERRIDE ->
                    Configs.CLIENT.placementPreview.dyeColorVariant.get()
                            .getColor(Configs.CLIENT.placementPreview.dyeColorOverride.get().dyeColor);
            default -> Configs.CLIENT.placementPreview.dyeColorVariant.get()
                        .getColor(spawnAABB.dyeColor);
        };
        return ARGB.color(alpha, rgb);
    }

    @Override
    public String toString() {
        return LangUtil.snakeCaseToTitleCase(name());
    }
}
