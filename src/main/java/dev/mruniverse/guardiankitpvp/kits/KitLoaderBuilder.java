package dev.mruniverse.guardiankitpvp.kits;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import dev.mruniverse.guardiankitpvp.enums.KitType;
import dev.mruniverse.guardiankitpvp.enums.PathType;
import dev.mruniverse.guardiankitpvp.interfaces.kits.KitInfo;
import dev.mruniverse.guardiankitpvp.interfaces.kits.KitLoader;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class KitLoaderBuilder implements KitLoader {
    private final GuardianKitPvP plugin;

    private final HashMap<String,KitInfo> kits = new HashMap<>();

    private final HashMap<String,KitInfo> kitsUsingID = new HashMap<>();

    private final HashMap<String,KitInfo> eventKits = new HashMap<>();

    private final HashMap<String,KitInfo> eventsKitsUsingID = new HashMap<>();

    private final HashMap<String,KitInfo> tournamentKits = new HashMap<>();

    private final HashMap<String,KitInfo> tournamentKitsUsingID = new HashMap<>();

    public KitLoaderBuilder(GuardianKitPvP plugin) {
        this.plugin = plugin;

        updateKits();
    }

    @Override
    public void unload() {
        kits.clear();
        kitsUsingID.clear();
        eventKits.clear();
        eventsKitsUsingID.clear();
        tournamentKits.clear();
        tournamentKitsUsingID.clear();
    }

    @Override
    public void loadKits(KitType kitType) {
        for(String kit : plugin.getKitPvP().getFileStorage().getContent(GuardianFiles.KITS,kitType.getPath(PathType.WITHOUT),false)) {
            loadKit(kitType,kit);
        }
    }

    @Override
    public void getToSelect(KitType kitType, Player player, String kitName) {

    }

    @Override
    public void updateKits() {
        kits.clear();
        kitsUsingID.clear();
        eventKits.clear();
        eventsKitsUsingID.clear();
        tournamentKits.clear();
        tournamentKitsUsingID.clear();

        loadKits(KitType.EVENTS);
        loadKits(KitType.NORMAL);
        loadKits(KitType.TOURNAMENT);

        plugin.getLogs().info(kits.keySet().size() + " normal kit(s) has been loaded.");
        plugin.getLogs().info(eventKits.keySet().size() + " event kit(s) has been loaded.");
        plugin.getLogs().info(tournamentKits.keySet().size() + " tournament kit(s) has been loaded.");
    }

    @Override
    public void loadKit(KitType kitType, String kitName) {
        KitInfo kitInfo = new KitInfoBuilder(plugin,kitType,kitName);
        switch (kitType) {
            case NORMAL:
                kitsUsingID.put(kitInfo.getID(),kitInfo);
                kits.put(kitName,kitInfo);
                return;
            case EVENTS:
                eventsKitsUsingID.put(kitInfo.getID(),kitInfo);
                eventKits.put(kitName,kitInfo);
                return;
            case TOURNAMENT:
            default:
                tournamentKitsUsingID.put(kitInfo.getID(),kitInfo);
                tournamentKits.put(kitName,kitInfo);
        }
    }

    @Override
    public void unloadKit(KitType kitType, String kitName) {
        switch (kitType) {
            case NORMAL:
                if(kits.get(kitName) != null) kitsUsingID.remove(kits.get(kitName).getID());
                kits.remove(kitName);
                return;
            case EVENTS:
                if(eventKits.get(kitName) != null) eventsKitsUsingID.remove(eventKits.get(kitName).getID());
                eventKits.remove(kitName);
                return;
            case TOURNAMENT:
                if(tournamentKits.get(kitName) != null) tournamentKitsUsingID.remove(tournamentKits.get(kitName).getID());
                tournamentKits.remove(kitName);
        }
    }

    @Override
    public HashMap<String, KitInfo> getKits(KitType kitType) {
        switch (kitType) {
            default:
            case NORMAL:
                return kits;
            case EVENTS:
                return eventKits;
            case TOURNAMENT:
                return tournamentKits;
        }
    }

    @Override
    public HashMap<String, KitInfo> getKitsUsingID(KitType kitType) {
        switch (kitType) {
            default:
            case NORMAL:
                return kitsUsingID;
            case TOURNAMENT:
                return tournamentKitsUsingID;
            case EVENTS:
                return eventsKitsUsingID;
        }
    }
}
