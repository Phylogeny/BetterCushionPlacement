package com.github.phylogeny.bettercushionplacement.client;

import com.github.phylogeny.bettercushionplacement.DyedAABB;
import com.github.phylogeny.bettercushionplacement.EntityHelper;
import com.github.phylogeny.bettercushionplacement.config.Configs;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.world.entity.player.Player;
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
        float lineWidthOverride = Configs.CLIENT.placementPreview.lineWidthOverride.get();
        if (!Configs.CLIENT.placementPreview.enabled.get() || lineWidthOverride == 0)
            return;

        int alpha = Configs.CLIENT.placementPreview.opacity.get();
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
        int color = Configs.CLIENT.placementPreview.color.get().getColor(spawnAABB, alpha);
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
                color,
                lineWidth,
                false
        );
        poseStack.popPose();
    }
}
