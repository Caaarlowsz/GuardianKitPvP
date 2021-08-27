package dev.mruniverse.guardiankitpvp.interfaces.holograms;

import dev.mruniverse.guardiankitpvp.enums.TopHologram;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public interface HoloManager {
    void loadTops();

    void updateSpawn();

    void updateStatHologram(Player player);

    void loadStats(Player player);

    void loadFrom(TopHologram top,List<Map.Entry<String,Integer>> list);
}
