package com.github.phylogeny.bettercushionplacement.config;

import com.github.phylogeny.bettercushionplacement.client.DyeColorOverride;
import com.github.phylogeny.bettercushionplacement.client.DyeColorVariant;
import com.github.phylogeny.bettercushionplacement.client.PreviewColor;
import com.github.phylogeny.bettercushionplacement.config.entry.BooleanEntry;
import com.github.phylogeny.bettercushionplacement.config.entry.EnumEntry;
import com.github.phylogeny.bettercushionplacement.config.entry.FloatEntry;
import com.github.phylogeny.bettercushionplacement.config.entry.IntegerEntry;
import com.github.phylogeny.bettercushionplacement.platform.services.registry.ConfigBuilder;

public class ClientConfig<S, T> extends ConfigFile<S, T> {
    public PlacementPreview<S, T> placementPreview = addNode(
            new PlacementPreview<>(this)
    );

    public static class PlacementPreview<S, T> extends ConfigFolder<S, T> {
        /**
         * {@value ENABLED}
         */
        public final BooleanEntry enabled = define(
                "enabled",
                true,
                ENABLED
        );
        private static final String ENABLED = "Whether to render a placement preview box for cushions";

        /**
         * {@value LINE_WIDTH_OVERRIDE}
         */
        public final FloatEntry lineWidthOverride = defineInRange(
                "line_width_override",
                -1.0F,
                -1.0F,
                10F,
                0.1F,
                LINE_WIDTH_OVERRIDE
        );
        private static final String LINE_WIDTH_OVERRIDE = """
                If positive, the lines of the box will render with this.
                - If negative, they will render with the default size.
                - If zero, rendering will be skipped.""";

        /**
         * {@value OPACITY}
         */
        public final IntegerEntry opacity = defineInRange(
                "opacity",
                255,
                0,
                255,
                1,
                OPACITY
        );
        private static final String OPACITY = """
                The box will render with this alpha value.
                - If zero, rendering will be skipped.""";

        /**
         * {@value COLOR}
         */
        public final EnumEntry<PreviewColor> color = defineEnum(
                "color",
                PreviewColor.CUSHION_COLOR,
                COLOR
        );
        private static final String COLOR = """
                The color the box will render as. The color will be:
                - CUSHION_COLOR: The dye color the cushion will be when placed.
                - DYE_COLOR_OVERRIDE: The dye color specified by the 'Dye Color Override' confg.
                  - In both cases, the variant of the dye color will be specified by the 'Dye Color Variant' config.
                - COLOR_OVERRIDE: The color specified by the 'Color Override' confg.""";

        /**
         * {@value DYE_COLOR_OVERRIDE}
         */
        public final EnumEntry<DyeColorOverride> dyeColorOverride = defineEnum(
                "dye_color_override",
                DyeColorOverride.BLUE_DYE,
                DYE_COLOR_OVERRIDE
        );
        private static final String DYE_COLOR_OVERRIDE = """
                If the 'Color' config is set to DYE_COLOR_OVERRIDE, the box will render as this dye color.
                - Otherwise, this will be ignored.""";

        /**
         * {@value DYE_COLOR_VARIANT}
         */
        public final EnumEntry<DyeColorVariant> dyeColorVariant = defineEnum(
                "dye_color_variant",
                DyeColorVariant.TEXTURE_DIFFUSE,
                DYE_COLOR_VARIANT
        );
        private static final String DYE_COLOR_VARIANT = """
                If the 'Color' config is set to CUSHION_COLOR or DYE_COLOR_OVERRIDE, the box will render as a dye color with this variant.
                - Otherwise, this will be ignored.""";

        /**
         * {@value COLOR_OVERRIDE}
         */
        public final IntegerEntry colorOverride = defineInRange(
                "color_override",
                255,
                0,
                16777215,
                COLOR_OVERRIDE
        );
        private static final String COLOR_OVERRIDE = """
                If the 'Color' config is set to COLOR_OVERRIDE, the box will render as this color.
                - Otherwise, this will be ignored.""";

        public PlacementPreview(ConfigFolder<S, T> configFolder) {
            super(
                    configFolder,
                    "placement_preview",
                    "Configures how to render a placement preview box for cushions"
            );
            closeFolder();
        }
    }

    public ClientConfig(ConfigBuilder<S, T> builder) {
        super(builder);
    }
}
