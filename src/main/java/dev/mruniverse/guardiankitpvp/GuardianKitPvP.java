package dev.mruniverse.guardiankitpvp;

import dev.mruniverse.guardiankitpvp.interfaces.KitPvP;
import dev.mruniverse.guardiankitpvp.storage.FileStorageBuilder;
import dev.mruniverse.guardiankitpvp.storage.PlayerDataBuilder;
import dev.mruniverse.guardiankitpvp.storage.PlayerManagerBuilder;
import dev.mruniverse.guardianlib.core.utils.ExternalLogger;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class GuardianKitPvP extends JavaPlugin {

    private static GuardianKitPvP instance;

    @SuppressWarnings("unused")
    public static GuardianKitPvP getInstance() { return instance; }

    private KitPvP kitPvP;

    private ExternalLogger logger;

    public ExternalLogger getLogs() { return logger; }

    public KitPvP getKitPvP() { return kitPvP; }

    @SuppressWarnings("unused")
    public void setKitPvP(KitPvP kitPvP) { this.kitPvP = kitPvP; }

    public void setLogs(ExternalLogger logger) { this.logger = logger; }

    @Override
    public void onEnable() {
        instance = this;

        setLogs(new ExternalLogger(this,"GuardianKitPvP","dev.mruniverse.guardiankitpvp"));

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                kitPvP = new KitPvPBuilder();
                kitPvP.setPlayerData(new PlayerDataBuilder())
                        .setDefaultPlayerManager(new PlayerManagerBuilder().setPlugin(instance))
                        .setFileStorage(new FileStorageBuilder(instance))
                        .create();

            }
        };
        runnable.runTaskLater(this, 1L);

    }

}
