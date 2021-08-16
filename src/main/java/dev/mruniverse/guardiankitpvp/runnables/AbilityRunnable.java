package dev.mruniverse.guardiankitpvp.runnables;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AbilityRunnable extends BukkitRunnable {

    private final GuardianKitPvP plugin;

    private final String ability;

    private final PlayerManager manager;

    private final Player player;

    private final int max;

    private int count = 0;

    public AbilityRunnable(GuardianKitPvP plugin,PlayerManager manager,Player player,String ability,int max) {
        this.max = max;
        this.ability = ability;
        this.plugin = plugin;
        this.manager = manager;
        this.player = player;
    }

    @Override
    public void run() {
        int currentCount = count;
        double remaining = max - currentCount;
        double percentage = remaining / max;
        int secondValue = (int)(20.0D * percentage);
        plugin.getUtils().getUtils().sendActionbar(player,manager.getProgressBar(secondValue,remaining));
        if(count == max) {
            cancel();
            manager.clearCountdowns();
        }
        count++;
    }
}
