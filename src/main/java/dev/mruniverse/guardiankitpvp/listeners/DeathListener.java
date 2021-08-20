package dev.mruniverse.guardiankitpvp.listeners;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DeathListener implements Listener {
    private final GuardianKitPvP plugin;

    public DeathListener(GuardianKitPvP plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void inLobbyDeath(PlayerDeathEvent event) {
        final Player player = event.getEntity();
        if(plugin.getKitPvP().getPlayers().getUser(player.getUniqueId()).getGame() == null) {
            event.getDrops().clear();
            event.setDeathMessage(null);
            event.setDroppedExp(0);
            player.spigot().respawn();
            player.setGameMode(GameMode.ADVENTURE);
            player.setHealth(20);
            player.setFoodLevel(20);
            Location MAP_LOCATION = plugin.getKitPvP().getListenerController().getMapLocation();
            if(MAP_LOCATION != null) {
                player.teleport(MAP_LOCATION);
                plugin.getKitPvP().getListenerController().getNormalInventory().giveInventory(player,true);
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onDeathRespawn(PlayerRespawnEvent event) {
        if(plugin.getKitPvP().getPlayers().getUser(event.getPlayer().getUniqueId()).getGame() == null) {
            Location MAP_LOCATION = plugin.getKitPvP().getListenerController().getMapLocation();
            if (MAP_LOCATION != null) {
                event.setRespawnLocation(MAP_LOCATION);
            }
        }
    }
}
