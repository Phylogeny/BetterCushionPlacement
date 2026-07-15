package com.github.phylogeny.bettercushionplacement;

import com.github.phylogeny.bettercushionplacement.registry.CommonGameRules;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.Cushion;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class EntityHelper {
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

    private static boolean isSneaking(BlockPlaceContext placeContext) {
        return Optional.ofNullable(placeContext.getPlayer())
                .map(Player::isSecondaryUseActive)
                .orElse(false);
    }

    private static boolean isSprinting(BlockPlaceContext placeContext) {
        Player player = placeContext.getPlayer();
        if (player instanceof ServerPlayer serverPlayer)
            return serverPlayer.getLastClientInput().sprint();
        else if (player instanceof LocalPlayer localPlayer)
            return localPlayer.input.keyPresses.sprint();

        return false;
    }

    public static Vec3 adjustPlacementPosition(
            Vec3 original,
            BlockPlaceContext placeContext
    ) {
        Vec3 clicked = placeContext.getClickLocation();
        boolean sneaking = isSneaking(placeContext);
        boolean sprinting = isSprinting(placeContext);
        if (sneaking) {
            Vec3 finalClicked = clicked;
            Cushion cushion = placeContext.getLevel().getEntitiesOfClass(
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
            return snapElevationTOPixelGrid(original, placeContext);

        Grid grid = sprinting ? Grid.PIXEL : Grid.BLOCK;
        Vec3 newPos = sneaking && sprinting ? clicked : grid.snapTo(clicked);
        return new Vec3(newPos.x, clicked.y, newPos.z);
    }

    private static Vec3 snapElevationTOPixelGrid(
            Vec3 pos,
            BlockPlaceContext placeContext
    ) {
        if (placeContext.getLevel() instanceof ServerLevel serverLevel) {
            boolean shouldSnap = serverLevel.getGameRules().get(CommonGameRules.SNAP_CUSHION_ELEVATION_TO_PIXEL_GRID.get());
            if (shouldSnap) {
                Vec3 snapped = Grid.PIXEL.snapTo(pos);
                return new Vec3(pos.x, snapped.y, pos.z);
            }
        }
        return pos;
    }

    @Nullable
    public static InteractionResult checkCushionIntersection(Level level, AABB spawnAABB) {
        return level.getEntitiesOfClass(Cushion.class, spawnAABB).isEmpty()
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
        ItemStack stack = player.getItemInHand(hand);
        if (!player.isSecondaryUseActive()
                || !stack.is(ItemTags.CUSHIONS)
                || cushion.isVehicle())
            return null;

        if (cushion.level().isClientSide())
            return InteractionResult.SUCCESS;

        Vec3 newLocation = cushion.position();
        BlockHitResult hitResult = new BlockHitResult(
                newLocation,
                Direction.UP,
                BlockPos.containing(newLocation),
                false
        );
        UseOnContext context = new UseOnContext(player, hand, hitResult);
        stack.useOn(context);
        return InteractionResult.CONSUME;
    }
}