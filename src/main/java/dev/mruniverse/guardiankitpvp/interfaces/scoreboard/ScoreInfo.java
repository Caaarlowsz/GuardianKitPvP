package dev.mruniverse.guardiankitpvp.interfaces.scoreboard;

import dev.mruniverse.guardiankitpvp.enums.GuardianBoard;
import org.bukkit.entity.Player;

import java.util.List;

public interface ScoreInfo {
    List<String> getLines(GuardianBoard board, Player player);

    boolean isCorrectSize(String line);

    String getTitle(GuardianBoard board);

    String replaceVariables(String text, Player player);

    String getDateFormat();

}
