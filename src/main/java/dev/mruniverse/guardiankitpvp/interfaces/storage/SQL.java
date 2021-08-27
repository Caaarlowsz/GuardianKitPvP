package dev.mruniverse.guardiankitpvp.interfaces.storage;

import org.bukkit.entity.Player;

import java.util.HashMap;

public interface SQL {

    void addKit(String paramPlayer,String kit);

    void removeKit(String paramPlayer,String kit);

    void loadStats(final Player player);

    void saveStats(Player paramPlayer);

    void saveStats(Player paramPlayer,PlayerManager manager);

    HashMap<String, String> getUsers();

}
