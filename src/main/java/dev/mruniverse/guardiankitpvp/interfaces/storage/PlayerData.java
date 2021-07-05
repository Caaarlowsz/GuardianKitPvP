package dev.mruniverse.guardiankitpvp.interfaces.storage;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public interface PlayerData {
    void setPlugin(GuardianKitPvP kitPvP);

    void addPlayer(Player player);

    void removePlayer(UUID uuid);

    void clear();

    boolean existPlayer(UUID uuid);

    HashMap<UUID, PlayerManager> getPlayers();

    PlayerManager getUser(UUID uuid);
}
