package dev.mruniverse.guardiankitpvp.interfaces;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianClass;
import dev.mruniverse.guardiankitpvp.interfaces.kits.ItemAbilities;
import dev.mruniverse.guardiankitpvp.interfaces.kits.KitLoader;
import dev.mruniverse.guardiankitpvp.interfaces.listeners.ListenerController;
import dev.mruniverse.guardiankitpvp.interfaces.rank.RankManager;
import dev.mruniverse.guardiankitpvp.interfaces.scoreboard.BoardController;
import dev.mruniverse.guardiankitpvp.interfaces.scoreboard.ScoreInfo;
import dev.mruniverse.guardiankitpvp.interfaces.storage.DataStorage;
import dev.mruniverse.guardiankitpvp.interfaces.storage.FileStorage;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerData;

@SuppressWarnings("UnusedReturnValue")
public interface KitPvP {
    KitPvP setMain(GuardianKitPvP plugin);

    KitPvP setItemsInfo(ItemsInfo itemsInfo);

    KitPvP setItemAbilities(ItemAbilities itemAbilities);

    KitPvP setFileStorage(FileStorage fileStorage);

    KitPvP setKitLoader(KitLoader kitLoader);

    KitPvP setListenerController(ListenerController listenerController);

    KitPvP setRankManager(RankManager rankManager);

    KitPvP setDataStorage(DataStorage dataStorage);

    KitPvP setPlayerData(PlayerData playerData);

    KitPvP setBoardController(BoardController boardController);

    KitPvP setScoreInfo(ScoreInfo scoreInfo);

    ItemsInfo getItemsInfo();

    FileStorage getFileStorage();

    KitLoader getKitLoader();

    RankManager getRankManager();

    DataStorage getDataStorage();

    PlayerData getPlayers();

    BoardController getBoardController();

    ScoreInfo getScoreInfo();

    ListenerController getListenerController();

    ItemAbilities getItemAbilities();

    void create();

    void disableMySQL();

    void enableMySQL();

    void reportIssue(String errorMessage);

    void resetDefault(GuardianClass guardianClass);

    boolean isUsingMySQL();
}
