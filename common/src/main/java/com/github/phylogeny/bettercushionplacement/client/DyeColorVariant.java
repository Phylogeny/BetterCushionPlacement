package com.github.phylogeny.bettercushionplacement.client;

import com.github.phylogeny.bettercushionplacement.util.LangUtil;
import net.minecraft.world.item.DyeColor;

import java.util.function.Function;

public enum DyeColorVariant {
    TEXTURE_DIFFUSE(DyeColor::getTextureDiffuseColor),
    TEXT(DyeColor::getTextColor),
    FIREWORK(DyeColor::getFireworkColor),
    MAP(dyeColor -> dyeColor.getMapColor().col),
    TERRACOTTA(dyeColor -> dyeColor.getTerracottaColor().col);

    private final Function<DyeColor, Integer> colorGetter;

    DyeColorVariant(Function<DyeColor, Integer> colorGetter) {
        this.colorGetter = colorGetter;
    }

    public int getColor(DyeColor dyeColor) {
        return colorGetter.apply(dyeColor);
    }

    @Override
    public String toString() {
        return LangUtil.snakeCaseToTitleCase(name());
    }
}
