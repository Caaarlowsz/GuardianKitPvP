package dev.mruniverse.guardiankitpvp.interfaces.storage;

import org.bukkit.entity.Player;

public interface SQL {

    void addKit(String paramPlayer,String kit);

    void removeKit(String paramPlayer,String kit);

    void loadStats(final Player player);

    void saveStats(Player paramPlayer);

    void saveStats(Player paramPlayer,PlayerManager manager);

}
