package dev.mruniverse.guardiankitpvp.storage;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import dev.mruniverse.guardiankitpvp.interfaces.storage.DataInfo;
import dev.mruniverse.guardiankitpvp.interfaces.storage.DataStorage;

import java.util.HashMap;
import java.util.UUID;

public class DataInfoBuilder implements DataInfo {
    private GuardianKitPvP plugin;
    public HashMap<String, Integer> coins = new HashMap<>();
    public HashMap<String, String> kits = new HashMap<>();
    public HashMap<String, String> selectedKits = new HashMap<>();

    public DataInfoBuilder(GuardianKitPvP plugin) {
        this.plugin = plugin;
    }

    @Override
    public GuardianKitPvP getPlugin() { return plugin; }

    @Override
    public HashMap<String, Integer> getCoins() { return coins; }

    @Override
    public HashMap<String, String> getKits() { return kits; }

    @Override
    public HashMap<String, String> getSelectedKits() { return selectedKits; }

    @Override
    public void setPlugin(GuardianKitPvP plugin) { this.plugin = plugin; }

    @Override
    public int getCoins(UUID uuid) { return coins.get(uuid.toString().replace("-","")); }

    @Override
    public String getKits(UUID uuid) { return kits.get(uuid.toString().replace("-","")); }

    @Override
    public String getSelectedKit(UUID uuid) { return selectedKits.get(uuid.toString().replace("-","")); }

    @Override
    public void setCoins(UUID uuid,Integer value) { coins.put(uuid.toString().replace("-",""), value); }

    @Override
    public void setKits(UUID uuid,String value) { kits.put(uuid.toString().replace("-",""), value); }

    @Override
    public void setSelectedKit(UUID uuid,String value) { selectedKits.put(uuid.toString().replace("-",""), value); }

    @Override
    public boolean exists(UUID uuid) { return coins.containsKey(uuid.toString().replace("-","")); }

    @Override
    public void savePlayer(UUID uuid) {
        String table = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.SETTINGS).getString("settings.game.mysql.table-prefix");
        String id = uuid.toString().replace("-","");
        DataStorage storage = plugin.getKitPvP().getDataStorage();
        storage.setInt(table, "Coins", "Player", id, coins.get(id));
        storage.setString(table, "SelectedKit", "Player", id, selectedKits.get(id));
        storage.setString(table, "Kits", "Player", id, kits.get(id));
        coins.remove(id);
        selectedKits.remove(id);
        kits.remove(id);
    }

    @Override
    public void addPlayer(UUID uuid) {
        if(exists(uuid)) return;
        String table = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.SETTINGS).getString("settings.game.mysql.table-prefix");
        String id = uuid.toString().replace("-","");
        DataStorage storage = plugin.getKitPvP().getDataStorage();
        coins.put(id,storage.getInt(table,"Coins","Player",id));
        selectedKits.put(id,storage.getString(table,"SelectedKit","Player",id));
        kits.put(id,storage.getString(table,"Kits","Player",id));
    }

    @Override
    public void save() {
        String table = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.SETTINGS).getString("settings.game.mysql.table-prefix");
        DataStorage storage = plugin.getKitPvP().getDataStorage();
        for(String id : kits.keySet()) {
            storage.setString(table, "Kits", "Player", id, kits.get(id));
        }
        kits.clear();
        for(String id : selectedKits.keySet()) {
            storage.setString(table, "SelectedKit", "Player", id, selectedKits.get(id));
        }
        selectedKits.clear();
        for(String id : coins.keySet()) {
            storage.setInt(table, "Coins", "Player", id, coins.get(id));
        }
        coins.clear();
    }
}
