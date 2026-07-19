package com.github.phylogeny.bettercushionplacement.network;

import com.github.phylogeny.bettercushionplacement.registry.RegistryObj;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRule;
import org.jetbrains.annotations.Nullable;

public class ImmutableSyncedGameRule<T> implements SyncedGameRule<T> {
    private final T fixedValue;

    public ImmutableSyncedGameRule(T fixedValue) {
        this.fixedValue = fixedValue;
    }

    @Nullable
    @Override
    public RegistryObj<GameRule<T>> registryObj() {
        return null;
    }

    @Nullable
    @Override
    public GameRule<T> rule() {
        return null;
    }

    @Override
    public T get(MinecraftServer server) {
        return fixedValue;
    }

    @Override
    public T get(Level level) {
        return fixedValue;
    }

    @Override
    public void setClientValue(T value) {}
}
