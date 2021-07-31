package dev.mruniverse.guardiankitpvp.utils;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerManager;
import dev.mruniverse.guardianlib.core.GuardianLIB;
import dev.mruniverse.guardianlib.core.utils.Utils;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GuardianUtils {
    private final Utils utils = GuardianLIB.getControl().getUtils();
    private final GuardianKitPvP plugin;

    public GuardianUtils(GuardianKitPvP plugin) {
        this.plugin = plugin;
    }


    /**
     * @param player player to send
     * @param message current message
     */
    public void sendMessage(Player player,String message) {
        player.sendMessage(replaceVariables(message,player));
    }

    /**
     * @param player player to send
     * @param message current message
     * @param chatPlayer player from the player.
     * @param chatMessage message from the chat.
     */
    public void sendMessage(Player player,String message,Player chatPlayer,String chatMessage) {
        message = ChatColor.translateAlternateColorCodes('&',replaceVariables(message,chatPlayer));
        if(chatPlayer.hasPermission("grftb.chat.color")) {
            sendMessage(player, message.replace("%message%", ChatColor.translateAlternateColorCodes('&',chatMessage)));
        } else {
            sendMessage(player, message.replace("%message%", ChatColor.stripColor(chatMessage)));
        }
    }

    public String getDateFormat() {
        String dateFormat = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.SETTINGS).getString("settings.dateFormat","dd/MM/yyyy");
        return "" + (new SimpleDateFormat(dateFormat).format(Calendar.getInstance().getTime()));

    }

    public String replaceVariables(String text, Player player) {
        PlayerManager manager = plugin.getKitPvP().getPlayers().getUser(player.getUniqueId());
        if(manager == null) {
            plugin.getKitPvP().getPlayers().addPlayer(player);
            manager = plugin.getKitPvP().getPlayers().getUser(player.getUniqueId());
        }
        text = text.replace("%player_name%",player.getName())
                .replace("%player%",player.getName())
                .replace("%ks",manager.getKs() + "")
                .replace("%player_display_name%",player.getDisplayName())
                .replace("[new line]","\n")
                .replace("%coins%", manager.getCoins() + "")
                .replace("%wins%",manager.getWins() + "")
                .replace("%kits%", manager.getKits().size() + "")
                .replace("%kills%", manager.getKills() + "")
                .replace("%deaths%", manager.getDeaths() + "")
                .replace("%rank%",manager.getRank().getName())
                .replace("%player_rank%",manager.getRank().getName())
                .replace("%player_next_rank%",manager.getRank().getName())
                .replace("%online%",plugin.getServer().getOnlinePlayers().size() + "")
                .replace("%rank_required%",manager.getRank().getName())
                .replace("%timeFormat%",getDateFormat());
        if(manager.getNextRank() != null) {
            text = text.replace("%next_rank%",manager.getRank().getName())
                    .replace("%next_rank_required%",manager.getNextRank().getRequiredExp() + "");
        } else {
            text = text.replace("%next_rank%","MAX")
                    .replace("%next_rank_required%","0");
        }
        if(plugin.hasPAPI()) { text = PlaceholderAPI.setPlaceholders(player,text); }
        return text;
    }
}
