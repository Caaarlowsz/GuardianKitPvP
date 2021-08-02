package dev.mruniverse.guardiankitpvp.storage;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerData;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerDataBuilder implements PlayerData {
    private final HashMap<UUID,PlayerManager> players = new HashMap<>();

    private GuardianKitPvP kitPvP;

    @Override
    public void clear() {
        this.players.clear();
    }

    @Override
    public void setPlugin(GuardianKitPvP kitPvP) {
        this.kitPvP = kitPvP;
    }

    @Override
    public void addPlayer(Player player) {
        if(!existPlayer(player.getUniqueId())) {
            if(kitPvP.getKitPvP() == null) reportNull();
            PlayerManager manager = kitPvP.getKitPvP().getPlayerManager();
            try {
                manager = manager.getClass().newInstance();
                manager.setPlugin(kitPvP)
                        .setPlayer(player)
                        .finish();
                kitPvP.getLogs().debug(manager.getPlayer().getName() + "!");
                players.put(player.getUniqueId(),manager);
            } catch (Throwable throwable){
                throwable.printStackTrace();
            }
        }
    }

    public void reportNull() {
        kitPvP.getLogs().info("The KitPvP Instance is null. :(");
    }

    @Override
    public void removePlayer(UUID uuid) {
        players.remove(uuid);
    }

    @Override
    public boolean existPlayer(UUID uuid) {
        return players.containsKey(uuid);
    }

    @Override
    public HashMap<UUID, PlayerManager> getPlayers() {
        return players;
    }

    @Override
    public PlayerManager getUser(final UUID uuid) {
        if(players.get(uuid) == null) {
            Player player = Bukkit.getPlayer(uuid);
            if(player != null) {
                addPlayer(player);
            }
        }
        return players.get(uuid);
    }

    @Override
    public PlayerManager getUser(final Player player) {
        if(players.get(player.getUniqueId()) == null) {
            addPlayer(player);
        }
        return players.get(player.getUniqueId());
    }
}
