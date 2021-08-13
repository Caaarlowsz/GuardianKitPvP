package dev.mruniverse.guardiankitpvp.storage;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerData;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerManager;
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
    public void addPlayer(final Player player) {
        if(kitPvP.getKitPvP() == null) reportNull();
        final PlayerManagerBuilder pm = new PlayerManagerBuilder(kitPvP,player);
        pm.finish();
        players.put(player.getUniqueId(),pm);
        kitPvP.getLogs().debug("Stats of " + player.getName() + " loaded.");
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
        return players.get(uuid);
    }

}
