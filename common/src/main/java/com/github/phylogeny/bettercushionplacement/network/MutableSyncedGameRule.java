package com.github.phylogeny.bettercushionplacement.network;

import com.github.phylogeny.bettercushionplacement.registry.RegistryObj;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRule;
import org.jetbrains.annotations.Nullable;

public class MutableSyncedGameRule<T> implements SyncedGameRule<T> {
    private final RegistryObj<GameRule<T>> registryObj;
    private T clientValue;

    public MutableSyncedGameRule(RegistryObj<GameRule<T>> registryObj) {
        this.registryObj = registryObj;
    }

    @Nullable
    @Override
    public RegistryObj<GameRule<T>> registryObj() {
        return registryObj;
    }

    @Nullable
    @Override
    public GameRule<T> rule() {
        return registryObj.get();
    }

    @Override
    public T get(MinecraftServer server) {
        return server.getGameRules().get(registryObj.get());
    }

    @Override
    public T get(Level level) {
        return level instanceof ServerLevel serverLevel
                ? get(serverLevel.getServer())
                : clientValue;
    }

    @Override
    public void setClientValue(T value) {
        clientValue = value;
    }
}