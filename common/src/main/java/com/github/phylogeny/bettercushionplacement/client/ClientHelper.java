package com.github.phylogeny.bettercushionplacement.client;

import com.github.phylogeny.bettercushionplacement.DyedAABB;
import com.github.phylogeny.bettercushionplacement.EntityHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;

public class ClientHelper {
    public static void renderCushionPlacementPreview(
            PoseStack poseStack,
            LevelRenderState levelRenderState,
            SubmitNodeCollector collector
    ) {
        boolean enabled = true;//TODO make config
        float lineWidthOverride = -1;//TODO make config
        if (!enabled || lineWidthOverride == 0)
            return;

        int opcity = 255;//TODO make config
        int alpha = opcity;
        if (alpha < 1)
            return;

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        Level level = mc.level;
        if (player == null || level == null)
            return;

        HitResult hitResult = mc.hitResult;
        DyedAABB spawnAABB = EntityHelper.getCushionSpawnBox(player, level, hitResult);
        if (spawnAABB == null)
            return;

        poseStack.pushPose();
        Vec3 camPos = levelRenderState.cameraRenderState.pos;
        poseStack.translate(-camPos.x, -camPos.y, -camPos.z);
        DyeColor dyeColorOverride = null;//TODO make config
        DyeColor dyeColor = dyeColorOverride == null
                ? spawnAABB.dyeColor
                : dyeColorOverride;
        int rgb = dyeColor.getTextureDiffuseColor();
        int redOverride = -1;//TODO make config
        int greenOverride = -1;//TODO make config
        int blueOverride = -1;//TODO make config
        int argb = ARGB.color(
                alpha,
                redOverride < 0 ? ARGB.red(rgb) : redOverride,
                greenOverride < 0 ? ARGB.green(rgb) : greenOverride,
                blueOverride < 0 ? ARGB.blue(rgb) : blueOverride
        );
        float lineWidth = lineWidthOverride;
        if (lineWidth < 0)
            lineWidth = Minecraft.getInstance()
                    .gameRenderer
                    .gameRenderState()
                    .windowRenderState
                    .appropriateLineWidth;

        collector.submitShapeOutline(
                poseStack,
                Shapes.create(spawnAABB.contract(0, -0.002, 0)),
                alpha == 255 ? RenderTypes.lines() : RenderTypes.linesTranslucent(),
                argb,
                lineWidth,
                false
        );
        poseStack.popPose();
    }
}
