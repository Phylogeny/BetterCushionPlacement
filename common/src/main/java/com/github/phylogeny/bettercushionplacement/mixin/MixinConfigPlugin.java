package com.github.phylogeny.bettercushionplacement.mixin;

import com.github.phylogeny.bettercushionplacement.Constants;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MixinConfigPlugin implements IMixinConfigPlugin {
    private final Properties config = new Properties();

    public enum MixinMode {
        GAME_RULE("Enabled or disabled by game rule"),
        ENABLED("Always enabled and game rule hidden"),
        DISABLED("Always disabled, game rule hidden, and mixin not applied");

        public final String description;
        public final String key;

        MixinMode(String description) {
            this.description = description;
            key = name().toLowerCase();
        }

        @Nullable
        public static MixinMode get(String name) {
            return Arrays.stream(MixinMode.values())
                    .filter(role -> role.name().equalsIgnoreCase(name))
                    .findFirst()
                    .orElse(null);
        }
    }

    public enum Mixin {
        CUSHIONS_SUPPORT_EACH_OTHER(
                "MixinCushionsSupportEachOther",
                "cushions_support_each_other",
                MixinMode.GAME_RULE,
                "Instead of only being supported by blocks, cushions can also be supported by other cushions, allowing direct stacking."
        ),
        INNER_WALL_CUSHION_PLACEMENT(
                "MixinInnerWallCushionPlacement",
                "allow_inner_wall_cushion_placement",
                MixinMode.GAME_RULE,
                "Ignores #cushion_uses_collision_shape block tags, thus allowing sub-pixel cushion placement on the inner walls of cauldrons, composters, and hoppers without a data pack."
        );

        private final AtomicReference<MixinMode> mode;
        public final String className;
        public final String registryName;
        public final String comment;

        Mixin(String className, String registryName, MixinMode defaultMode, String comment) {
            this.className = className;
            this.registryName = registryName;
            this.comment = comment;
            mode = new AtomicReference<>(defaultMode);
        }

        public MixinMode getMode() {
            return mode.get();
        }

        void setMode(MixinMode mode) {
            this.mode.set(mode);
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
        Path configPath = Paths.get("config", Constants.MOD_ID + "-gamerules.properties");
        String errorMessage = "Failed to {} configuration file";
        if (!Files.exists(configPath)) {
            try {
                Files.createDirectories(configPath.getParent());
                for (Mixin mixin : Mixin.values())
                    config.setProperty(mixin.registryName, mixin.getMode().key);

                try (BufferedWriter writer = Files.newBufferedWriter(configPath)) {
                    int maxLength =  writeHeader(writer);
                    for (Mixin mixin : Mixin.values()) {
                        writer.newLine();
                        for (String line : splitText(mixin.comment, maxLength))
                            writeComment(writer, line);

                        writer.write("%s = %s\n".formatted(mixin.registryName, mixin.getMode().key));
                    }
                }
            } catch (IOException e) {
                Constants.LOG.error(errorMessage, "create", e);
            }
        } else {
            try (var reader = Files.newBufferedReader(configPath)) {
                config.load(reader);
            } catch (IOException e) {
                Constants.LOG.error(errorMessage, "read", e);
            }
        }
    }

    private static List<String> splitText(String text, int maxLength) {
        List<String> lines = new ArrayList<>();
        Pattern pattern = Pattern.compile(".{1," + maxLength + "}(?:\\s|$)+");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find())
            lines.add(matcher.group().trim());

        return lines;
    }

    private static int writeHeader(BufferedWriter writer) throws IOException {
        List<String> lines = new ArrayList<>(List.of(
                Constants.MOD_DISPLAY_NAME + " Game Rule Configuration File",
                "",
                "Set each game feature as one of the following. Requires game restart."
        ));
        for (MixinMode mode : MixinMode.values())
            lines.add("    %s - %s".formatted(mode.key, mode.description));

        int maxLength = 0;
        for (String line : lines) {
            int len = line.length();
            if (len > maxLength)
                maxLength = len;
        }
        maxLength += 20;
        String separator = "=".repeat(maxLength);
        writeComment(writer, separator);
        for (String line : lines)
            writeComment(writer, line);

        writeComment(writer, separator);
        return maxLength;
    }

    private static void writeComment(BufferedWriter writer, String line) throws IOException {
        writer.write("# " + line + "\n");
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        String shortName = mixinClassName.substring(mixinClassName.lastIndexOf('.') + 1);
        @Nullable Mixin mixin = Mixin.fromClassName(shortName);
        if (mixin == null)
            return true;

        String value = config.getProperty(mixin.registryName, MixinMode.GAME_RULE.key);
        MixinMode mode = MixinMode.get(value);
        mixin.setMode(mode);
        return mode != MixinMode.DISABLED;
    }

    @Override
    public String getRefMapperConfig() {
        return Constants.MOD_ID + ".refmap.json";
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() {
        return List.of();
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}
