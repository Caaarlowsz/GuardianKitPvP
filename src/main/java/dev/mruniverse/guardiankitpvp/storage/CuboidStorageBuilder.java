package dev.mruniverse.guardiankitpvp.storage;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import dev.mruniverse.guardiankitpvp.interfaces.storage.CuboidStorage;
import dev.mruniverse.guardiankitpvp.interfaces.utils.Cuboid;
import dev.mruniverse.guardiankitpvp.utils.CuboidBuilder;
import dev.mruniverse.guardianlib.core.utils.Utils;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class CuboidStorageBuilder implements CuboidStorage {

    private final GuardianKitPvP plugin;

    private final HashMap<String,Cuboid> spawns = new HashMap<>();

    private final HashMap<String,Cuboid> koths = new HashMap<>();

    private final HashMap<String,Cuboid> zones = new HashMap<>();

    private final HashMap<String,Cuboid> teleports = new HashMap<>();

    private final Utils utils;

    private Cuboid currentSpawn;

    private Cuboid currentTeleport;

    private Cuboid lastKoTh;

    public CuboidStorageBuilder(GuardianKitPvP plugin) {
        this.plugin = plugin;
        utils = plugin.getUtils().getUtils();
    }

    @Override
    public void init() {
        FileConfiguration file = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.GAMES);
        for(String zone : file.getStringList("zone-list")) {
            loadZone(zone);
        }
        for(String spawn : file.getStringList("spawn-list")) {
            loadSpawn(spawn);
        }
        for(String koth : file.getStringList("koths-list")) {
            loadKoTH(koth);
        }
    }

    /**
     * @return Cuboid List
     */
    @Override
    public HashMap<String,Cuboid> getSpawns() {
        return spawns;
    }

    /**
     * @return Cuboid List
     */
    @Override
    public HashMap<String,Cuboid> getKoTHs() {
        return koths;
    }

    /**
     * @return Cuboid List
     */
    @Override
    public HashMap<String,Cuboid> getZones() {
        return zones;
    }

    /**
     * @return Cuboid or null
     */
    @Override
    public Cuboid getZone(String zoneID) {
        return zones.get(zoneID);
    }

    /**
     * @return Cuboid or null
     */
    @Override
    public Cuboid getKoTH(String koThID) {
        return koths.get(koThID);
    }

    /**
     * @return Cuboid or null
     */
    @Override
    public Cuboid getSpawn(String spawnID) {
        return spawns.get(spawnID);
    }

    @Override
    public Cuboid getCurrentSpawn() {
        return currentSpawn;
    }

    @Override
    public Cuboid getCurrentKoTh() {
        return lastKoTh;
    }

    @Override
    public Cuboid getTeleport() {
        return currentTeleport;
    }

    @Override
    public void setCurrentSpawn(String spawnID) {
        currentSpawn = spawns.get(spawnID);
        currentTeleport = teleports.get(spawnID);
        if(currentTeleport == null) {
            for(String keys : teleports.keySet()) {
                currentTeleport = teleports.get(keys);
                return;
            }
        }
    }

    @Override
    public void setCurrentKoTh(String kothID) {
        lastKoTh = koths.get(kothID);
    }

    /**
     * @param spawnID is the id of the spawn
     */
    @Override
    public void loadSpawn(String spawnID) {
        FileConfiguration config = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.GAMES);
        if(config.contains("teleport.cuboid-list." + spawnID + ".name") && config.contains("teleport.cuboid-list." + spawnID + ".pos1") && config.contains("teleport.cuboid-list." + spawnID + ".pos2")) {
            Location pos1,pos2;

            pos1 = utils.getLocationFromString(config.getString("teleport.cuboid-list." + spawnID + ".pos1","notSet"));
            pos2 = utils.getLocationFromString(config.getString("teleport.cuboid-list." + spawnID + ".pos2","notSet"));

            if(pos1 != null && pos2 != null) {
                teleports.put(spawnID,new CuboidBuilder(pos1,pos2));
            }
            plugin.getLogs().error("Can't find pos1 or pos2 of cuboid-teleport: " + spawnID);
        } else {
            plugin.getLogs().error("The spawn with id: " + spawnID + " don't have teleport zone, please add one to prevent issues.");
        }
        if(config.contains("lobby.cuboid-list." + spawnID + ".name") && config.contains("lobby.cuboid-list." + spawnID + ".pos1") && config.contains("lobby.cuboid-list." + spawnID + ".pos2")) {
            Location pos1,pos2;

            pos1 = utils.getLocationFromString(config.getString("lobby.cuboid-list." + spawnID + ".pos1","notSet"));
            pos2 = utils.getLocationFromString(config.getString("lobby.cuboid-list." + spawnID + ".pos2","notSet"));

            if(pos1 != null && pos2 != null) {
                spawns.put(spawnID,new CuboidBuilder(pos1,pos2));
                return;
            }
            plugin.getLogs().error("Can't find pos1 or pos2 of cuboid: " + spawnID);
            return;
        }
        plugin.getLogs().error("The cuboid-list doesn't contain id: " + spawnID + ", so this cuboid will not be added to spawn-list.");
    }

    /**
     * @param spawnID is the id of the spawn
     */
    @Override
    public void unloadSpawn(String spawnID) {
        spawns.remove(spawnID);
    }

    /**
     * @param koThID is the id of the KoTh
     */
    @Override
    public void loadKoTH(String koThID) {
        FileConfiguration config = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.GAMES);
        if(config.contains("lobby.cuboid-list." + koThID + ".name") && config.contains("lobby.cuboid-list." + koThID + ".pos1") && config.contains("lobby.cuboid-list." + koThID + ".pos2")) {
            Location pos1,pos2;

            pos1 = utils.getLocationFromString(config.getString("lobby.cuboid-list." + koThID + ".pos1","notSet"));
            pos2 = utils.getLocationFromString(config.getString("lobby.cuboid-list." + koThID + ".pos2","notSet"));

            if(pos1 != null && pos2 != null) {
                koths.put(koThID,new CuboidBuilder(pos1,pos2));
                return;
            }
            plugin.getLogs().error("Can't find pos1 or pos2 of cuboid: " + koThID);
            return;
        }
        plugin.getLogs().error("The cuboid-list doesn't contain id: " + koThID + ", so this cuboid will not be added to koth-list.");
    }

    /**
     * @param koThID is the id of the KoTh
     */
    @Override
    public void unloadKoTH(String koThID) {
        koths.remove(koThID);
    }

    /**
     * @param zoneID is the id of the zone
     */
    @Override
    public void loadZone(String zoneID) {
        FileConfiguration config = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.GAMES);
        if(config.contains("lobby.cuboid-list." + zoneID + ".name") && config.contains("lobby.cuboid-list." + zoneID + ".pos1") && config.contains("lobby.cuboid-list." + zoneID + ".pos2")) {
            Location pos1,pos2;

            pos1 = utils.getLocationFromString(config.getString("lobby.cuboid-list." + zoneID + ".pos1","notSet"));
            pos2 = utils.getLocationFromString(config.getString("lobby.cuboid-list." + zoneID + ".pos2","notSet"));

            if(pos1 != null && pos2 != null) {
                zones.put(zoneID,new CuboidBuilder(pos1,pos2));
                return;
            }
            plugin.getLogs().error("Can't find pos1 or pos2 of cuboid: " + zoneID);
            return;
        }
        plugin.getLogs().error("The cuboid-list doesn't contain id: " + zoneID + ", so this cuboid will not be added to zones-list.");
    }

    /**
     * @param zoneID is the id of the zone
     */
    @Override
    public void unloadZone(String zoneID) {
        zones.remove(zoneID);
    }
}
