package com.github.phylogeny.bettercushionplacement;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.phys.AABB;

public class DyedAABB extends AABB {
    public final DyeColor dyeColor;

    public DyedAABB(AABB box, DyeColor dyeColor) {
        super(box.getMinPosition(), box.getMaxPosition());
        this.dyeColor = dyeColor;
    }
}
