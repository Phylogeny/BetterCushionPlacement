package com.github.phylogeny.bettercushionplacement.platform.registry;

import com.github.phylogeny.bettercushionplacement.config.ConfigExtension;
import com.github.phylogeny.bettercushionplacement.config.NeoForgeConfigEntry;
import com.github.phylogeny.bettercushionplacement.config.entry.*;
import com.github.phylogeny.bettercushionplacement.platform.services.registry.ConfigBuilder;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.Nullable;

public class NeoForgeConfigBuilder implements ConfigBuilder<ModConfigSpec, ModConfig.Type> {
    private final ModConfigSpec.Builder builder;
    private final ModConfig.Type type;
    @Nullable
    private ModConfigSpec configSpec;

    public NeoForgeConfigBuilder(ModConfig.Type type) {
        builder = new ModConfigSpec.Builder();
        this.type = type;
    }

    @Override
    public ConfigExtension getExtension() {
        return ConfigExtension.TOML;
    }

    @Override
    public ModConfig.Type getType() {
        return type;
    }

    @Override
    public ModConfigSpec build() {
        configSpec = builder.build();
        return configSpec;
    }

    @Override
    public void save() {
        if (configSpec != null)
            configSpec.save();
    }

    @Override
    public void push(
            String name,
            String comment,
            String translationKey
    ) {
        builder.comment(comment)
                .translation(translationKey)
                .push(name);
    }

    @Override
    public void pop() {
        builder.pop();
    }

    @Override
    public BooleanEntry define(
            String name,
            boolean defaultValue,
            boolean requiresGameRestart,
            boolean useTickBox,
            String comment,
            String translationKey
    ) {
        ModConfigSpec.BooleanValue entry = builder
                .comment(comment + "\nDefault: " + defaultValue)
                .translation(translationKey)
                .define(name, defaultValue);
        return new BooleanEntry(
                translationKey,
                defaultValue,
                requiresGameRestart,
                useTickBox,
                new NeoForgeConfigEntry<>(
                        entry,
                        entry::set
                )
        );
    }

    @Override
    public IntegerEntry defineInRange(
            String name,
            int defaultValue,
            boolean requiresGameRestart,
            int min,
            int max,
            @Nullable Integer increment,
            String comment,
            String translationKey
    ) {
        ModConfigSpec.IntValue entry = builder
                .comment(comment)
                .translation(translationKey)
                .defineInRange(
                        name,
                        defaultValue,
                        min,
                        max
                );
        return new IntegerEntry(
                translationKey,
                defaultValue,
                requiresGameRestart,
                min,
                max,
                increment,
                new NeoForgeConfigEntry<>(
                        entry,
                        entry::set
                )
        );
    }

    @Override
    public FloatEntry defineInRange(
            String name,
            float defaultValue,
            boolean requiresGameRestart,
            float min,
            float max,
            @Nullable Float increment,
            String comment,
            String translationKey
    ) {
        ModConfigSpec.DoubleValue entry = builder
                .comment(comment)
                .translation(translationKey)
                .defineInRange(
                        name,
                        defaultValue,
                        min,
                        max
                );
        return new FloatEntry(
                translationKey,
                defaultValue,
                requiresGameRestart,
                min,
                max,
                increment,
                new NeoForgeConfigEntry<>(
                        () -> entry.get().floatValue(),
                        value -> entry.set(value.doubleValue())
                )
        );
    }

    @Override
    public DoubleEntry defineInRange(
            String name,
            double defaultValue,
            boolean requiresGameRestart,
            double min,
            double max,
            @Nullable Double increment,
            String comment,
            String translationKey
    ) {
        ModConfigSpec.DoubleValue entry = builder
                .comment(comment)
                .translation(translationKey)
                .defineInRange(
                        name,
                        defaultValue,
                        min,
                        max
                );
        return new DoubleEntry(
                translationKey,
                defaultValue,
                requiresGameRestart,
                min,
                max,
                increment,
                new NeoForgeConfigEntry<>(
                        entry,
                        entry::set
                )
        );
    }

    @Override
    public <V extends Enum<V>> EnumEntry<V> defineEnum(
            String name,
            V defaultValue,
            boolean requiresGameRestart,
            String comment,
            String translationKey
    ) {
        ModConfigSpec.EnumValue<V> entry = builder
                .comment(comment + "\nDefault: " + defaultValue.name())
                .translation(translationKey)
                .defineEnum(name, defaultValue);
        return new EnumEntry<>(
                translationKey,
                defaultValue,
                requiresGameRestart,
                new NeoForgeConfigEntry<>(
                        entry,
                        entry::set
                )
        );
    }
}
