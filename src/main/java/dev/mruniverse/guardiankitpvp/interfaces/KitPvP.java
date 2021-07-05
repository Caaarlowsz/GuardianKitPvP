package dev.mruniverse.guardiankitpvp.interfaces;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianClass;
import dev.mruniverse.guardiankitpvp.interfaces.kits.KitLoader;
import dev.mruniverse.guardiankitpvp.interfaces.storage.DataStorage;
import dev.mruniverse.guardiankitpvp.interfaces.storage.FileStorage;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerData;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerManager;

public interface KitPvP {
    KitPvP setMain(GuardianKitPvP plugin);

    KitPvP setItemsInfo(ItemsInfo itemsInfo);

    KitPvP setFileStorage(FileStorage fileStorage);

    KitPvP setKitLoader(KitLoader kitLoader);

    KitPvP setDataStorage(DataStorage dataStorage);

    KitPvP setPlayerData(PlayerData playerData);

    KitPvP setDefaultPlayerManager(PlayerManager playerManager);

    ItemsInfo getItemsInfo();

    FileStorage getFileStorage();

    KitLoader getKitLoader();

    DataStorage getDataStorage();

    PlayerManager getPlayerManager();

    PlayerData getPlayers();

    void create();

    void reportIssue(String errorMessage);

    void resetDefault(GuardianClass guardianClass);

    boolean isUsingMySQL();
}
