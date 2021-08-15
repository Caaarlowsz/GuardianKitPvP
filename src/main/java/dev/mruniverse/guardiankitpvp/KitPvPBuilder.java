package dev.mruniverse.guardiankitpvp;

import dev.mruniverse.guardiankitpvp.enums.GuardianClass;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import dev.mruniverse.guardiankitpvp.interfaces.*;
import dev.mruniverse.guardiankitpvp.interfaces.kits.ItemAbilities;
import dev.mruniverse.guardiankitpvp.interfaces.kits.KitLoader;
import dev.mruniverse.guardiankitpvp.interfaces.listeners.ListenerController;
import dev.mruniverse.guardiankitpvp.interfaces.rank.RankManager;
import dev.mruniverse.guardiankitpvp.interfaces.scoreboard.BoardController;
import dev.mruniverse.guardiankitpvp.interfaces.scoreboard.ScoreInfo;
import dev.mruniverse.guardiankitpvp.interfaces.storage.DataStorage;
import dev.mruniverse.guardiankitpvp.interfaces.storage.FileStorage;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerData;
import dev.mruniverse.guardiankitpvp.storage.FileStorageBuilder;

public class KitPvPBuilder implements KitPvP {

    private GuardianKitPvP plugin;

    private ItemAbilities itemAbilities;

    private ItemsInfo itemsInfo;

    private FileStorage fileStorage;

    private RankManager rankManager;

    private KitLoader kitLoader;

    private DataStorage dataStorage;

    private PlayerData playerData;

    private ListenerController listenerController;

    private BoardController boardController;

    private ScoreInfo scoreInfo;

    private boolean alwaysFalse = false;

    private boolean alwaysEnable = false;

    @Override
    public KitPvP setItemAbilities(ItemAbilities abilities) {
        this.itemAbilities = abilities;
        return this;
    }

    @Override
    public KitPvP setMain(GuardianKitPvP plugin) {
        this.plugin = plugin;
        return this;
    }

    @Override
    public KitPvP setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
        return this;
    }

    @Override
    public KitPvP setBoardController(BoardController boardController) {
        this.boardController = boardController;
        return this;
    }

    @Override
    public KitPvP setRankManager(RankManager rankManager) {
        this.rankManager = rankManager;
        return this;
    }

    @Override
    public KitPvP setListenerController(ListenerController listenerController) {
        this.listenerController = listenerController;
        return this;
    }

    @Override
    public KitPvP setScoreInfo(ScoreInfo scoreInfo) {
        this.scoreInfo = scoreInfo;
        return this;
    }

    @Override
    public KitPvP setItemsInfo(ItemsInfo itemsInfo) {
        this.itemsInfo = itemsInfo;
        return this;
    }

    @Override
    public KitPvP setFileStorage(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
        return this;
    }

    @Override
    public KitPvP setKitLoader(KitLoader kitLoader) {
        this.kitLoader = kitLoader;
        return this;
    }

    @Override
    public KitPvP setDataStorage(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        return this;
    }


    @Override
    public ItemsInfo getItemsInfo() {
        return itemsInfo;
    }

    private GuardianKitPvP getMain() {
        if(plugin != null) return plugin;
        return GuardianKitPvP.getInstance();
    }

    @Override
    public ItemAbilities getItemAbilities() {
        return itemAbilities;
    }

    @Override
    public FileStorage getFileStorage() {
        if(fileStorage != null) return fileStorage;
        return new FileStorageBuilder(getMain());
    }

    @Override
    public KitLoader getKitLoader() {
        return kitLoader;
    }

    @Override
    public DataStorage getDataStorage() {
        return dataStorage;
    }


    @Override
    public RankManager getRankManager() { return rankManager; }

    @Override
    public PlayerData getPlayers() {
        return playerData;
    }

    @Override
    public ListenerController getListenerController() {
        return listenerController;
    }

    @Override
    public BoardController getBoardController() { return boardController; }

    @Override
    public ScoreInfo getScoreInfo() { return scoreInfo; }



    @Override
    public void create() {
        if(playerData == null) reportIssue("&9PLUGIN-BUILDER | &fThe plugin was loaded with issues, please contact developer! Error Code: 0");
        playerData.setPlugin(plugin);
        if(itemsInfo == null) reportIssue("&9PLUGIN-BUILDER | &fThe plugin was loaded with issues, please contact developer! Error Code: 1");
        if(fileStorage == null) reportIssue("&9PLUGIN-BUILDER | &fThe plugin was loaded with issues, please contact developer! Error Code: 2");
        if(kitLoader == null) reportIssue("&9PLUGIN-BUILDER | &fThe plugin was loaded with issues, please contact developer! Error Code: 3");
        if(dataStorage == null) reportIssue("&9PLUGIN-BUILDER | &fThe plugin was loaded with issues, please contact developer! Error Code: 4");
        if(rankManager == null) reportIssue("&9PLUGIN-BUILDER | &fThe plugin was loaded with issues, please contact developer! Error Code: 5");
        if(itemAbilities == null) reportIssue("&9PLUGIN-BUILDER | &fThe plugin was loaded with issues, please contact developer! Error Code: 6");
        plugin.getLogs().info("&9PLUGIN-BUILDER | &fThe KitPvP internal management was loaded correctly using default settings.");
    }

    @Override
    public void reportIssue(String errorMessage) {
        if(plugin == null) return;
        if(plugin.getLogs() == null) return;
        plugin.getLogs().info(errorMessage);
    }

    @Override
    public void disableMySQL() {
         alwaysFalse = true;
         alwaysEnable = false;
    }

    @Override
    public void enableMySQL() {
        alwaysEnable = true;
        alwaysFalse = false;
    }

    @Override
    public void resetDefault(GuardianClass guardianClass) {
        try {
            switch (guardianClass) {
                case ITEMS_INFO:
                    if(itemsInfo != null) itemsInfo = itemsInfo.getClass().newInstance();
                    break;
                case KIT_LOADER:
                    if(kitLoader != null) kitLoader = kitLoader.getClass().newInstance();
                    break;
                case DATA_STORAGE:
                    if(dataStorage != null) dataStorage = dataStorage.getClass().newInstance();
                    break;
                case FILE_STORAGE:
                    if(fileStorage != null) fileStorage = fileStorage.getClass().newInstance();
                    break;
                case RANK_MANAGER:
                    if(rankManager != null) rankManager = rankManager.getClass().newInstance();
                    break;
                case ALL:
                    itemsInfo = itemsInfo.getClass().newInstance();
                    kitLoader = kitLoader.getClass().newInstance();
                    dataStorage = dataStorage.getClass().newInstance();
                    fileStorage = fileStorage.getClass().newInstance();
                    rankManager = rankManager.getClass().newInstance();
                    break;
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public boolean isUsingMySQL() {
        if(alwaysEnable) return true;
        if(!alwaysFalse) return fileStorage.getControl(GuardianFiles.SETTINGS).getBoolean("settings.game.mysql.toggle");
        return false;
    }
}
