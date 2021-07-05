package dev.mruniverse.guardiankitpvp.interfaces.storage;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianBoard;
import dev.mruniverse.guardiankitpvp.enums.KitType;
import dev.mruniverse.guardiankitpvp.enums.PlayerStatus;
import dev.mruniverse.guardiankitpvp.interfaces.Game;
import dev.mruniverse.guardiankitpvp.interfaces.kits.KitMenu;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public interface PlayerManager {

    PlayerManager setPlayer(Player player);

    PlayerManager setPlugin(GuardianKitPvP plugin);

    boolean hasSelectedKit();

    boolean getAutoPlayStatus();

    boolean toggleAutoplay();

    KitMenu getKitMenu(KitType kitType);

    GuardianBoard getBoard();
    PlayerStatus getStatus();
    String getName();
    Game getGame();
    Player getPlayer();

    int getLeaveDelay();

    int getWins();

    void setLeaveDelay(int delay);

    void setStatus(PlayerStatus status);

    void setBoard(GuardianBoard board);

    void setGame(Game game);

    void setWins(int wins);

    void addWins();

    void addCoins(int coins);

    void removeCoins(int coins);

    int getCoins();
    void updateCoins(int addOrRemove);
    void setCoins(int coinCounter);

    int getKills();

    void setKills(int kills);

    void setSelectedKit(String kitID);

    void setKits(String kits);

    void finish();

    String getSelectedKit();

    void addKit(String kitID);

    List<String> getKits();

    @SuppressWarnings("unused")
    void addKills();

    int getDeaths();

    void setDeaths(int deaths);

    String getID();

    void addDeaths();

    void create();
}
