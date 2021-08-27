package dev.mruniverse.guardiankitpvp.storage;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import dev.mruniverse.guardiankitpvp.enums.SaveMode;
import dev.mruniverse.guardiankitpvp.interfaces.storage.FileStorage;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerData;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerManager;
import dev.mruniverse.guardiankitpvp.interfaces.storage.SQL;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class SQLBuilder implements SQL {
    private final GuardianKitPvP plugin;

    private final boolean isUUID;



    public SQLBuilder(GuardianKitPvP main) {
        plugin = main;
        isUUID = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.SETTINGS).getBoolean("settings.game.user-uuid-on-data",false);
    }

    @Override
    public void addKit(String paramPlayer,String kit) {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    List<String> list = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.DATA).getStringList("Players." + paramPlayer + ".Kits");
                    list.add(kit);
                    plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.DATA).set("Players." + paramPlayer + ".Kits", list);
                    plugin.getKitPvP().getFileStorage().save(SaveMode.DATA);
                } catch (Throwable throwable) {
                    plugin.getLogs().error("Can't add kit '" + kit + "' to player '" + paramPlayer + "'");
                    plugin.getLogs().error(throwable);
                }
            }
        };
        runnable.runTaskAsynchronously(plugin);
    }
    @Override
    public void removeKit(String paramPlayer,String kit) {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    List<String> list = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.DATA).getStringList("Players." + paramPlayer + ".Kits");
                    list.remove(kit);
                    plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.DATA).set("Players." + paramPlayer + ".Kits", list);
                    plugin.getKitPvP().getFileStorage().save(SaveMode.DATA);
                } catch (Throwable throwable) {
                    plugin.getLogs().error("Can't remove kit '" + kit + "' from player '" + paramPlayer + "'");
                    plugin.getLogs().error(throwable);
                }
            }
        };
        runnable.runTaskAsynchronously(plugin);
    }

    @Override
    public void loadStats(final Player player) {
        final String playerName =  isUUID ? player.getUniqueId().toString() : player.getName();
        (new BukkitRunnable() {
            public void run() {
                PlayerManager manager = plugin.getKitPvP().getPlayers().getUser(player.getUniqueId());
                try {
                    FileConfiguration fileConfiguration = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.DATA);
                    if (fileConfiguration.contains("Players." + playerName)) {
                        StringBuilder kits = new StringBuilder();
                        for (String str : fileConfiguration.getStringList("Players." + playerName + ".Kits"))
                            if(kits.toString().equalsIgnoreCase("")) {
                                kits = new StringBuilder(str);
                            } else {
                                kits.append(",").append(str);
                            }
                        manager.setKits(kits.toString());
                        manager.setStatsFromString(fileConfiguration.getString("Players." + playerName + ".Statistics"));
                    } else {
                        manager.resetPlayer();
                    }
                } catch (Throwable ignored) {
                    manager.resetPlayer();
                }
                manager.updateRank();
            }
        }).runTaskLaterAsynchronously(plugin, 2L);
    }

    @Override
    public void saveStats(Player paramPlayer) {
        String str = "Players." + (isUUID ? paramPlayer.getUniqueId().toString() : paramPlayer.getName());
        PlayerManager manager = plugin.getKitPvP().getPlayers().getUser(paramPlayer.getUniqueId());
        plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.DATA).set(str + ".Name", paramPlayer.getName());
        plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.DATA).set(str + ".Statistics", manager.getStatsString());
        plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.DATA).set(str + ".Kits", manager.getKits());
        plugin.getKitPvP().getFileStorage().save(SaveMode.DATA);
    }

    @Override
    public void saveStats(Player paramPlayer,PlayerManager manager) {
        String str = "Players." + (isUUID ? paramPlayer.getUniqueId().toString() : paramPlayer.getName());
        plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.DATA).set(str + ".Name", paramPlayer.getName());
        plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.DATA).set(str + ".Statistics", manager.getStatsString());
        plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.DATA).set(str + ".Kits", manager.getKits());
        plugin.getKitPvP().getFileStorage().save(SaveMode.DATA);
    }

    @Override
    public HashMap<String, String> getUsers() {
        try {
            HashMap<String, String> hashMap = new HashMap<>();
            FileStorage storage = plugin.getKitPvP().getFileStorage();
            PlayerData data = plugin.getKitPvP().getPlayers();
            for (String string : storage.getContent(GuardianFiles.DATA,"Players",false)) {
                String username = storage.getControl(GuardianFiles.DATA).getString("Players." + string + ".Name","NoBodyIsTheNick999222");
                Player player = Bukkit.getPlayer(username);
                if(player != null) {
                    hashMap.put(username, data.existPlayer(player.getUniqueId()) ? data.getUser(player.getUniqueId()).getStatsString() : storage.getControl(GuardianFiles.DATA).getString("Players." + string + ".Statistics"));
                } else {
                    hashMap.put(username, storage.getControl(GuardianFiles.DATA).getString("Players." + string + ".Statistics"));
                }
            }
            return hashMap;
        }catch(Throwable throwable) {
            if(plugin.getKitPvP() != null) {
                if(plugin.getKitPvP().getPlayers() != null) {
                    plugin.getLogs().error("&3DATA-LIB | &fCan't load statics");
                    plugin.getLogs().error(throwable);
                }
            }
        }
        return null;
    }
}
