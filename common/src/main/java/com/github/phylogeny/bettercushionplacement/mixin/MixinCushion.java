package com.github.phylogeny.bettercushionplacement.mixin;

import com.github.phylogeny.bettercushionplacement.EntityHelper;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.Cushion;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Cushion.class)
public class MixinCushion {
    @Redirect(
            method = "interact(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/InteractionResult;",
            at = @At(
                    value = "INVOKE",
                    target = "isSecondaryUseActive()Z"
            )
    )
    private boolean allowCushionInteraction(
            Player player,
            @Local InteractionHand hand
    ) {
        return EntityHelper.allowCushionInteraction(player, hand);
    }

    @Redirect(
            method = "interact(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/InteractionResult;",
            at = @At(
                    value = "INVOKE",
                    target = "startRiding(Lnet/minecraft/world/entity/Entity;)Z"
            )
    )
    private boolean stackCushion(
            Player player,
            Entity cushion,
            @Local InteractionHand hand
    ) {
        return EntityHelper.stackCushion(player, cushion, hand);
    }
}