package dev.mruniverse.guardiankitpvp.holograms;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import dev.mruniverse.guardiankitpvp.enums.GuardianHolograms;
import dev.mruniverse.guardiankitpvp.enums.HoloPath;
import dev.mruniverse.guardiankitpvp.enums.TopHologram;
import dev.mruniverse.guardiankitpvp.interfaces.holograms.HoloManager;
import dev.mruniverse.guardiankitpvp.runnables.HologramRunnable;
import dev.mruniverse.guardianlib.core.holograms.GlobalHologram;
import dev.mruniverse.guardianlib.core.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HoloManagerBuilder implements HoloManager {

    private final GuardianKitPvP plugin;

    private final HologramRunnable hologramRunnable;

    private final List<GlobalHologram> spawnLocation = new ArrayList<>();

    private final HashMap<TopHologram, List<GlobalHologram>> holograms;

    private final HashMap<TopHologram, List<String>> top;

    private final HashMap<TopHologram, String> format;

    private final HashMap<TopHologram, List<String>> bottom;

    private final String none;

    private final int size;

    public HoloManagerBuilder(GuardianKitPvP plugin) {
        this.plugin = plugin;
        hologramRunnable = new HologramRunnable(plugin);
        holograms = new HashMap<>();
        top = new HashMap<>();
        format = new HashMap<>();
        bottom = new HashMap<>();
        size = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.HOLOGRAMS).getInt(GuardianHolograms.getSizePath());
        none = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.HOLOGRAMS).getString("holograms.tops.none","NO_PLAYER");
        loadSides();
        startTimer();
        loadSpawn();
    }

    private void loadSides() {
        FileConfiguration holograms = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.HOLOGRAMS);
        for(TopHologram hologram : TopHologram.values()) {
            top.put(hologram,holograms.getStringList(hologram.getGuardian().getPath(HoloPath.TOP)));
            format.put(hologram,holograms.getString(hologram.getGuardian().getPath(HoloPath.FORMAT)));
            bottom.put(hologram,holograms.getStringList(hologram.getGuardian().getPath(HoloPath.BOT)));
        }
    }

    private void loadSpawn() {
        List<String> lines = new ArrayList<>();
        String locationName = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.SETTINGS).getString("settings.destinyName","Map");
        for(String line : plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.HOLOGRAMS).getStringList(GuardianHolograms.SPAWN_LOCATION.getPath(HoloPath.LINES))) {
            line = ChatColor.translateAlternateColorCodes('&',line.replace("%location-name%",locationName));
            lines.add(line);
        }
        Utils utils = plugin.getUtils().getUtils();
        for(String locations : plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.HOLOGRAMS).getStringList(GuardianHolograms.SPAWN_LOCATION.getPath(HoloPath.LOCATIONS))) {
            Location location = utils.getLocationFromString(locations);
            if(location != null) {
                GlobalHologram globalHologram = new GlobalHologram(location,lines);
                globalHologram.spawn();
                spawnLocation.add(globalHologram);
            }
        }
    }

    private void startTimer() {
        if(hologramRunnable != null) {
            try {
                hologramRunnable.cancel();
            } catch (Throwable ignored) { }
            int minutes = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.SETTINGS).getInt("settings.update-hologram-delay");
            hologramRunnable.runTaskTimer(plugin, 0L, minutes * 1200L);
        }
    }

    @Override
    public void loadFrom(TopHologram top,List<Map.Entry<String,Integer>> list){
        for(GlobalHologram hologram : holograms.get(top)) {
            loadTop(top,hologram,list);
        }
    }

    @Override
    public void loadTops() {
        for(TopHologram hologram : TopHologram.values()) {
            loadTop(hologram);
        }
    }

    private void loadTop(TopHologram top,GlobalHologram hologram,List<Map.Entry<String,Integer>> list) {
        if(holograms.get(top) != null) {
            for(GlobalHologram holo : holograms.get(top)) {
                try {
                    holo.remove();
                }catch (Throwable ignored) {}
            }
        }

        List<String> lines = new ArrayList<>();
        for(String line : this.top.get(top)) {
            line = ChatColor.translateAlternateColorCodes('&',line)
                    .replace("%currentMode%",top.getName());
            lines.add(line);
        }
        int position = 1;
        for(Map.Entry<String,Integer> entry : getFixedEntry(list).entrySet()) {
            if(position <= size) {
                String format = this.format.get(top);
                format = format.replace(top.getReplace(), entry.getValue() + "").replace("%position%", "" + position).replace("%player%", entry.getKey());
                lines.add(ChatColor.translateAlternateColorCodes('&', format));
                position++;
            }
        }

        for(String line : this.bottom.get(top)) {
            line = ChatColor.translateAlternateColorCodes('&',line)
                    .replace("%currentMode%",top.getName());
            lines.add(line);
        }
        hologram.setLines(lines);
        hologram.spawn();

    }


    private HashMap<String,Integer> getFixedEntry(List<Map.Entry<String,Integer>> entryList) {
        HashMap<String,Integer> hash = new HashMap<>();
        for(int i = 1; i<=size; i++) {
            hash.put(entryList.get(i).getKey(),entryList.get(i).getValue());
        }
        return hash;
    }

    private void loadTop(TopHologram top) {
        List<GlobalHologram> list = new ArrayList<>();
        if(holograms.get(top) != null) {
            for(GlobalHologram holo : holograms.get(top)) {
                try {
                    holo.remove();
                }catch (Throwable ignored) {}
            }
        }
        Utils utils = plugin.getUtils().getUtils();

        List<String> lines = new ArrayList<>();
        for(String line : this.top.get(top)) {
            line = ChatColor.translateAlternateColorCodes('&',line)
                    .replace("%currentMode%",top.getName());
            lines.add(line);
        }

        for(int i = 1; i<=12; i++) {
            String format = this.format.get(top);
            format = format.replace(top.getReplace(),"0").replace("%position%","" + i).replace("%player%",none);
            lines.add(format);
        }

        for(String line : this.bottom.get(top)) {
            line = ChatColor.translateAlternateColorCodes('&',line)
                    .replace("%currentMode%",top.getName());
            lines.add(line);
        }

        for(String locationString : plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.HOLOGRAMS).getStringList(top.getGuardian().getPath(HoloPath.LOCATIONS))) {
            Location location = utils.getLocationFromString(locationString);
            GlobalHologram global = new GlobalHologram(location,lines);
            global.spawn();
            list.add(global);
        }
        holograms.put(top,list);
    }

    @Override
    public void updateSpawn() {
        if(spawnLocation.size() != 0) {
            for(GlobalHologram holo : spawnLocation) {
                try {
                    holo.remove();
                }catch (Throwable ignored) {}
            }
        }
        loadSpawn();
    }

    @Override
    public void updateStatHologram(Player player) {
        plugin.getKitPvP().getPlayers().getUser(player.getUniqueId()).updateHolograms();
    }

    @Override
    public void loadStats(Player player) {
        for(String locations : plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.HOLOGRAMS).getStringList(GuardianHolograms.STATICS.getPath(HoloPath.LOCATIONS))) {
            Location location = plugin.getUtils().getUtils().getLocationFromString(locations);
            if(location != null) {
                plugin.getKitPvP().getPlayers().getUser(player.getUniqueId()).addHologram(location,true);
            }
        }
    }
}
