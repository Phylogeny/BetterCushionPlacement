package com.github.phylogeny.bettercushionplacement.mixin;

import com.github.phylogeny.bettercushionplacement.EntityHelper;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.decoration.Cushion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Cushion.class)
public class MixinCushionsSupportEachOther {
    @ModifyReturnValue(
            method = "wouldSuriveAt(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/phys/AABB;)Z",
            at = @At("RETURN")
    )
    private static boolean allowStackedCushions(
            boolean wouldSurvive,
            @Local(argsOnly = true, name = "level") Level level,
            @Local(argsOnly = true, name = "boundingBox") AABB boundingBox,
            @Local(name = "anchorBox") AABB anchorBox
    ) {
        return wouldSurvive || EntityHelper.allowStackedCushions(level, boundingBox, anchorBox);
    }
}