package com.github.phylogeny.bettercushionplacement.platform.registry;

import com.github.phylogeny.bettercushionplacement.Constants;
import com.github.phylogeny.bettercushionplacement.config.ConfigExtension;
import com.github.phylogeny.bettercushionplacement.config.ConfigType;
import com.github.phylogeny.bettercushionplacement.config.FabricConfigEntry;
import com.github.phylogeny.bettercushionplacement.config.entry.*;
import com.github.phylogeny.bettercushionplacement.platform.Services;
import com.github.phylogeny.bettercushionplacement.platform.services.registry.ConfigBuilder;
import com.github.phylogeny.bettercushionplacement.util.ConfigUtil;
import com.google.common.collect.Lists;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.loader.ConfigurationLoader;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class FabricConfigBuilder implements ConfigBuilder<CommentedConfigurationNode, ConfigType> {
    private final ConfigurationLoader<CommentedConfigurationNode> loader;
    private final List<CommentedConfigurationNode> nodes = Lists.newArrayList();
    private final Path path;
    private final ConfigType type;

    public FabricConfigBuilder(ConfigType type) {
        path = Services.PLATFORM
                .getConfigDir()
                .resolve(ConfigUtil.getConfigName(type, getExtension()));
        loader = HoconConfigurationLoader.builder().path(path).build();
        CommentedConfigurationNode root;
        try {
            root = loader.load();
        } catch (ConfigurateException _) {
            root = loader.createNode();
        }
        nodes.add(root);
        this.type = type;
    }

    @Override
    public ConfigExtension getExtension() {
        return ConfigExtension.HOCON;
    }

    @Override
    public ConfigType getType() {
        return type;
    }

    @Override
    public CommentedConfigurationNode build() {
        save();
        return nodes.getFirst();
    }

    @Override
    public void save() {
        try {
            loader.save(nodes.getFirst());
        } catch (ConfigurateException e) {
            Constants.LOG.error("Config file ({}) failed to save: {}", path, e);
        }
    }

    @Override
    public void push(
            String name,
            String comment,
            String translationKey
    ) {
        nodes.add(getNode(name, comment));
    }

    @Override
    public void pop() {
        nodes.removeLast();
    }

    private CommentedConfigurationNode getNode(
            String name,
            String comment,
            String... additionalCommentLines
    ) {
        StringJoiner comments = new StringJoiner("\n");
        comments.add(comment);
        for (String line : additionalCommentLines)
            comments.add(line);

        return nodes.getLast()
                .node(name)
                .comment(comments.toString());
    }

    private String getDefaultString() {
        return "Default: ";
    }

    @Override
    public BooleanEntry define(
            String name,
            boolean defaultValue,
            boolean useTickBox,
            String comment,
            String translationKey
    ) {
        CommentedConfigurationNode node = getNode(
                name,
                comment,
                getDefaultString() + defaultValue
        );
        FabricConfigEntry<Boolean> entry =
                new FabricConfigEntry<>(
                        nodes.getFirst(),
                        node,
                        defaultValue
                );
        return new BooleanEntry(
                translationKey,
                defaultValue,
                useTickBox,
                entry
        );
    }

    private <T extends Number> FabricConfigEntry<T> getConfigEntry(
            String name,
            T defaultValue,
            T min,
            T max,
            T value,
            T validValue,
            CommentedConfigurationNode node
    ) {
        if (!value.equals(validValue)) {
            String message = "The %s config value of '%s' is out of bounds (%s-%s)"
                    .formatted(
                            name,
                            value,
                            min,
                            max
                    );
            Constants.LOG.warn(message);
        }
        return new FabricConfigEntry<>(
                nodes.getFirst(),
                node,
                defaultValue
        );
    }

    private static final String RANGE_FORMAT = "Range: %s ~ %s";

    @Override
    public IntegerEntry defineInRange(
            String name,
            int defaultValue,
            int min,
            int max,
            @Nullable Integer increment,
            String comment,
            String translationKey
    ) {
        CommentedConfigurationNode node = getNode(
                name,
                comment,
                getDefaultString() + defaultValue,
                RANGE_FORMAT.formatted(min, max)
        );
        Integer value = node.getInt(defaultValue);
        Integer validValue = Mth.clamp(
                node.getInt(defaultValue),
                min,
                max
        );
        FabricConfigEntry<Integer> entry = getConfigEntry(
                name,
                defaultValue,
                min,
                max,
                value,
                validValue,
                node
        );
        return new IntegerEntry(
                translationKey,
                defaultValue,
                min,
                max,
                increment,
                entry
        );
    }

    @Override
    public FloatEntry defineInRange(
            String name,
            float defaultValue,
            float min,
            float max,
            @Nullable Float increment,
            String comment,
            String translationKey
    ) {
        CommentedConfigurationNode node = getNode(
                name,
                comment,
                getDefaultString() + defaultValue,
                RANGE_FORMAT.formatted(min, max)
        );
        Float value = node.getFloat(defaultValue);
        Float validValue = Mth.clamp(
                node.getFloat(defaultValue),
                min,
                max
        );
        FabricConfigEntry<Float> entry = getConfigEntry(
                name,
                defaultValue,
                min,
                max,
                value,
                validValue,
                node
        );
        return new FloatEntry(
                translationKey,
                defaultValue,
                min,
                max,
                increment,
                entry
        );
    }

    @Override
    public DoubleEntry defineInRange(
            String name,
            double defaultValue,
            double min,
            double max,
            @Nullable Double increment,
            String comment,
            String translationKey
    ) {
        CommentedConfigurationNode node = getNode(
                name,
                comment,
                getDefaultString() + defaultValue,
                RANGE_FORMAT.formatted(min, max)
        );
        Double value = node.getDouble(defaultValue);
        Double validValue = Mth.clamp(
                node.getDouble(defaultValue),
                min,
                max
        );
        FabricConfigEntry<Double> entry = getConfigEntry(
                name,
                defaultValue,
                min,
                max,
                value,
                validValue,
                node
        );
        return new DoubleEntry(
                translationKey,
                defaultValue,
                min,
                max,
                increment,
                entry
        );
    }

    @Override
    public <V extends Enum<V>> EnumEntry<V> defineEnum(
            String name,
            V defaultValue,
            String comment,
            String translationKey
    ) {
        Class<V> valueClass = defaultValue.getDeclaringClass();
        String values = Arrays
                .stream(valueClass.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.joining(", "));
        CommentedConfigurationNode node = getNode(
                name,
                comment,
                getDefaultString() + defaultValue.name(),
                "Allowed Values: " + values
        );
        FabricConfigEntry<V> entry =
                new FabricConfigEntry<>(
                        nodes.getFirst(),
                        node,
                        defaultValue
                );
        return new EnumEntry<>(
                translationKey,
                defaultValue,
                entry
        );
    }
}
