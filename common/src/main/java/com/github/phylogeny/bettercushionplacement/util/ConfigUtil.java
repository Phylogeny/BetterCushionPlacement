package com.github.phylogeny.bettercushionplacement.util;

import com.github.phylogeny.bettercushionplacement.Constants;
import com.github.phylogeny.bettercushionplacement.config.ConfigExtension;

import java.util.Locale;

public class ConfigUtil {
    public static final String CONFIG_PREFIX =  Constants.MOD_ID + ".configuration.";

    public static String getConfigName(Object type, ConfigExtension extension) {
        return String.format(
                Locale.ROOT,
                "%s/%s-%s.%s",
                Constants.MOD_ID,
                Constants.MOD_INITIALS,
                toLowerCase(type),
                extension
        );
    }

    public static String toLowerCase(Object object) {
        return object.toString().toLowerCase(Locale.ROOT);
    }
}
