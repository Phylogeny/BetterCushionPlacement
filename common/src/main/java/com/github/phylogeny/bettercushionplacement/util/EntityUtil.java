package com.github.phylogeny.bettercushionplacement.util;

import com.github.phylogeny.bettercushionplacement.client.DyedAABB;
import com.github.phylogeny.bettercushionplacement.registry.CommonGameRules;
import com.github.phylogeny.bettercushionplacement.registry.CommonTags;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.decoration.Cushion;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CushionItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Predicate;

public class EntityUtil {
    private enum Grid {
        BLOCK(1.0),
        PIXEL(1 / 16.0);

        private final double cellSize;

        Grid(double cellSize) {
            this.cellSize = cellSize;
        }

        public Vec3 snapTo(Vec3 pos) {
            double x = Math.round(pos.x / cellSize) * cellSize;
            double y = Math.round(pos.y / cellSize) * cellSize;
            double z = Math.round(pos.z / cellSize) * cellSize;
            return new Vec3(x, y, z);
        }
    }

    @Nullable
    public static DyedAABB getCushionSpawnBox(Player player, Level level, HitResult hitResult) {
        ItemStack heldStack = null;
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack stack = player.getItemInHand(hand);
            if (stack.is(ItemTags.CUSHIONS)) {
                heldStack = stack;
                break;
            }
        }
        if (heldStack == null)
            return null;

        if (hitResult instanceof EntityHitResult entityHit
                && entityHit.getEntity() instanceof Cushion cushion)
            hitResult = getStackedCushionHitResult(player, cushion, heldStack);

        if (!(hitResult instanceof BlockHitResult blockHit)
                || blockHit.getDirection() != Direction.UP)
            return null;

        BlockPos pos = blockHit.getBlockPos();
        Vec3 spawnVec = hitResult.getLocation();
        BlockHitResult blockHitResult = new BlockHitResult(
                spawnVec,
                Direction.UP,
                pos,
                false
        );
        UseOnContext context = new UseOnContext(player, InteractionHand.MAIN_HAND, blockHitResult);
        UseOnContext recalculatedContext = CushionItem.recalculateContextForSpecialCollisionShapes(context);
        if (recalculatedContext.getClickedFace() != Direction.UP)
            return null;

        BlockPlaceContext placeContext = new BlockPlaceContext(recalculatedContext);
        Vec3 entityPos = Vec3.atCenterOfWithY(
                pos.relative(blockHit.getDirection()),
                placeContext.getClickLocation().y
        );
        spawnVec = adjustPlacementPosition(entityPos, recalculatedContext);
        AABB spawnAABB = EntityTypes.CUSHION.getSpawnAABB(spawnVec);
        if (intersectsCushion(level, spawnAABB) || !Cushion.wouldSuriveAt(level, spawnAABB))
            return null;

        DyeColor color = heldStack.getOrDefault(
                DataComponents.CUSHION_COLOR,
                DyeColor.BLUE
        );
        return new DyedAABB(spawnAABB, color);
    }

    private static boolean isSneaking(UseOnContext context) {
        return Optional.ofNullable(context.getPlayer())
                .map(Player::isSecondaryUseActive)
                .orElse(false);
    }

    private static boolean isSprinting(UseOnContext context) {
        Player player = context.getPlayer();
        if (player instanceof ServerPlayer serverPlayer)
            return serverPlayer.getLastClientInput().sprint();
        else if (player instanceof LocalPlayer localPlayer)
            return localPlayer.input.keyPresses.sprint();

        return false;
    }

    public static Vec3 adjustPlacementPosition(
            Vec3 original,
            UseOnContext context
    ) {
        if (context.getLevel()
                .getBlockState(context.getClickedPos())
                .is(CommonTags.Blocks.NORMAL_CUSHION_PLACEMENT))
            return original;

        Vec3 clicked = context.getClickLocation();
        boolean sneaking = isSneaking(context);
        boolean sprinting = isSprinting(context);
        if (sneaking) {
            Vec3 finalClicked = clicked;
            Cushion cushion = context.getLevel().getEntitiesOfClass(
                    Cushion.class,
                    AABB.ofSize(original, 0.1, 0.1, 0.1),
                    entity -> entity.position().equals(finalClicked)
            ).stream().findAny().orElse(null);
            if (cushion != null) {
                clicked = cushion.position().add(0, cushion.getBbHeight(), 0);
                sprinting = true;
            }
        }
        if (!sneaking && !sprinting)
            return original;

        Grid grid = sprinting ? Grid.PIXEL : Grid.BLOCK;
        Vec3 newPos = sneaking && sprinting ? clicked : grid.snapTo(clicked);
        return new Vec3(newPos.x, clicked.y, newPos.z);
    }

    @Nullable
    public static InteractionResult checkCushionIntersection(Level level, AABB spawnAABB) {
        return !intersectsCushion(level, spawnAABB)
                ? null : InteractionResult.FAIL;
    }

    public static void adjustEntityPosition(
            Entity entity,
            Vec3 pos,
            BlockPlaceContext placeContext
    ) {
        if (isSneaking(placeContext) || isSprinting(placeContext))
            entity.snapTo(pos, entity.getYRot(), entity.getXRot());
    }

    @Nullable
    public static InteractionResult stackCushion(
            Player player,
            Entity cushion,
            InteractionHand hand
    ) {
        ItemStack heldStack = player.getItemInHand(hand);
        BlockHitResult hitResult = getStackedCushionHitResult(player, cushion, heldStack);
        if (hitResult == null)
            return null;

        UseOnContext context = new UseOnContext(player, hand, hitResult);
        return heldStack.useOn(context);
    }

    @Nullable
    private static BlockHitResult getStackedCushionHitResult(
            Player player,
            Entity cushion,
            ItemStack heldStack
    ) {
        if (!player.isSecondaryUseActive()
                || !heldStack.is(ItemTags.CUSHIONS)
                || cushion.isVehicle())
            return null;

        Vec3 newLocation = cushion.position();
        return new BlockHitResult(
                newLocation,
                Direction.UP,
                BlockPos.containing(newLocation),
                false
        );
    }

    public static boolean bypassCollisionBlockTags(UseOnContext context) {
        return CommonGameRules.ALLOW_INNER_WALL_CUSHION_PLACEMENT.get(context.getLevel());
    }

    public static boolean allowStackedCushions(Level level, AABB boundingBox, AABB anchorBox) {
        if (!CommonGameRules.CUSHIONS_SUPPORT_EACH_OTHER.get(level))
            return false;

        return intersectsCushion(
                level,
                anchorBox,
                cushion -> !cushion.getBoundingBox().equals(boundingBox)
        );
    }

    private static boolean intersectsCushion(
            Level level,
            AABB box,
            Predicate<Cushion> filter
    ) {
        return level.hasEntities(
                EntityTypeTest.forClass(Cushion.class),
                box,
                filter
        );
    }

    private static boolean intersectsCushion(
            Level level,
            AABB box
    ) {
        return intersectsCushion(level, box, _ -> true);
    }
}
