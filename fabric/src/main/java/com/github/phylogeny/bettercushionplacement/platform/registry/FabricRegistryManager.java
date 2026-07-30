package com.github.phylogeny.bettercushionplacement.platform.registry;

import com.github.phylogeny.bettercushionplacement.Constants;
import com.github.phylogeny.bettercushionplacement.FabricNetworkRegistry;
import com.github.phylogeny.bettercushionplacement.registry.RegistryObj;
import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.List;
import java.util.function.Supplier;

public class FabricRegistryManager {
    private static final List<RegistryObj<?>> REGISTRY_ENTRIES = Lists.newArrayList();

    public static <T extends R, R> RegistryObj<T> register(String name, Supplier<T> value, Registry<R> registries) {
        return register(name, () -> Registry.register(registries, Constants.MOD_IDENTIFIER.withPath(name), value.get()));
    }

    public static <T extends R, R> RegistryObj<T> register(
            String name, Supplier<T> value, ResourceKey<R> resourceKey, Registry<R> registries
    ) {
        return register(name, () -> Registry.register(registries, resourceKey, value.get()));
    }

    private static <T> RegistryObj<T> register(String name, Supplier<T> value) {
        RegistryObj<T> entry = new RegistryObj<>(name, Suppliers.memoize(value::get));
        REGISTRY_ENTRIES.add(entry);
        return entry;
    }

    public static void register() {
        REGISTRY_ENTRIES.forEach(RegistryObj::get);
        FabricNetworkRegistry.registerCommon();
    }
}
