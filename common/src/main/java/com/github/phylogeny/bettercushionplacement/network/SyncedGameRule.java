package com.github.phylogeny.bettercushionplacement.network;

import com.github.phylogeny.bettercushionplacement.registry.RegistryObj;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRule;

public final class SyncedGameRule<T> {
    private final RegistryObj<GameRule<T>> registryObj;
    private T clientValue;

    public SyncedGameRule(RegistryObj<GameRule<T>> registryObj) {
        this.registryObj = registryObj;
    }

    public RegistryObj<GameRule<T>> registryObj() {
        return registryObj;
    }

    public GameRule<T> rule() {
        return registryObj.get();
    }

    public T get(MinecraftServer server) {
        return server.getGameRules().get(registryObj.get());
    }

    public T get(Level level) {
        return level instanceof ServerLevel serverLevel
                ? get(serverLevel.getServer())
                : clientValue;
    }

    public void setClientValue(T value) {
        clientValue = value;
    }
}