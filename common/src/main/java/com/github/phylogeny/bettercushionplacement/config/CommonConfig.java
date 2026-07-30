package com.github.phylogeny.bettercushionplacement.config;

import com.github.phylogeny.bettercushionplacement.config.entry.EnumEntry;
import com.github.phylogeny.bettercushionplacement.mixin.MixinConfigPlugin;
import com.github.phylogeny.bettercushionplacement.platform.services.registry.ConfigBuilder;

public class CommonConfig<S, T> extends ConfigFile<S, T> {
    public GameRule<S, T> gameRule = addNode(
            new GameRule<>(this)
    );

    public static class GameRule<S, T> extends ConfigFolder<S, T> {
        /**
         * {@value com.github.phylogeny.bettercushionplacement.mixin.MixinConfigPlugin#CUSHIONS_SUPPORT_EACH_OTHER}
         *
         */
        public final EnumEntry<MixinConfigPlugin.MixinMode> cushionsSupportEachOther = defineGameRule(
                MixinConfigPlugin.Mixin.CUSHIONS_SUPPORT_EACH_OTHER
        );

        /**
         * {@value com.github.phylogeny.bettercushionplacement.mixin.MixinConfigPlugin#INNER_WALL_CUSHION_PLACEMENT}
         */
        public final EnumEntry<MixinConfigPlugin.MixinMode> innerWallCushionPlacement = defineGameRule(
                MixinConfigPlugin.Mixin.INNER_WALL_CUSHION_PLACEMENT
        );

        private EnumEntry<MixinConfigPlugin.MixinMode> defineGameRule(
                MixinConfigPlugin.Mixin mixin
        ) {
            return defineEnum(
                    mixin.registryName,
                    mixin.defaultMode,
                    true,
                    mixin.comment
            );
        }

        public GameRule(ConfigFolder<S, T> configFolder) {
            super(
                    configFolder,
                    "game_rules",
                    """
                            Configures each game feature as one of the following. Requires game restart.
                            - GAME_RULE: Enabled or disabled by game rule
                            - ENABLED: Always enabled and game rule hidden
                            - DISABLED: Always disabled, game rule hidden, and mixin not applied"""
            );
            closeFolder();
        }
    }

    public CommonConfig(ConfigBuilder<S, T> builder) {
        super(builder);
    }
}
