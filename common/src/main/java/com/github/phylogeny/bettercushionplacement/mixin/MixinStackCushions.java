package com.github.phylogeny.bettercushionplacement.mixin;

import com.github.phylogeny.bettercushionplacement.util.EntityUtil;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.Cushion;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Cushion.class)
public class MixinStackCushions {
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
        Optional.ofNullable(EntityUtil.stackCushion(player, (Cushion)(Object)this, hand))
                .ifPresent(cir::setReturnValue);
    }
}
