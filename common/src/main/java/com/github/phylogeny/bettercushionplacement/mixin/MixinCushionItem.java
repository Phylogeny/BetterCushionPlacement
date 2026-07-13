package com.github.phylogeny.bettercushionplacement.mixin;

import com.github.phylogeny.bettercushionplacement.EntityHelper;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.Cushion;
import net.minecraft.world.item.CushionItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(CushionItem.class)
public class MixinCushionItem {
    @ModifyVariable(
            method = "useOn(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;",
            at = @At("STORE"),
            ordinal = 0
    )
    private Vec3 adjustPlacementPosition(
            Vec3 entityPos,
            @Local BlockPlaceContext placeContext
    ) {
        return EntityHelper.adjustPlacementPosition(entityPos, placeContext);
    }

    @Inject(
            method = "useOn(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;",
            at = @At(
                    value = "INVOKE",
                    target = "getItemInHand()Lnet/minecraft/world/item/ItemStack;"
            ),
            cancellable = true
    )
    private void checkCushionIntersection(
            CallbackInfoReturnable<InteractionResult> cir,
            @Local Level level,
            @Local AABB spawnAABB
    ) {
        Optional.ofNullable(EntityHelper.checkCushionIntersection(level, spawnAABB))
                .ifPresent(cir::setReturnValue);
    }

    @Inject(
            method = "useOn(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;",
            at = @At(
                    value = "INVOKE",
                    target = "addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"
            )
    )
    private void adjustEntityPosition(
            CallbackInfoReturnable<InteractionResult> cir,
            @Local Cushion entity,
            @Local Vec3 entityPos,
            @Local BlockPlaceContext placeContext
    ) {
        EntityHelper.adjustEntityPosition(entity, entityPos, placeContext);
    }
}