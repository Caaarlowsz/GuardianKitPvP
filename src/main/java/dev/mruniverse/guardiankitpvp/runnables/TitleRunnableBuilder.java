package dev.mruniverse.guardiankitpvp.runnables;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import dev.mruniverse.guardiankitpvp.interfaces.runnables.TitleRunnable;
import dev.mruniverse.guardiankitpvp.interfaces.scoreboard.BoardController;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerData;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerManager;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.UUID;

public class TitleRunnableBuilder extends BukkitRunnable implements TitleRunnable {
    private final GuardianKitPvP plugin;
    private boolean isEnabled;
    private int showingTitle = 0;
    private List<String> titles;
    public TitleRunnableBuilder(GuardianKitPvP main) {
        plugin = main;
        titles = main.getKitPvP().getFileStorage().getControl(GuardianFiles.SCOREBOARD).getStringList("scoreboards.animatedTitle.titles");
        isEnabled = main.getKitPvP().getFileStorage().getControl(GuardianFiles.SCOREBOARD).getBoolean("scoreboards.animatedTitle.toggle");
    }
    public void update() {
        titles = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.SCOREBOARD).getStringList("scoreboards.animatedTitle.titles");
        isEnabled = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.SCOREBOARD).getBoolean("scoreboards.animatedTitle.toggle");
    }
    @Override
    public void run () {
        if(!isEnabled) cancel();
        PlayerData playerData = plugin.getKitPvP().getPlayers();
        BoardController boardController = plugin.getKitPvP().getBoardController();
        for (UUID uuid : playerData.getPlayers().keySet()) {
            PlayerManager playerManagerImpl = playerData.getUser(uuid);
            String currentTitle = titles.get(showingTitle);
            boardController.setTitle(playerManagerImpl.getPlayer(),currentTitle);
            if(showingTitle == (titles.size() - 1)) {
                showingTitle = 0;
            } else {
                showingTitle++;
            }
        }
    }
}
