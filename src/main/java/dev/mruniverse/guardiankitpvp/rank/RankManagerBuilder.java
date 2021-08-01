package dev.mruniverse.guardiankitpvp.rank;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import dev.mruniverse.guardiankitpvp.interfaces.rank.Rank;
import dev.mruniverse.guardiankitpvp.interfaces.rank.RankManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class RankManagerBuilder implements RankManager {
    private final GuardianKitPvP plugin;
    private final ArrayList<Rank> ranks = new ArrayList<>();

    public RankManagerBuilder(GuardianKitPvP plugin) {
        this.plugin = plugin;
    }

    @Override
    public void loadRanks() {
        FileConfiguration fileConfiguration = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.RANKS);
        if (fileConfiguration.getConfigurationSection("Ranks") == null)
            return;
        for (String currentRank : plugin.getKitPvP().getFileStorage().getContent(GuardianFiles.RANKS,"Ranks",false)) {
            int i = fileConfiguration.getInt("Ranks." + currentRank + ".Required-Exp");
            List<String> list = fileConfiguration.getStringList("Ranks." + currentRank + ".Commands-Executed-When-Rank-Reached");
            String prefix;
            String secondPrefix;
            if(fileConfiguration.contains("Ranks." + currentRank + ".Prefix")) {
                prefix = ChatColor.translateAlternateColorCodes('&',fileConfiguration.getString("Ranks." + currentRank + ".Prefix","&4&lX&c&lPrefixError&4&lX &7"));
            } else {
                prefix = ChatColor.translateAlternateColorCodes('&',fileConfiguration.getString("General-Ranks-Prefix","&4&lX&c&lPrefixError&4&lX &6"));
            }
            if(fileConfiguration.contains("Ranks." + currentRank + ".SecondPrefix")) {
                secondPrefix = ChatColor.translateAlternateColorCodes('&',fileConfiguration.getString("Ranks." + currentRank + ".SecondPrefix","&4&lX&c&lPrefixError&4&lX &7"));
            } else {
                secondPrefix = ChatColor.translateAlternateColorCodes('&',fileConfiguration.getString("General-Ranks-Prefix","&4&lX&c&lPrefixError&4&lX &6"));
            }
            ranks.add(new RankBuilder(currentRank,prefix.replace("%rank%",currentRank),secondPrefix.replace("%rank%",currentRank),i,list));
        }
    }

    @Override
    public ArrayList<Rank> getRanks() {
        return ranks;
    }
}
