package dev.mruniverse.guardiankitpvp.scoreboard;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianBoard;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import dev.mruniverse.guardiankitpvp.interfaces.scoreboard.ScoreInfo;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ScoreInfoBuilder implements ScoreInfo {
    private final GuardianKitPvP plugin;

    public ScoreInfoBuilder(GuardianKitPvP plugin) { this.plugin = plugin; }

    public List<String> getLines(GuardianBoard board, Player player) {
        List<String> lines = new ArrayList<>();
        FileConfiguration scoreboard = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.SCOREBOARD);
        StringBuilder white = new StringBuilder("&f");
        switch(board) {
            case NORMAL:
            default:
                for (String line : scoreboard.getStringList("scoreboards.normal.lines")) {
                    String replacedLine = replaceVariables(line,player);
                    if(!isCorrectSize(replacedLine)) {
                        if (!lines.contains(replacedLine)) {
                            line = replacedLine;
                        } else {
                            if(!isCorrectSize(white + replacedLine)) {
                                line = white + replacedLine;
                                white.append("&r");
                            }
                        }
                        lines.add(line);
                    }
                }
                return lines;
            case WAITING:
                for (String line : scoreboard.getStringList("scoreboards.waiting.lines")) {
                    if (!line.contains("<isStarting>") && !line.contains("<isVoting>") && !line.contains("<GracePeriod>")) {
                        if (line.contains("<isWaiting>")) line = line.replace("<isWaiting>", "");
                        String replacedLine = replaceVariables(line,player);
                        if(!isCorrectSize(replacedLine)) {
                            if (!lines.contains(replacedLine)) {
                                line = replacedLine;
                            } else {
                                if(!isCorrectSize(white + replacedLine)) {
                                    line = white + replacedLine;
                                    white.append("&r");
                                }
                            }
                            lines.add(line);
                        }
                    }
                }
                return lines;
            case VOTING:
                for (String line : scoreboard.getStringList("scoreboards.waiting.lines")) {
                    if (!line.contains("<isWaiting>") && !line.contains("<isStarting>") && !line.contains("<GracePeriod>")) {
                        if (line.contains("<isVoting>")) line = line.replace("<isVoting>", "");
                        if(!isCorrectSize(line)) {
                            String replacedLine = replaceVariables(line,player);
                            if (!lines.contains(replacedLine)) {
                                line = replacedLine;
                            } else {
                                if(!isCorrectSize(white + replacedLine)) {
                                    line = white + replacedLine;
                                    white.append("&r");
                                }
                            }
                            lines.add(line);
                        }
                    }
                }
                return lines;
            case STARTING:
                for (String line : scoreboard.getStringList("scoreboards.waiting.lines")) {
                    if (!line.contains("<isWaiting>") && !line.contains("<isVoting>") && !line.contains("<GracePeriod>")) {
                        if (line.contains("<isStarting>")) line = line.replace("<isStarting>", "");
                        String replacedLine = replaceVariables(line,player);
                        if(!isCorrectSize(replacedLine)) {
                            if (!lines.contains(replacedLine)) {
                                line = replacedLine;
                            } else {
                                if(!isCorrectSize(white + replacedLine)) {
                                    line = white + replacedLine;
                                    white.append("&r");
                                }
                            }
                            lines.add(line);
                        }
                    }
                }
                return lines;
            case GRACE_PERIOD:
                for (String line : scoreboard.getStringList("scoreboards.waiting.lines")) {
                    if (!line.contains("<isWaiting>") && !line.contains("<isVoting>") && !line.contains("<isStarting>")) {
                        if (line.contains("<GracePeriod>")) line = line.replace("<GracePeriod>", "");
                        String replacedLine = replaceVariables(line,player);
                        if(!isCorrectSize(replacedLine)) {
                            if (!lines.contains(replacedLine)) {
                                line = replacedLine;
                            } else {
                                if(!isCorrectSize(white + replacedLine)) {
                                    line = white + replacedLine;
                                    white.append("&r");
                                }
                            }
                            lines.add(line);
                        }
                    }
                }
                return lines;
            case ONE_VS_ONE:
                for (String line : scoreboard.getStringList("scoreboards.one-vs-one-playing.lines")) {
                    String replacedLine = replaceVariables(line,player);
                    if(!isCorrectSize(replacedLine)) {
                        if (!lines.contains(replacedLine)) {
                            line = replacedLine;
                        } else {
                            if(!isCorrectSize(white + replacedLine)) {
                                line = white + replacedLine;
                                white.append("&r");
                            }
                        }
                        lines.add(line);
                    }
                }
                return lines;
            case TOURNAMENT:
                for (String line : scoreboard.getStringList("scoreboards.tournament-playing.lines")) {
                    String replacedLine = replaceVariables(line,player);
                    if(!isCorrectSize(replacedLine)) {
                        if (!lines.contains(replacedLine)) {
                            line = replacedLine;
                        } else {
                            if(!isCorrectSize(white + replacedLine)) {
                                line = white + replacedLine;
                                white.append("&r");
                            }
                        }
                        lines.add(line);
                    }
                }
                return lines;
            case VICTORY_FOR_WINNERS:
                for (String line : scoreboard.getStringList("scoreboards.victory.forWinner.lines")) {
                    String replacedLine = replaceVariables(line,player);
                    if(!isCorrectSize(replacedLine)) {
                        if (!lines.contains(replacedLine)) {
                            line = replacedLine;
                        } else {
                            if(!isCorrectSize(white + replacedLine)) {
                                line = white + replacedLine;
                                white.append("&r");
                            }
                        }
                        lines.add(line);
                    }
                }
                return lines;
            case VICTORY_FOR_OTHERS:
                for (String line : scoreboard.getStringList("scoreboards.victory.forOthers.lines")) {
                    String replacedLine = replaceVariables(line,player);
                    if(!isCorrectSize(replacedLine)) {
                        if (!lines.contains(replacedLine)) {
                            line = replacedLine;
                        } else {
                            if(!isCorrectSize(white + replacedLine)) {
                                line = white + replacedLine;
                                white.append("&r");
                            }
                        }
                        lines.add(line);
                    }
                }
                return lines;
        }
    }

    public boolean isCorrectSize(String line) {
        line = ChatColor.translateAlternateColorCodes('&',line);
        if(39 <= line.length()) {
            plugin.getLogs().error("&fLine: '" + line + "&f' has more than 39 characters, String length is longer than maximum allowed (" + line.length() + " > 39)");
            plugin.getLogs().error("This issue can kick users showing an error.");
            return true;
        }
        return false;
    }

    public String getTitle(GuardianBoard board) {
        FileConfiguration scoreboard = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.SCOREBOARD);
        switch (board) {
            case NORMAL:
                if (scoreboard.getString("scoreboards.normal.title") != null) {
                    return scoreboard.getString("scoreboards.normal.title");
                }
                return "";
            case WAITING:
            case STARTING:
            case GRACE_PERIOD:
            case VOTING:
                if (scoreboard.getString("scoreboards.waiting.title") != null) {
                    return scoreboard.getString("scoreboards.waiting.title");
                }
                return "";
            case ONE_VS_ONE:
                if (scoreboard.getString("scoreboards.one-vs-one-playing.title") != null) {
                    return scoreboard.getString("scoreboards.one-vs-one-playing.title");
                }
                return "";
            case TOURNAMENT:
                if (scoreboard.getString("scoreboards.tournament-playing.title") != null) {
                    return scoreboard.getString("scoreboards.tournament-playing.title");
                }
                return "";
            case VICTORY_FOR_OTHERS:
                if (scoreboard.getString("scoreboards.victory.forOthers.title") != null) {
                    return scoreboard.getString("scoreboards.victory.forOthers.title");
                }
                return "";
            case VICTORY_FOR_WINNERS:
                if (scoreboard.getString("scoreboards.victory.forWinner.title") != null) {
                    return scoreboard.getString("scoreboards.victory.forWinner.title");
                }
                return "";
        }
        return "";
    }

    public String replaceVariables(String text,Player player) {
        return plugin.getUtils().replaceVariables(text,player);
    }

    public String getDateFormat() {
        String dateFormat = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.SETTINGS).getString("settings.dateFormat");
        if(dateFormat == null) dateFormat = "dd/MM/yyyy";
        return "" + (new SimpleDateFormat(dateFormat).format(Calendar.getInstance().getTime()));

    }

}
