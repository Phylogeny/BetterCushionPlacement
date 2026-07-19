package com.github.phylogeny.bettercushionplacement.mixin;

import com.github.phylogeny.bettercushionplacement.EntityHelper;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.item.CushionItem;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CushionItem.class)
public class MixinInnerWallCushionPlacement {
    @Inject(
            method = "recalculateContextForSpecialCollisionShapes(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/item/context/UseOnContext;",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void bypassCollisionBlockTags(
            CallbackInfoReturnable<UseOnContext> cir,
            @Local(argsOnly = true, name = "context") UseOnContext context
    ) {
        if (EntityHelper.bypassCollisionBlockTags(context))
            cir.setReturnValue(context);
    }
}
