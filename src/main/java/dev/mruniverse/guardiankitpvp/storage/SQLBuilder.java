package dev.mruniverse.guardiankitpvp.storage;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import dev.mruniverse.guardiankitpvp.enums.SaveMode;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerManager;
import dev.mruniverse.guardiankitpvp.interfaces.storage.SQL;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SQLBuilder implements SQL {
    public HashMap<String, Integer> coins = new HashMap<>();
    public HashMap<String, String> kits = new HashMap<>();
    public HashMap<String, String> selectedKits = new HashMap<>();
    private final GuardianKitPvP plugin;
    public SQLBuilder(GuardianKitPvP main) {
        plugin = main;
    }

    public HashMap<String, Integer> getCoins() { return coins; }

    public HashMap<String, String> getKits() { return kits; }

    public HashMap<String, String> getSelectedKits() { return selectedKits; }

    public void putData() {
        if (coins.size() != 0)
            for (Map.Entry<String, Integer> k : coins.entrySet())
                plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.DATA).set("coins." + k.getKey(), k.getValue());
        if (kits.size() != 0)
            for (Map.Entry<String, String> k : kits.entrySet())
                plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.DATA).set("kits." + k.getKey(), k.getValue());
        if (selectedKits.size() != 0)
            for (Map.Entry<String, String> k : selectedKits.entrySet())
                plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.DATA).set("selected-kit." + k.getKey(), k.getValue());
        plugin.getKitPvP().getFileStorage().save(SaveMode.DATA);
    }

    public void loadData() {
        FileConfiguration file = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.DATA);
        if(file.contains("selected-kit")) {
            for (String player : plugin.getKitPvP().getFileStorage().getContent(GuardianFiles.DATA,"selected-kit",false)) {
                selectedKits.put(player,file.getString("selected-kit." + player));
            }
        }
        if(file.contains("kits")) {
            for (String player : plugin.getKitPvP().getFileStorage().getContent(GuardianFiles.DATA,"kits",false)) {
                selectedKits.put(player,file.getString("kits." + player));
            }
        }
        if(file.contains("coins")) {
            for (String player : plugin.getKitPvP().getFileStorage().getContent(GuardianFiles.DATA,"coins",false)) {
                selectedKits.put(player,file.getString("coins." + player));
            }
        }
    }

    public int getCoins(UUID uuid) { return coins.get(uuid.toString().replace("-","")); }
    public String getKits(UUID uuid) { return kits.get(uuid.toString().replace("-","")); }
    public String getSelectedKit(UUID uuid) { return selectedKits.get(uuid.toString().replace("-","")); }
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean exist(UUID uuid) { return kits.containsKey(uuid.toString().replace("-","")); }

    public void createPlayer(Player player) {
        if(!exist(player.getUniqueId())) {
            coins.put(player.getUniqueId().toString().replace("-", ""), 0);
            String defaultKit = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.SETTINGS).getString("settings.game.default-kits.default");
            if (plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.SETTINGS).getBoolean("settings.game.default-kits.toggle")) {

                kits.put(player.getUniqueId().toString().replace("-", ""), "K" + defaultKit);
                selectedKits.put(player.getUniqueId().toString().replace("-", ""), defaultKit);
            } else {
                kits.put(player.getUniqueId().toString().replace("-", ""), "NONE");
                selectedKits.put(player.getUniqueId().toString().replace("-", ""), "NONE");
            }
        } else {
            String id = player.getUniqueId().toString().replace("-","");
            PlayerManager playerManager = plugin.getKitPvP().getPlayers().getUser(player.getUniqueId());
            playerManager.setCoins(coins.get(id));
            playerManager.setSelectedKit(selectedKits.get(id));
            playerManager.setKits(kits.get(id));
        }
    }
}
