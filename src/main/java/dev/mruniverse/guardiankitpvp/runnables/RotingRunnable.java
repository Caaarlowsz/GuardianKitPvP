package dev.mruniverse.guardiankitpvp.runnables;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class RotingRunnable extends BukkitRunnable {
    private final GuardianKitPvP plugin;

    private int minutes = 14;

    private int seconds = 59;

    private final Random random = new Random();

    public RotingRunnable(GuardianKitPvP plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if(minutes != 0 && seconds != 0) {
            seconds--;
            if(seconds == 0) {
                checkTimer();
                minutes--;
                seconds = 59;
            }
        } else {
            checkTimer();
        }
    }

    public String getTimer() {
        String builder;
        if(minutes < 10) {
            builder = "0" + minutes + ":";
        } else {
            builder = "" + minutes + ":";
        }

        if(seconds < 10) {
            builder = builder + "0" + seconds;
        } else {
            builder = builder + seconds;
        }

        return builder;
    }

    public void checkTimer() {
        if(minutes == 0 && seconds == 0) {
            rotate();
        }
    }

    public void rotate() {
        List<String> locations = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.SETTINGS).getStringList("settings.map-locations");
        int ZONE_ID = random.nextInt(locations.size());
        String ZONE_STRING = locations.get(ZONE_ID);
        Location ZONE_LOCATION = plugin.getUtils().getUtils().getLocationFromString(ZONE_STRING);
        if(ZONE_LOCATION != null) {
            plugin.getKitPvP().getListenerController().setMapLocation(ZONE_LOCATION);
        }
        minutes = 14;
        seconds = 59;
        String rotateMessage = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.MESSAGES).getString("messages.rotate","&b&lROTATION! &7The map has been rotated to another spawn!");
        for(Player player : Bukkit.getOnlinePlayers()) {
            plugin.getUtils().getUtils().sendMessage(player,rotateMessage);
        }
    }
}
