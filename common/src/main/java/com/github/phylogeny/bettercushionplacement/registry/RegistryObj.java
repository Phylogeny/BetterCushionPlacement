package com.github.phylogeny.bettercushionplacement.registry;

import com.github.phylogeny.bettercushionplacement.Constants;
import net.minecraft.resources.Identifier;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public record RegistryObj<T>(String name, Supplier<T> factory) implements Supplier<T> {
    public static <K> RegistryObj<K> register(String name, Supplier<K> factory, BiFunction<String, Supplier<K>, Supplier<K>> registerFunction) {
        return new RegistryObj<>(name, registerFunction.apply(name, factory));
    }

    @Override
    public T get() {
        return factory.get();
    }

    public Identifier location() {
        return Constants.MOD_IDENTIFIER.withPath(name);
    }
}
