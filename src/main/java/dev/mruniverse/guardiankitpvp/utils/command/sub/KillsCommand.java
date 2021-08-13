package dev.mruniverse.guardiankitpvp.utils.command.sub;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerManager;
import dev.mruniverse.guardianlib.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KillsCommand {
    private final GuardianKitPvP main;
    private final String command;

    public KillsCommand(GuardianKitPvP kitPvP, String command) {
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
                        manager.setKills(number);
                        utils.sendMessage(sender,"&aKills of &b" + playerName + "&a now is &b" + number);
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
                        manager.addKills(number);
                        utils.sendMessage(sender,"&aAdded &b" + number + "&a kills to &b" + playerName + "&a.");
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
                        manager.setKills(manager.getKills() - number);
                        utils.sendMessage(sender,"&aRemoved &b" + number + "&a kills from &b" + playerName + "&a.");
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
        utils.sendMessage(sender,"&6Admin - Kills Commands:");
        utils.sendMessage(sender,cmdPrefix + " admin kills set (player) (kills) &e- &fSet kills of a player");
        utils.sendMessage(sender,cmdPrefix + " admin kills add (player) (kills) &e- &fAdd kills to a player");
        utils.sendMessage(sender,cmdPrefix + " admin kills remove (player) (kills) &e- &fRemove kills from a player");
        utils.sendMessage(sender,"&b------------ &aGuardian KP &b------------");
    }
    private void argumentsIssue(CommandSender sender) {
        main.getUtils().getUtils().sendMessage(sender,"&aInvalid arguments, please use &b/" + command + " admin &ato see all commands.");
    }
    private void playerIssue(CommandSender sender,String playerName) {
        main.getUtils().getUtils().sendMessage(sender,"&aInvalid player, the player &b" + playerName + "&a is not online.");
    }

}

