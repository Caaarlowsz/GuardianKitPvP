package dev.mruniverse.guardiankitpvp;

import dev.mruniverse.guardiankitpvp.abilities.Ghost;
import dev.mruniverse.guardiankitpvp.enums.GuardianArmor;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import dev.mruniverse.guardiankitpvp.enums.KitType;
import dev.mruniverse.guardiankitpvp.interfaces.KitPvP;
import dev.mruniverse.guardiankitpvp.interfaces.kits.KitInfo;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerManager;
import dev.mruniverse.guardiankitpvp.kits.ItemAbilitiesBuilder;
import dev.mruniverse.guardiankitpvp.kits.KitLoaderBuilder;
import dev.mruniverse.guardiankitpvp.listeners.ListenerControllerBuilder;
import dev.mruniverse.guardiankitpvp.rank.RankManagerBuilder;
import dev.mruniverse.guardiankitpvp.runnables.PlayerRunnableBuilder;
import dev.mruniverse.guardiankitpvp.runnables.RotingRunnable;
import dev.mruniverse.guardiankitpvp.runnables.TitleRunnableBuilder;
import dev.mruniverse.guardiankitpvp.scoreboard.BoardControllerBuilder;
import dev.mruniverse.guardiankitpvp.scoreboard.ScoreInfoBuilder;
import dev.mruniverse.guardiankitpvp.storage.DataStorageBuilder;
import dev.mruniverse.guardiankitpvp.storage.FileStorageBuilder;
import dev.mruniverse.guardiankitpvp.storage.PlayerDataBuilder;
import dev.mruniverse.guardiankitpvp.utils.GuardianUtils;
import dev.mruniverse.guardiankitpvp.utils.command.MainCommand;
import dev.mruniverse.guardianlib.core.utils.ExternalLogger;
import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class GuardianKitPvP extends JavaPlugin {

    private static GuardianKitPvP instance;

    @SuppressWarnings("unused")
    public static GuardianKitPvP getInstance() { return instance; }

    private final StringBuilder progressBar = new StringBuilder();

    private String leftPB;

    private String rightPB;

    private String colorComplete;

    private String colorPending;

    private KitPvP kitPvP;

    private ExternalLogger logger;

    public GuardianUtils utils;

    private PlayerRunnableBuilder playerRunnableBuilder;

    private TitleRunnableBuilder titleRunnableBuilder;

    private RotingRunnable rotingRunnable;

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

        utils = new GuardianUtils(instance);

        setLogs(new ExternalLogger(this,"GuardianKitPvP","dev.mruniverse.guardiankitpvp."));

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                hasPAPI = getServer().getPluginManager().isPluginEnabled("PlaceholderAPI");

                setKitPvP(new KitPvPBuilder().setMain(instance)
                        .setFileStorage(new FileStorageBuilder(instance))
                        .setPlayerData(new PlayerDataBuilder())
                        .setItemAbilities(new ItemAbilitiesBuilder(instance))
                        .setBoardController(new BoardControllerBuilder(instance))
                        .setScoreInfo(new ScoreInfoBuilder(instance))
                        .setRankManager(new RankManagerBuilder(instance))
                        .setKitLoader(new KitLoaderBuilder(instance))
                );


                setKitPvP(getKitPvP().setDataStorage(new DataStorageBuilder(instance)));

                setKitPvP(getKitPvP().setListenerController(new ListenerControllerBuilder(instance)));

                getKitPvP().getRankManager().loadRanks();

                getKitPvP().getDataStorage().loadDatabase();

                getKitPvP().getItemAbilities().registerAbility(new Ghost())
                        .finishRegister();

                getKitPvP().getKitLoader().updateKits();

                getServer().getPluginManager().registerEvents(getKitPvP().getItemAbilities(),instance);

                getKitPvP().create();

                playerRunnableBuilder = new PlayerRunnableBuilder(instance);

                titleRunnableBuilder = new TitleRunnableBuilder(instance);

                rotingRunnable = new RotingRunnable(instance);

                loadCommand("gkp");

                loadCommand("kp");

                runRunnable();

                //GENERAL

                FileConfiguration settings = getKitPvP().getFileStorage().getControl(GuardianFiles.SETTINGS);
                String character = StringEscapeUtils.unescapeJava(
                        settings.getString("settings.progressBar.unicode","|")
                );

                leftPB = StringEscapeUtils.unescapeJava(
                        settings.getString("settings.progressBar.left","&8[ ")
                );

                rightPB = StringEscapeUtils.unescapeJava(
                        settings.getString("settings.progressBar.right"," &8]")
                );

                colorComplete = settings.getString("settings.progressBar.color-complete","&a");

                colorPending = settings.getString("settings.progressBar.color-pending","&7");

                for (byte b = 0; b < 20; ) {
                    progressBar.append(character);
                    b++;
                }

            }
        };
        runnable.runTaskLater(this, 1L);

    }

    @Override
    public void onDisable(){
        for (Player player : getServer().getOnlinePlayers()) {
            PlayerManager manager = getKitPvP().getPlayers().getUser(player.getUniqueId());
            kitPvP.getDataStorage().saveStats(player, true, manager);
        }
    }

    public void giveKit(KitType type,Player player) {
        PlayerManager user = getKitPvP().getPlayers().getUser(player.getUniqueId());
        if(user.hasSelectedKit()) {
            String kit = user.getSelectedKit();
            KitInfo info = getKitPvP().getKitLoader().getKitsUsingID(type).get(kit);

            if(info == null) {
                getLogs().info("The kit-id [" + kit + "] doesn't exists or isn't registered.");
                getLogs().info("Kits registered: " + getKitPvP().getKitLoader().getKitsUsingID(type).entrySet());
                return;
            }

            equipKit(info,player);
        }
    }

    public void equipKit(KitInfo kit,Player player) {
        player.getInventory().clear();
        for(Map.Entry<ItemStack,Integer> data : kit.getInventoryItems().entrySet()) {
            player.getInventory().setItem(data.getValue(),data.getKey());
        }

        if(kit.getArmor(GuardianArmor.HELMET) != null) player.getInventory().setHelmet(kit.getArmor(GuardianArmor.HELMET));
        if(kit.getArmor(GuardianArmor.CHESTPLATE) != null) player.getInventory().setChestplate(kit.getArmor(GuardianArmor.CHESTPLATE));
        if(kit.getArmor(GuardianArmor.LEGGINGS) != null) player.getInventory().setLeggings(kit.getArmor(GuardianArmor.LEGGINGS));
        if(kit.getArmor(GuardianArmor.BOOTS) != null) player.getInventory().setBoots(kit.getArmor(GuardianArmor.BOOTS));

    }


    public String getColorComplete() {
        return colorComplete;
    }

    public String getColorPending() {
        return colorPending;
    }

    public String getProgressBar() {
        return progressBar.toString();
    }

    public String getLeftPB() {
        return leftPB;
    }

    public String getRightPB() {
        return rightPB;
    }

    public void runRunnable() {
        if (getKitPvP().getFileStorage().getControl(GuardianFiles.SCOREBOARD).getBoolean("scoreboards.animatedTitle.toggle")) {
            titleRunnableBuilder.runTaskTimer(instance,0L, getKitPvP().getFileStorage().getControl(GuardianFiles.SCOREBOARD).getLong("scoreboards.animatedTitle.repeatTime"));
        }
        playerRunnableBuilder.runTaskTimer(instance,0L,15L);

        rotingRunnable.runTaskTimer(instance,0L,20L);

        if(getKitPvP().getFileStorage().getControl(GuardianFiles.SETTINGS).getStringList("settings.map-locations").size() >= 1) {
            rotingRunnable.rotate();
        }
    }

    public RotingRunnable getRotingRunnable() {
        return rotingRunnable;
    }

    public void loadCommand(String command) {
        PluginCommand cmd = getCommand(command);
        if(cmd == null) return;
        cmd.setExecutor(new MainCommand(this,command));
    }

}
