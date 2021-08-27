package dev.mruniverse.guardiankitpvp.interfaces.storage;

import dev.mruniverse.guardiankitpvp.interfaces.utils.Cuboid;

import java.util.HashMap;

public interface CuboidStorage {
    /**
     * @return Cuboid List
     */
    HashMap<String,Cuboid> getSpawns();

    /**
     * @return Cuboid List
     */
    HashMap<String,Cuboid> getKoTHs();

    /**
     * @return Cuboid List
     */
    HashMap<String,Cuboid> getZones();

    /**
     * @return Cuboid or null
     */
    Cuboid getZone(String zoneID);

    /**
     * @return Cuboid or null
     */
    Cuboid getKoTH(String koThID);

    /**
     * @return Cuboid or null
     */
    Cuboid getSpawn(String spawnID);

    Cuboid getCurrentSpawn();

    Cuboid getCurrentKoTh();

    Cuboid getTeleport();

    void setCurrentSpawn(String spawnID);

    void setCurrentKoTh(String kothID);

    /**
     * @param spawnID is the id of the spawn
     */
    void loadSpawn(String spawnID);

    /**
     * @param spawnID is the id of the spawn
     */
    void unloadSpawn(String spawnID);

    /**
     * @param koThID is the id of the KoTh
     */
    void loadKoTH(String koThID);

    /**
     * @param koThID is the id of the KoTh
     */
    void unloadKoTH(String koThID);

    /**
     * @param zoneID is the id of the zone
     */
    void loadZone(String zoneID);

    /**
     * @param zoneID is the id of the zone
     */
    void unloadZone(String zoneID);

    void init();




}
