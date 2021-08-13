package dev.mruniverse.guardiankitpvp.utils.command.sub;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerManager;
import dev.mruniverse.guardianlib.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BowHitsCommand {
    private final GuardianKitPvP main;
    private final String command;

    public BowHitsCommand(GuardianKitPvP kitPvP, String command) {
        this.command = command;
        main = kitPvP;
    }

    public void usage(CommandSender sender, String[] arguments) {
        Utils utils = main.getUtils().getUtils();
        if (arguments[0].equalsIgnoreCase("set")) {
            if (arguments.length == 3) {
                String playerName = arguments[1];
                Player player = Bukkit.getPlayer(playerName);
                if(player != null) {
                    PlayerManager manager = main.getKitPvP().getPlayers().getUser(player.getUniqueId());
                    if(manager != null) {
                        int number = Integer.parseInt(arguments[2]);
                        manager.setBowHits(number);
                        utils.sendMessage(sender,"&aBowHits of &b" + playerName + "&a now is &b" + number);
                        return;
                    }
                    playerIssue(sender,playerName);
                    return;
                }
                playerIssue(sender, playerName);
                return;
            }
            argumentsIssue(sender);
            return;
        }
        if (arguments[0].equalsIgnoreCase("add")) {
            if (arguments.length == 3) {
                String playerName = arguments[1];
                Player player = Bukkit.getPlayer(playerName);
                if(player != null) {
                    PlayerManager manager = main.getKitPvP().getPlayers().getUser(player.getUniqueId());
                    if(manager != null) {
                        int number = Integer.parseInt(arguments[2]);
                        manager.addBH(number);
                        utils.sendMessage(sender,"&aAdded &b" + number + "&a BowHits to &b" + playerName + "&a.");
                        return;
                    }
                    playerIssue(sender,playerName);
                    return;
                }
                playerIssue(sender, playerName);
                return;
            }
            argumentsIssue(sender);
            return;
        }
        if (arguments[0].equalsIgnoreCase("remove")) {
            if (arguments.length == 3) {
                String playerName = arguments[1];
                Player player = Bukkit.getPlayer(playerName);
                if(player != null) {
                    PlayerManager manager = main.getKitPvP().getPlayers().getUser(player.getUniqueId());
                    if(manager != null) {
                        int number = Integer.parseInt(arguments[2]);
                        manager.removeBH(number);
                        utils.sendMessage(sender,"&aRemoved &b" + number + "&a BowHits from &b" + playerName + "&a.");
                        return;
                    }
                    playerIssue(sender,playerName);
                    return;
                }
                playerIssue(sender, playerName);
                return;
            }
            argumentsIssue(sender);
            return;
        }
        help(sender,utils);
    }
    private void help(CommandSender sender,Utils utils) {
        String cmdPrefix = "&e/" + command;
        sender.sendMessage(" ");
        utils.sendMessage(sender,"&b------------ &aGuardian KP &b------------");
        utils.sendMessage(sender,"&6Admin - Bow Hits Commands:");
        utils.sendMessage(sender,cmdPrefix + " admin bh set (player) (coins) &e- &fSet bow hits of a player");
        utils.sendMessage(sender,cmdPrefix + " admin bh add (player) (coins) &e- &fAdd bow hits to a player");
        utils.sendMessage(sender,cmdPrefix + " admin bh remove (player) (coins) &e- &fRemove bow hits from a player");
        utils.sendMessage(sender,"&b------------ &aGuardian KP &b------------");
    }
    private void argumentsIssue(CommandSender sender) {
        main.getUtils().getUtils().sendMessage(sender,"&aInvalid arguments, please use &b/" + command + " admin &ato see all commands.");
    }
    private void playerIssue(CommandSender sender,String playerName) {
        main.getUtils().getUtils().sendMessage(sender,"&aInvalid player, the player &b" + playerName + "&a is not online.");
    }

}