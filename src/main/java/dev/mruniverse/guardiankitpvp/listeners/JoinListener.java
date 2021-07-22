package dev.mruniverse.guardiankitpvp.listeners;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianBoard;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    private final GuardianKitPvP plugin;
    public JoinListener(GuardianKitPvP plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void teleport(PlayerJoinEvent event) {
        if(plugin.getKitPvP().getListenerController().getMapLocation() != null) {
            try {
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> event.getPlayer().teleport(plugin.getKitPvP().getListenerController().getMapLocation()), 4L);
            } catch (Throwable throwable) {
                plugin.getLogs().error("Can't teleport " + event.getPlayer().getName() + " to the lobby!");
                plugin.getLogs().error(throwable);
            }
            return;
        }
        plugin.getLogs().error("The lobby is not set!");
    }
    @EventHandler
    public void scoreboard(PlayerJoinEvent event) {
        try {
            if(plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.SCOREBOARD).getBoolean("scoreboards.normal.toggle")) {
                plugin.getKitPvP().getBoardController().setScoreboard(GuardianBoard.NORMAL,event.getPlayer());
            }
        } catch (Throwable throwable) {
            plugin.getLogs().error("Can't generate lobby scoreboard for " + event.getPlayer().getName() +"!");
            plugin.getLogs().error(throwable);
        }
    }

    @EventHandler
    public void extras(PlayerJoinEvent event) {
        plugin.getKitPvP().getPlayers().addPlayer(event.getPlayer());
        Player player = event.getPlayer();
        event.setJoinMessage(null);
        player.setHealth(20.0D);
        player.setFoodLevel(20);
        player.setLevel(0);
        player.setExp(0.0F);
    }

}