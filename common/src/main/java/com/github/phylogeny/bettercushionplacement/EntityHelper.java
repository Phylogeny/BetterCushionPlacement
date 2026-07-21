package com.github.phylogeny.bettercushionplacement;

import com.github.phylogeny.bettercushionplacement.registry.CommonGameRules;
import com.github.phylogeny.bettercushionplacement.registry.CommonTags;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
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
import net.minecraft.world.level.entity.EntityTypeTest;
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

    private static boolean isSneaking(UseOnContext placeContext) {
        return Optional.ofNullable(placeContext.getPlayer())
                .map(Player::isSecondaryUseActive)
                .orElse(false);
    }

    private static boolean isSprinting(UseOnContext placeContext) {
        Player player = placeContext.getPlayer();
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

        Vec3 newLocation = cushion.position();
        BlockHitResult hitResult = new BlockHitResult(
                newLocation,
                Direction.UP,
                BlockPos.containing(newLocation),
                false
        );
        UseOnContext context = new UseOnContext(player, hand, hitResult);
        return stack.useOn(context);
    }

    public static boolean bypassCollisionBlockTags(UseOnContext context) {
        return CommonGameRules.ALLOW_INNER_WALL_CUSHION_PLACEMENT.get(context.getLevel());
    }

    public static boolean allowStackedCushions(Level level, AABB boundingBox, AABB anchorBox) {
        if (!CommonGameRules.CUSHIONS_SUPPORT_EACH_OTHER.get(level))
            return false;

        return level.hasEntities(
                EntityTypeTest.forClass(Cushion.class),
                anchorBox,
                cushion -> !cushion.getBoundingBox().equals(boundingBox)
        );
    }
}