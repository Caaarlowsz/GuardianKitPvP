package dev.mruniverse.guardiankitpvp;

import dev.mruniverse.guardiankitpvp.enums.GuardianClass;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import dev.mruniverse.guardiankitpvp.interfaces.*;
import dev.mruniverse.guardiankitpvp.interfaces.kits.KitLoader;
import dev.mruniverse.guardiankitpvp.interfaces.storage.DataStorage;
import dev.mruniverse.guardiankitpvp.interfaces.storage.FileStorage;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerData;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerManager;

public class KitPvPBuilder implements KitPvP {

    private GuardianKitPvP plugin;

    private ItemsInfo itemsInfo;

    private FileStorage fileStorage;

    private KitLoader kitLoader;

    private DataStorage dataStorage;

    private PlayerManager playerManager;

    private PlayerData playerData;

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
    public KitPvP setDefaultPlayerManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
        return this;
    }

    @Override
    public ItemsInfo getItemsInfo() {
        return itemsInfo;
    }

    @Override
    public FileStorage getFileStorage() {
        return fileStorage;
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
    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    @Override
    public PlayerData getPlayers() {
        return playerData;
    }

    @Override
    public void create() {
        if(playerData == null) reportIssue("The plugin was loaded with issues, please contact developer! Error Code: 0");
        if(itemsInfo == null) reportIssue("The plugin was loaded with issues, please contact developer! Error Code: 1");
        if(fileStorage == null) reportIssue("The plugin was loaded with issues, please contact developer! Error Code: 2");
        if(kitLoader == null) reportIssue("The plugin was loaded with issues, please contact developer! Error Code: 3");
        if(dataStorage == null) reportIssue("The plugin was loaded with issues, please contact developer! Error Code: 4");
        if(playerManager == null) reportIssue("The plugin was loaded with issues, please contact developer! Error Code: 5");
        plugin.getLogs().info("The KitPvP internal management was loaded correctly using default settings.");
    }

    @Override
    public void reportIssue(String errorMessage) {
        plugin.getLogs().error(errorMessage);
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
                case PLAYER_MANAGER:
                    if(playerManager != null) playerManager = playerManager.getClass().newInstance();
                    break;
                case ALL:
                    itemsInfo = itemsInfo.getClass().newInstance();
                    kitLoader = kitLoader.getClass().newInstance();
                    dataStorage = dataStorage.getClass().newInstance();
                    fileStorage = fileStorage.getClass().newInstance();
                    playerManager = playerManager.getClass().newInstance();
                    break;
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public boolean isUsingMySQL() {
        return fileStorage.getControl(GuardianFiles.SETTINGS).getBoolean("settings.game.mysql.toggle");
    }
}