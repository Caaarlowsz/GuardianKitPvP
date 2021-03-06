package dev.mruniverse.guardiankitpvp.interfaces.kits;

import dev.mruniverse.guardiankitpvp.enums.KitType;
import org.bukkit.entity.Player;

import java.util.HashMap;


public interface KitLoader {

    void unload();

    void loadKits(KitType kitType);

    void getToSelect(KitType kitType, Player player, String kitName);

    void updateKits();

    void loadKit(KitType kitType,String kitName);

    void unloadKit(KitType kitType,String kitName);

    HashMap<String,KitInfo> getKits(KitType kitType);

    HashMap<String,KitInfo> getKitsUsingID(KitType kitType);
}
