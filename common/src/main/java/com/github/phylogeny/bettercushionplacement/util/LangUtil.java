package com.github.phylogeny.bettercushionplacement.util;

import com.github.phylogeny.bettercushionplacement.Constants;
import com.github.phylogeny.bettercushionplacement.config.ConfigNode;
import com.github.phylogeny.bettercushionplacement.registry.RegistryObj;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class LangUtil {
    private static final Map<String, String> CONFIG_ENTRIES = new LinkedHashMap<>();
    public static final Splitter DOT_SPLITTER = Splitter.on(".");

    public enum ConfigKey {
        TITLE("title", Constants.MOD_DISPLAY_NAME + " Configuration"),
        FOLDER("folder", "Folder"),
        FOLDER_OPEN("folder.open", "Open"),
        COLOR_SWATH_SELECTED("color.swath.selected", "■"),
        COLOR_SWATH_UNSELECTED("color.swath.unselected", "◼");

        private final String key;
        private final String value;

        ConfigKey(String keySuffix, String value) {
            key = ConfigUtil.CONFIG_PREFIX + keySuffix;
            this.value = value;
        }

        public String get() {
            return key;
        }

        public Component getComponent() {
            return Component.translatable(key);
        }
    }

    public static String snakeCaseToTitleCase(String name) {
        return Arrays.stream(name.toLowerCase().split("_"))
                .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
                .collect(Collectors.joining(" "));
    }

    public static String snakeCaseToTitleCase(RegistryObj<?> holder) {
        return snakeCaseToTitleCase(holder.name());
    }

    public static String addConfigFolder(
            String path,
            String tooltip,
            Object type
    ) {
        return addConfigFolder(
                path,
                tooltip,
                type,
                null
        );
    }

    public static String addConfigFolder(
            String path,
            String tooltip,
            Object type,
            @Nullable String button
    ) {
        return addConfigElement(
                path,
                tooltip,
                type,
                button,
                "Open folder"
        );
    }

    public static String addConfigEntry(
            String path,
            String tooltip,
            Object type
    ) {
        return addConfigElement(
                path,
                tooltip,
                type,
                null,
                null
        );
    }

    public static String addConfigFile(String name) {
        String key = ConfigUtil.CONFIG_PREFIX + "section.%s.%s.%s.toml".formatted(
                Constants.MOD_ID.replace("_", "."),
                Constants.MOD_INITIALS,
                name
        );
        String value = LangUtil.snakeCaseToTitleCase(name) + " settings";
        CONFIG_ENTRIES.put(key, value);
        CONFIG_ENTRIES.put(key + ConfigNode.Type.FILE.getDescriptionKeySuffix(), value);
        return key;
    }

    private static List<String> split(String path) {
        return Lists.newArrayList(DOT_SPLITTER.split(path));
    }

    private static String addConfigElement(
            String path,
            String tooltip,
            Object type,
            @Nullable String button,
            @Nullable String defaultButton
    ) {
        String key = ConfigUtil.CONFIG_PREFIX + toLowerCase(type) + "." + path;
        CONFIG_ENTRIES.put(key, LangUtil.snakeCaseToTitleCase(split(path).getLast()));
        CONFIG_ENTRIES.put(key + ConfigNode.Type.ENTRY.getDescriptionKeySuffix(), tooltip);
        if (button != null || defaultButton != null)
            CONFIG_ENTRIES.put(
                    key + ".button",
                    button != null ? button : defaultButton
            );

        return key;
    }

    public static void addConfigLangEntries(
            BiConsumer<String, String> configConsumer
    ) {
        for (ConfigKey configKey : ConfigKey.values())
            CONFIG_ENTRIES.put(configKey.key, configKey.value);

        CONFIG_ENTRIES.forEach(configConsumer);
    }

    public static String toLowerCase(Object object) {
        return object.toString().toLowerCase(Locale.ENGLISH);
    }
}
