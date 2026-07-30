package com.github.phylogeny.bettercushionplacement;

import com.github.phylogeny.bettercushionplacement.client.DyeColorOverride;
import com.github.phylogeny.bettercushionplacement.client.DyeColorVariant;
import com.github.phylogeny.bettercushionplacement.config.*;
import com.github.phylogeny.bettercushionplacement.config.entry.*;
import com.github.phylogeny.bettercushionplacement.mixin.MixinConfigPlugin;
import com.github.phylogeny.bettercushionplacement.util.LangUtil;
import com.google.common.collect.Lists;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class YaclConfigScreen {
    @Nullable
    private static DyeColorVariant selectedVariantValue;

    public static Screen create(Screen parent) {
        ArrayList<ConfigFile<?, ?>> files = Lists.newArrayList(
                Configs.CLIENT,
                Configs.SERVER,
                Configs.COMMON
        );
        return createCategoryScreen(
                new ArrayList<>(files),
                files,
                parent
        );
    }

    private static Screen createCategoryScreen(
            List<ConfigFolderBase> folders,
            List<ConfigFile<?, ?>> files,
            Screen parent
    ) {
        YetAnotherConfigLib.Builder builder = YetAnotherConfigLib.createBuilder()
                .title(LangUtil.ConfigKey.TITLE.getComponent())
                .save(() -> {
                    for (ConfigFile<?, ?> file : files) {
                        if (!file.getContents().isEmpty())
                            file.save();
                    }
                });
        folders.forEach(folder -> {
            if (!folder.getContents().isEmpty())
                builder.category(createCategory(folder, files));
        });
        return builder.build().generateScreen(parent);
    }

    private static ConfigCategory createCategory(
            ConfigFolderBase folder,
            List<ConfigFile<?, ?>> files
    ) {
        ConfigCategory.Builder category = ConfigCategory.createBuilder()
                .name(Component.translatable(folder.translationKey()))
                .tooltip(Component.translatable(folder.descriptionTranslationKey()));
        for (ConfigNode node : folder.getContents()) {
            if (node instanceof ConfigFolderBase subFolder) {
                ButtonOption.Builder option = ButtonOption.createBuilder()
                        .name(Component.empty()
                                .append(Component.translatable(subFolder.translationKey()))
                                .append(Component.literal(" "))
                                .append(LangUtil.ConfigKey.FOLDER.getComponent())
                        )
                        .description(OptionDescription.of(
                                Component.translatable(subFolder.descriptionTranslationKey()))
                        )
                        .action((yaclScreen, _) ->
                                Minecraft.getInstance().setScreenAndShow(
                                        createCategoryScreen(
                                                Collections.singletonList(subFolder),
                                                files,
                                                yaclScreen
                                        )
                                )
                        )
                        .text(LangUtil.ConfigKey.FOLDER_OPEN.getComponent());
                category.option(option.build());
                continue;
            }
            if (!(node instanceof ConfigEntry<?> entry)) {
                String message = "Encountered a config entry of an unimplemented type: " + node;
                Constants.LOG.warn(message);
                continue;
            }
            @Nullable Option.Builder<?> option;
            switch (entry) {
                case BooleanEntry booleanEntry -> option = Option.<Boolean>createBuilder()
                        .binding(
                                booleanEntry.defaultValue(),
                                booleanEntry,
                                booleanEntry
                        ).controller(opt -> booleanEntry.useTickBox()
                                ? TickBoxControllerBuilder.create(opt)
                                : BooleanControllerBuilder
                                .create(opt)
                                .coloured(true)
                                .trueFalseFormatter()
                        );
                case IntegerEntry integerEntry -> {
                    if (integerEntry.max() == Math.pow(256, 3) - 1) {
                        option = Option.<Color>createBuilder().binding(
                                new Color(integerEntry.defaultValue()),
                                () -> new Color(integerEntry.get()),
                                newVal -> integerEntry.set(newVal.getRGB() & 0x00FFFFFF)
                        ).controller(ColorControllerBuilder::create);
                    } else {
                        Option.Builder<Integer> integerOption = Option.<Integer>createBuilder()
                                .binding(
                                        integerEntry.defaultValue(),
                                        integerEntry,
                                        integerEntry
                                );
                        integerEntry.getIncrement().ifPresentOrElse(
                                increment -> integerOption.controller(opt ->
                                        IntegerSliderControllerBuilder.create(opt)
                                                .range(integerEntry.min(), integerEntry.max())
                                                .step(increment)
                                ),
                                () -> integerOption.controller(opt ->
                                        IntegerFieldControllerBuilder.create(opt)
                                                .range(integerEntry.min(), integerEntry.max())
                                )
                        );
                        option = integerOption;
                    }
                }
                case FloatEntry floatEntry -> {
                    Option.Builder<Float> integerOption = Option.<Float>createBuilder()
                            .binding(
                                    floatEntry.defaultValue(),
                                    floatEntry,
                                    floatEntry
                            );
                    floatEntry.getIncrement().ifPresentOrElse(
                            increment -> integerOption.controller(opt ->
                                    FloatSliderControllerBuilder.create(opt)
                                            .range(floatEntry.min(), floatEntry.max())
                                            .step(increment)
                            ),
                            () -> integerOption.controller(opt ->
                                    FloatFieldControllerBuilder.create(opt)
                                            .range(floatEntry.min(), floatEntry.max())
                            )
                    );
                    option = integerOption;
                }
                case DoubleEntry doubleEntry -> {
                    Option.Builder<Double> integerOption = Option.<Double>createBuilder()
                            .binding(
                                    doubleEntry.defaultValue(),
                                    doubleEntry,
                                    doubleEntry
                            );
                    doubleEntry.getIncrement().ifPresentOrElse(
                            increment -> integerOption.controller(opt ->
                                    DoubleSliderControllerBuilder.create(opt)
                                            .range(doubleEntry.min(), doubleEntry.max())
                                            .step(increment)
                            ),
                            () -> integerOption.controller(opt ->
                                    DoubleFieldControllerBuilder.create(opt)
                                            .range(doubleEntry.min(), doubleEntry.max())
                            )
                    );
                    option = integerOption;
                }
                default -> option = getEnumOption(entry);
            }
            if (option != null) {
                option.name(
                        Component.translatable(entry.translationKey())
                );
                option.description(
                        OptionDescription.of(
                                Component.translatable(entry.descriptionTranslationKey())
                        )
                );
                if (entry.requiresGameRestart())
                    option.flag(OptionFlag.GAME_RESTART);

                category.option(option.build());
            }
        }
        return category.build();
    }

    @SuppressWarnings({"unchecked, rawtypes"})
    private static Option.Builder<?> getEnumOption(ConfigEntry<?> entry) {
        if (!(entry instanceof EnumEntry enumEntry))
            return null;

        Class enumClass = enumEntry.get().getDeclaringClass();
        Option.Builder option = Option.<Enum<?>>createBuilder().binding(
                enumEntry.defaultValue(),
                enumEntry,
                enumEntry
        ).controller(opt -> {
            EnumControllerBuilder builder = EnumControllerBuilder.create((Option) opt)
                    .enumClass(enumClass);
            boolean isOverride = enumClass.isAssignableFrom(DyeColorOverride.class);
            if (isOverride || enumClass.isAssignableFrom(DyeColorVariant.class)) {
                builder.formatValue(enumValue -> {
                    MutableComponent text = Component.empty();
                    MutableComponent selectedColorSwath =
                            getColorSwath(LangUtil.ConfigKey.COLOR_SWATH_SELECTED);
                    if (isOverride) {
                        DyeColorOverride dyeColorValue = (DyeColorOverride) enumValue;
                        DyeColorVariant variantValue = selectedVariantValue == null
                                ? Configs.CLIENT.placementPreview.dyeColorVariant.get()
                                : selectedVariantValue;
                        text.append(selectedColorSwath
                                .withColor(variantValue.getColor(dyeColorValue.dyeColor))
                        );
                    } else {
                        int cycleMills = 1000;
                        DyeColor[] dyeColors = DyeColor.values();
                        int totalMilliseconds = dyeColors.length * cycleMills;
                        int index = (int) (System.currentTimeMillis() % totalMilliseconds) / cycleMills;
                        DyeColor dispalyDyeColor = dyeColors[index];
                        selectedVariantValue = (DyeColorVariant) enumValue;
                        for (DyeColorVariant variant : DyeColorVariant.values()) {
                            MutableComponent colorSwath = variant == selectedVariantValue
                                    ? selectedColorSwath
                                    : getColorSwath(LangUtil.ConfigKey.COLOR_SWATH_UNSELECTED);
                            text.append(colorSwath.withColor(variant.getColor(dispalyDyeColor)));
                        }
                    }
                    return text.append(Component.literal(enumValue.toString()));
                });
            }
            return builder;
        });
        return option;
    }

    private static MutableComponent getColorSwath(LangUtil.ConfigKey configKey) {
        return Component.empty()
                .append(configKey.getComponent())
                .append(Component.literal(" "));
    }
}
