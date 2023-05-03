package net.freedinner.extraordinary_extra_totems.networking;

import net.freedinner.extraordinary_extra_totems.ExtraordinaryExtraTotems;
import net.freedinner.extraordinary_extra_totems.util.PlayerState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.UUID;

public class ServerState extends PersistentState {
    public HashMap<UUID, PlayerState> playerStatesHashMap = new HashMap<>();

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtCompound playerStatesNbt = new NbtCompound();

        playerStatesHashMap.forEach((UUID, playerState) -> {
            NbtCompound playerStateNbt = new NbtCompound();

            playerStateNbt.putBoolean("experiencedDeathStare", playerState.experiencedDeathStare);

            playerStatesNbt.put(String.valueOf(UUID), playerStateNbt);
        });
        nbt.put("players", playerStatesNbt);

        return nbt;
    }

    public static ServerState createFromNbt(NbtCompound nbt) {
        ServerState serverState = new ServerState();

        NbtCompound playerStatesNbt = nbt.getCompound("players");
        playerStatesNbt.getKeys().forEach(key -> {
            PlayerState playerState = new PlayerState();

            playerState.experiencedDeathStare = playerStatesNbt.getCompound(key).getBoolean("experiencedDeathStare");

            UUID uuid = UUID.fromString(key);
            serverState.playerStatesHashMap.put(uuid, playerState);
        });

        return serverState;
    }

    public static ServerState getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();

        return persistentStateManager.getOrCreate(
                ServerState::createFromNbt,
                ServerState::new,
                ExtraordinaryExtraTotems.MOD_ID);
    }

    public static PlayerState getPlayerState(LivingEntity player) {
        ServerState serverState = getServerState(player.world.getServer());
        PlayerState playerState = serverState.playerStatesHashMap.computeIfAbsent(player.getUuid(), uuid -> new PlayerState());
        serverState.markDirty();
        return playerState;
    }
}
