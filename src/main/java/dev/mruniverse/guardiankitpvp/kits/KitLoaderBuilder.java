package dev.mruniverse.guardiankitpvp.kits;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.KitType;
import dev.mruniverse.guardiankitpvp.interfaces.kits.KitInfo;
import dev.mruniverse.guardiankitpvp.interfaces.kits.KitLoader;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class KitLoaderBuilder implements KitLoader {
    private final GuardianKitPvP plugin;

    public KitLoaderBuilder(GuardianKitPvP plugin) {
        this.plugin = plugin;
    }

    @Override
    public void unload() {

    }

    @Override
    public void loadKits(KitType kitType) {

    }

    @Override
    public void getToSelect(KitType kitType, Player player, String kitName) {

    }

    @Override
    public void updateKits() {

    }

    @Override
    public void loadKit(KitType kitType, String kitName) {

    }

    @Override
    public void unloadKit(KitType kitType, String kitName) {

    }

    @Override
    public HashMap<String, KitInfo> getKits(KitType kitType) {
        return null;
    }

    @Override
    public HashMap<String, KitInfo> getKitsUsingID(KitType kitType) {
        return null;
    }
}
