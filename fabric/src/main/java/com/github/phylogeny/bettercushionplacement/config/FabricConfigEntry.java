package com.github.phylogeny.bettercushionplacement.config;

import com.github.phylogeny.bettercushionplacement.Constants;
import com.github.phylogeny.bettercushionplacement.config.entry.LoaderConfigEntry;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.NodePath;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.StringJoiner;

public class FabricConfigEntry<T> implements LoaderConfigEntry<T> {
    private final CommentedConfigurationNode root;
    private final NodePath path;
    private final T defaultValue;
    private T cachedValue;

    public FabricConfigEntry(
            CommentedConfigurationNode root,
            CommentedConfigurationNode node,
            T defaultValue
    ) {
        this.root = root;
        this.path = node.path();
        this.defaultValue = defaultValue;
        cacheValue(node, defaultValue);
    }

    @Override
    public T get() {
        return cachedValue;
    }

    @Override
    public void set(T value) {
        CommentedConfigurationNode node = root.node(path);
        try {
            node.set(value);
        } catch (SerializationException e) {
            StringJoiner joiner = new StringJoiner(".");
            for (Object obj : path)
                joiner.add(obj.toString());

            String message = "Could not set '%s' config to '%s'".formatted(joiner.toString(), cachedValue);
            Constants.LOG.error(message, e);
        }
        cacheValue(node, value);
    }

    @SuppressWarnings({"unchecked, rawtypes"})
    private void cacheValue(CommentedConfigurationNode node, T value) {
        if (value instanceof Enum enumValue) {
            try {
                Class<Enum> enumClass = (Class<Enum>) enumValue.getClass();
                cachedValue = (T) Enum.valueOf(enumClass, node.getString(enumValue.name()));
            } catch (ClassCastException | IllegalArgumentException | NullPointerException e) {
                String message = "No matching enum constant found for: " + enumValue.name();
                Constants.LOG.warn(message);
            }
            return;
        }
        cachedValue = switch (value) {
            case Boolean booleanValue -> (T) Boolean.valueOf(node.getBoolean(booleanValue));
            case Integer integerValue -> (T) Integer.valueOf(node.getInt(integerValue));
            case Float floatValue -> (T) Float.valueOf(node.getFloat(floatValue));
            case Double doubleValue -> (T) Double.valueOf(node.getDouble(doubleValue));
            default -> null;
        };
        if (cachedValue == null) {
            cachedValue = defaultValue;
            String message = "Failed to update %s to %s".formatted(cachedValue, value);
            Constants.LOG.warn(message);
        }
    }
}
