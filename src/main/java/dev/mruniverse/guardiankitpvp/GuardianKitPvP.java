package dev.mruniverse.guardiankitpvp;

import dev.mruniverse.guardiankitpvp.interfaces.KitPvP;
import dev.mruniverse.guardiankitpvp.listeners.ListenerControllerBuilder;
import dev.mruniverse.guardiankitpvp.rank.RankManagerBuilder;
import dev.mruniverse.guardiankitpvp.scoreboard.BoardControllerBuilder;
import dev.mruniverse.guardiankitpvp.scoreboard.ScoreInfoBuilder;
import dev.mruniverse.guardiankitpvp.storage.DataStorageBuilder;
import dev.mruniverse.guardiankitpvp.storage.FileStorageBuilder;
import dev.mruniverse.guardiankitpvp.storage.PlayerDataBuilder;
import dev.mruniverse.guardiankitpvp.storage.PlayerManagerBuilder;
import dev.mruniverse.guardiankitpvp.utils.GuardianUtils;
import dev.mruniverse.guardianlib.core.utils.ExternalLogger;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class GuardianKitPvP extends JavaPlugin {

    private static GuardianKitPvP instance;

    @SuppressWarnings("unused")
    public static GuardianKitPvP getInstance() { return instance; }

    private KitPvP kitPvP;

    private ExternalLogger logger;

    public GuardianUtils utils;

    private boolean hasPAPI = false;

    public ExternalLogger getLogs() { return logger; }

    public KitPvP getKitPvP() { return kitPvP; }

    @SuppressWarnings("unused")
    public void setKitPvP(KitPvP kitPvP) { this.kitPvP = kitPvP; }

    public void setLogs(ExternalLogger logger) { this.logger = logger; }

    public GuardianUtils getUtils() { return utils; }

    public boolean hasPAPI() { return hasPAPI; }

    @Override
    public void onEnable() {
        instance = this;

        setLogs(new ExternalLogger(this,"GuardianKitPvP","dev.mruniverse.guardiankitpvp."));

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                hasPAPI = getServer().getPluginManager().isPluginEnabled("PlaceholderAPI");

                setKitPvP(new KitPvPBuilder().setMain(instance)
                        .setDefaultPlayerManager(new PlayerManagerBuilder().setPlugin(instance))
                        .setFileStorage(new FileStorageBuilder(instance))
                        .setPlayerData(new PlayerDataBuilder())
                        .setBoardController(new BoardControllerBuilder(instance))
                        .setScoreInfo(new ScoreInfoBuilder(instance))
                        .setRankManager(new RankManagerBuilder(instance))
                );


                setKitPvP(getKitPvP().setDataStorage(new DataStorageBuilder(instance)));

                setKitPvP(getKitPvP().setListenerController(new ListenerControllerBuilder(instance)));

                getKitPvP().getRankManager().loadRanks();

                getKitPvP().create();

                utils = new GuardianUtils(instance);

            }
        };
        runnable.runTaskLater(this, 1L);

    }

    @Override
    public void onDisable(){
        for (Player player : getServer().getOnlinePlayers())
            kitPvP.getDataStorage().saveStats(player,true);
    }

}
