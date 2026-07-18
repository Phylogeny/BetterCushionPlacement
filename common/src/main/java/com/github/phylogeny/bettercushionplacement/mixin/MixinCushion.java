package com.github.phylogeny.bettercushionplacement.mixin;

import com.github.phylogeny.bettercushionplacement.EntityHelper;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.Cushion;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Cushion.class)
public class MixinCushion {
    @Inject(
            method = "interact(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/InteractionResult;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void stackCushion(
            CallbackInfoReturnable<InteractionResult> cir,
            @Local(argsOnly = true, name = "player") Player player,
            @Local(argsOnly = true, name = "hand") InteractionHand hand
    ) {
        Optional.ofNullable(EntityHelper.stackCushion(player, (Cushion)(Object)this, hand))
                .ifPresent(cir::setReturnValue);
    }

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