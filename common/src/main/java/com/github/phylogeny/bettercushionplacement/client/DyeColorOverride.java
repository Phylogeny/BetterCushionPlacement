package com.github.phylogeny.bettercushionplacement.client;

import com.github.phylogeny.bettercushionplacement.util.LangUtil;
import net.minecraft.world.item.DyeColor;

public enum DyeColorOverride {
    WHITE_DYE(DyeColor.WHITE),
    ORANGE_DYE(DyeColor.ORANGE),
    MAGENTA_DYE(DyeColor.MAGENTA),
    LIGHT_BLUE_DYE(DyeColor.LIGHT_BLUE),
    YELLOW_DYE(DyeColor.YELLOW),
    LIME_DYE(DyeColor.LIME),
    PINK_DYE(DyeColor.PINK),
    GRAY_DYE(DyeColor.GRAY),
    LIGHT_GRAY_DYE(DyeColor.LIGHT_GRAY),
    CYAN_DYE(DyeColor.CYAN),
    PURPLE_DYE(DyeColor.PURPLE),
    BLUE_DYE(DyeColor.BLUE),
    BROWN_DYE(DyeColor.BROWN),
    GREEN_DYE(DyeColor.GREEN),
    RED_DYE(DyeColor.RED),
    BLACK_DYE(DyeColor.BLACK);

    public final DyeColor dyeColor;

    DyeColorOverride(DyeColor dyeColor) {
        this.dyeColor = dyeColor;
    }

    @Override
    public String toString() {
        return LangUtil.snakeCaseToTitleCase(name());
    }
}
