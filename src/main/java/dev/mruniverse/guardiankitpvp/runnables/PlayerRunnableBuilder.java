package dev.mruniverse.guardiankitpvp.runnables;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.interfaces.runnables.PlayerRunnable;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerData;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerManager;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class PlayerRunnableBuilder extends BukkitRunnable implements PlayerRunnable {
    private final GuardianKitPvP plugin;

    public PlayerRunnableBuilder(GuardianKitPvP plugin) {
        this.plugin = plugin;
    }

    @Override
    public void update() {
        /*
         * THIS IS OPTIONAL, ONLY IF YOU WANT UPDATE SOMETHING OF THIS RUNNABLE, IN THIS CASE, I DON'T NEED TO DO THIS.
         */
    }

    @Override
    public void run() {
        PlayerData playerData = plugin.getKitPvP().getPlayers();
        for (UUID uuid : playerData.getPlayers().keySet()) {
            PlayerManager playerManager = playerData.getUser(uuid);
            if(playerManager != null) {
                plugin.getKitPvP().getBoardController().setScoreboard(playerManager.getBoard(), playerManager.getPlayer());
            }
        }
    }
}
