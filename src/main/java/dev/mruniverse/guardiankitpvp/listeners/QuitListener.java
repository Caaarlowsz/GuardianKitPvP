package dev.mruniverse.guardiankitpvp.listeners;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerManager;
import dev.mruniverse.guardianlib.core.GuardianLIB;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    private final GuardianKitPvP plugin;

    public QuitListener(GuardianKitPvP plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        PlayerManager manager = plugin.getKitPvP().getPlayers().getUser(player.getUniqueId());
        plugin.getKitPvP().getDataStorage().saveStats(event.getPlayer(),false,manager);

        plugin.getKitPvP().getBoardController().removeScore(event.getPlayer());
        plugin.getKitPvP().getPlayers().removePlayer(event.getPlayer().getUniqueId());
        GuardianLIB.getControl().getNMS().deleteBossBar(event.getPlayer());
        event.setQuitMessage(null);
    }

}
