package com.github.phylogeny.bettercushionplacement.network;

import com.github.phylogeny.bettercushionplacement.registry.RegistryObj;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRule;
import org.jetbrains.annotations.Nullable;

public interface SyncedGameRule<T> {
    @Nullable
    RegistryObj<GameRule<T>> registryObj();

    @Nullable
    GameRule<T> rule();

    T get(MinecraftServer server);

    T get(Level level);

    void setClientValue(T value);
}