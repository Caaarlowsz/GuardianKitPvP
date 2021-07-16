package dev.mruniverse.guardiankitpvp.interfaces.scoreboard;

import dev.mruniverse.guardiankitpvp.enums.GuardianBoard;
import dev.mruniverse.guardiankitpvp.netherboard.BPlayerBoard;
import org.bukkit.entity.Player;

public interface BoardController {

    BPlayerBoard getBoardOfPlayer(Player player);

    void removeScore(Player player);

    void setScoreboard(GuardianBoard board, Player player);

    void setTitle(Player player, String title);

    void updateScoreboard(GuardianBoard board, Player player);

    void deletePlayer(Player player);

    boolean existPlayer(Player player);

}