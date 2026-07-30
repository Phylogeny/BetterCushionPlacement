package com.github.phylogeny.bettercushionplacement.mixin;

import com.github.phylogeny.bettercushionplacement.util.EntityUtil;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.Cushion;
import net.minecraft.world.item.CushionItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
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
public class MixinCushionPlacement {
    @ModifyVariable(
            method = "useOn(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;",
            at = @At("STORE"),
            name = "entityPos")
    private Vec3 adjustPlacementPosition(
            Vec3 entityPos,
            @Local(name = "recalculatedContext") UseOnContext recalculatedContext
    ) {
        return EntityUtil.adjustPlacementPosition(entityPos, recalculatedContext);
    }

    @Inject(
            method = "useOn(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/context/UseOnContext;getItemInHand()Lnet/minecraft/world/item/ItemStack;"
            ),
            cancellable = true
    )
    private void checkCushionIntersection(
            CallbackInfoReturnable<InteractionResult> cir,
            @Local(name = "level") Level level,
            @Local(name = "spawnAABB") AABB spawnAABB
    ) {
        Optional.ofNullable(EntityUtil.checkCushionIntersection(level, spawnAABB))
                .ifPresent(cir::setReturnValue);
    }

    @Inject(
            method = "useOn(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"
            )
    )
    private void adjustEntityPosition(
            CallbackInfoReturnable<InteractionResult> cir,
            @Local(name = "cushion") Cushion cushion,
            @Local(name = "entityPos") Vec3 entityPos,
            @Local(name = "placeContext") BlockPlaceContext placeContext
    ) {
        EntityUtil.adjustEntityPosition(cushion, entityPos, placeContext);
    }
}
