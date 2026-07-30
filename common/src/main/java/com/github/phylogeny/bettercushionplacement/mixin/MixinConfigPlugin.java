package com.github.phylogeny.bettercushionplacement.mixin;

import com.github.phylogeny.bettercushionplacement.Constants;
import com.github.phylogeny.bettercushionplacement.config.ConfigExtension;
import com.github.phylogeny.bettercushionplacement.config.EarlyConfig;
import com.github.phylogeny.bettercushionplacement.config.HoconConfig;
import com.github.phylogeny.bettercushionplacement.config.TomlConfig;
import com.github.phylogeny.bettercushionplacement.util.LangUtil;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class MixinConfigPlugin implements IMixinConfigPlugin {
    public static final String CUSHIONS_SUPPORT_EACH_OTHER = "Instead of only being supported by blocks, cushions can " +
            "also be supported by other cushions, allowing direct stacking.";
    public static final String INNER_WALL_CUSHION_PLACEMENT = "Ignores #cushion_uses_collision_shape block tags, " +
            "thus allowing sub-pixel cushion placement on the inner walls of cauldrons, composters, and hoppers " +
            "without a data pack.";

    public enum MixinMode {
        GAME_RULE,
        ENABLED,
        DISABLED;

        @Nullable
        public static MixinMode get(String name) {
            return Arrays.stream(MixinMode.values())
                    .filter(role -> role.name().equalsIgnoreCase(name))
                    .findFirst()
                    .orElse(null);
        }

        @Override
        public String toString() {
            return LangUtil.snakeCaseToTitleCase(name());
        }
    }

    public enum Mixin {
        CUSHIONS_SUPPORT_EACH_OTHER(
                "MixinCushionsSupportEachOther",
                "cushions_support_each_other",
                MixinMode.GAME_RULE,
                MixinConfigPlugin.CUSHIONS_SUPPORT_EACH_OTHER
        ),
        INNER_WALL_CUSHION_PLACEMENT(
                "MixinInnerWallCushionPlacement",
                "allow_inner_wall_cushion_placement",
                MixinMode.GAME_RULE,
                MixinConfigPlugin.INNER_WALL_CUSHION_PLACEMENT
        );

        private final AtomicReference<MixinMode> mode;
        public final String className;
        public final String registryName;
        public final MixinMode defaultMode;
        public final String comment;

        Mixin(
                String className,
                String registryName,
                MixinMode defaultMode,
                String comment
        ) {
            this.className = className;
            this.registryName = registryName;
            this.defaultMode = defaultMode;
            this.comment = comment;
            mode = new AtomicReference<>(defaultMode);
        }

        public MixinMode getMode() {
            return mode.get();
        }

        void setMode(String modeString) {
            Optional.ofNullable(MixinMode.get(modeString))
                    .ifPresent(mode::set);
        }

        @Nullable
        static Mixin fromClassName(String className) {
            for (Mixin mixin : Mixin.values()) {
                if (mixin.className.equals(className)) {
                    return mixin;
                }
            }
            return null;
        }
    }

    @Override
    public void onLoad(String mixinPackage) {
        for (ConfigExtension extension : ConfigExtension.values()) {
            Path configPath = Paths.get(
                    "config",
                    Constants.MOD_ID,
                    extension.getFile(Constants.MOD_INITIALS + "-common"));
            if (Files.exists(configPath)) {
                String folder = "game_rules.";
                EarlyConfig config = switch (extension) {
                    case TOML -> new TomlConfig(configPath);
                    case HOCON -> new HoconConfig(configPath);
                };
                for (Mixin mixin : Mixin.values()) {
                    String modeString = config.getValue(
                            folder + mixin.registryName,
                            mixin.defaultMode.name()
                    );
                    mixin.setMode(modeString);
                }
                break;
            }
        }
    }

    @Override
    public boolean shouldApplyMixin(
            String targetClassName,
            String mixinClassName
    ) {
        String shortName = mixinClassName.substring(mixinClassName.lastIndexOf('.') + 1);
        @Nullable Mixin mixin = Mixin.fromClassName(shortName);
        return mixin == null || mixin.getMode() != MixinMode.DISABLED;
    }

    @Override
    public String getRefMapperConfig() {
        return Constants.MOD_ID + ".refmap.json";
    }

    @Override
    public void acceptTargets(
            Set<String> myTargets,
            Set<String> otherTargets
    ) {}

    @Override
    public List<String> getMixins() {
        return List.of();
    }

    @Override
    public void preApply(
            String targetClassName,
            ClassNode targetClass,
            String mixinClassName,
            IMixinInfo mixinInfo
    ) {}

    @Override
    public void postApply(
            String targetClassName,
            ClassNode targetClass,
            String mixinClassName,
            IMixinInfo mixinInfo
    ) {}
}
