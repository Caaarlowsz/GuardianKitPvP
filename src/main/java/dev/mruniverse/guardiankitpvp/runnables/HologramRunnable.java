package dev.mruniverse.guardiankitpvp.runnables;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import dev.mruniverse.guardiankitpvp.enums.GuardianHolograms;
import dev.mruniverse.guardiankitpvp.enums.TopHologram;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HologramRunnable extends BukkitRunnable {

    private final GuardianKitPvP plugin;

    private final int topSize;

    public HologramRunnable(GuardianKitPvP plugin) {
        this.plugin = plugin;
        this.topSize = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.HOLOGRAMS).getInt(GuardianHolograms.getSizePath());
    }

    public HashMap<String, String> getData() {
        if(plugin.getKitPvP().isUsingMySQL()) {
            return plugin.getKitPvP().getDataStorage().getMySQL().getUsers();
        }
        return plugin.getKitPvP().getDataStorage().getSQL().getUsers();
    }


    public List<Map.Entry<String, Integer>> getTopPlayers(HashMap<String, String> paramHashMap, TopHologram paramStat, int paramInt) {
        if (paramInt < 1)
            throw new IllegalArgumentException("Amount must be a number above 0!");
        HashMap<String, Integer> hashMap = new HashMap<>();
        for (String str : paramHashMap.keySet()) {
            hashMap.put(str, Integer.valueOf(paramHashMap.get(str).split(":")[paramStat.getId()]));
        }
        if (hashMap.size() < paramInt) {
            int i = paramInt - hashMap.size() + 1;
            for (byte b = 1; b < i; b++)
                hashMap.put("NO_PLAYER" + b, 0);
        }
        LinkedList<Map.Entry<String,Integer>> linkedList = new LinkedList<>(hashMap.entrySet());
        linkedList.sort((param1Entry1, param1Entry2) -> param1Entry2.getValue() - param1Entry1.getValue());
        return linkedList;
    }

    @Override
    public void run() {
        try {
            HashMap<String, String> hashMap = getData();
            for(int i = 1; i<=topSize; i++) {
                for(TopHologram holo : TopHologram.values()) {
                    List<Map.Entry<String, Integer>> list = getTopPlayers(hashMap, holo, i);
                    plugin.getKitPvP().getHoloManager().loadFrom(holo,list);
                }
            }

            /*
             * BUKKIT RUNNABLE HEAD
             */
        } catch (Throwable throwable) {
            plugin.getLogs().error("Can't update holograms and heads leaderboard");
            plugin.getLogs().error(throwable);
        }
    }
}
