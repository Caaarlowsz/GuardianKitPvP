package dev.mruniverse.guardiankitpvp.utils.command;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import dev.mruniverse.guardiankitpvp.enums.SaveMode;
import dev.mruniverse.guardiankitpvp.utils.command.sub.*;
import dev.mruniverse.guardianlib.core.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class MainCommand implements CommandExecutor {
    private final GuardianKitPvP plugin;
    private final String cmdPrefix;

    private final CoinsCommand coinsCommand;
    private final DeathsCommand deathsCommand;
    private final ExpCommand expCommand;
    private final KillsCommand killsCommand;
    private final BowHitsCommand bowHitsCommand;

    public MainCommand(GuardianKitPvP plugin, String command) {
        this.plugin = plugin;
        this.cmdPrefix = "&e/" + command;

        coinsCommand = new CoinsCommand(plugin,command);
        killsCommand = new KillsCommand(plugin,command);
        expCommand = new ExpCommand(plugin,command);
        deathsCommand = new DeathsCommand(plugin,command);
        bowHitsCommand = new BowHitsCommand(plugin,command);

    }

    @SuppressWarnings("SameParameterValue")
    private boolean hasPermission(CommandSender sender, String permission, boolean sendMessage) {
        boolean check = true;
        if(sender instanceof Player) {
            Player player = (Player)sender;
            check = player.hasPermission(permission);
            if(sendMessage) {
                String permissionMsg = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.MESSAGES).getString("messages.no-permission");
                if (permissionMsg == null) permissionMsg = "&cYou need permission &7%permission% &cfor this action.";
                if (!check)
                    plugin.getUtils().getUtils().sendMessage(player, permissionMsg.replace("%permission%", permission));
            }
        }
        return check;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        try {
            Utils utils = plugin.getUtils().getUtils();
            if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
                utils.sendMessage(sender,"&bCreated by &fMrUniverse44 &bwith love &d❤");
                return true;
            }
            if (args[0].equalsIgnoreCase("admin")) {
                if(args.length == 1 || args[1].equalsIgnoreCase("1")) {
                    if(hasPermission(sender,"gkb.admin.help",true)) {
                        sender.sendMessage(" ");
                        utils.sendMessage(sender,"&b------------ &aGuardian KP &b------------");
                        utils.sendMessage(sender,"&6Admin - Game Commands Page#1:");
                        utils.sendMessage(sender,cmdPrefix + " admin addRotationLocation &e- &fAdd Map Rotation Location");
                        utils.sendMessage(sender,cmdPrefix + " admin rotate &e- &fRotate Map Location");
                        utils.sendMessage(sender,cmdPrefix + " admin reload &e- &fAdd Map Rotation Location");
                        utils.sendMessage(sender,"&b------------ &a(Page 1&l/4&a) &b------------");
                    }
                    return true;
                }
                if(args[1].equalsIgnoreCase("addRotationLocation")) {
                    if(hasPermission(sender,"gkb.admin.addRotationLocation",true) && sender instanceof Player) {
                        Player player = (Player)sender;
                        FileConfiguration file = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.SETTINGS);
                        String location = plugin.getUtils().getUtils().getStringFromLocation(player.getLocation());
                        if (file.get("settings.map-locations") != null) {
                            if (file.getStringList("settings.map-locations").contains(location)) {
                                utils.sendMessage(sender, "&ccThis location is already added!");
                                return true;
                            }
                            List<String> locations = file.getStringList("settings.map-locations");
                            locations.add(location);
                            utils.sendMessage(sender, "&aRotation Location Added.");
                            plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.SETTINGS).set("settings.map-locations", locations);
                            plugin.getKitPvP().getFileStorage().save(SaveMode.SETTINGS);
                            return true;
                        }
                        List<String> locations = new ArrayList<>();
                        locations.add(location);
                        utils.sendMessage(sender, "&aRotation Location Added.");
                        plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.SETTINGS).set("settings.map-locations", locations);
                        plugin.getKitPvP().getFileStorage().save(SaveMode.SETTINGS);
                        return true;
                    }
                    return true;
                }
                if(args[1].equalsIgnoreCase("rotate")) {
                    if(hasPermission(sender,"gkb.rotate",true)) {
                        utils.sendMessage(sender,"&aThe map has been rotated!");
                        plugin.getRotingRunnable().rotate();
                    }
                    return true;
                }

                if(args[1].equalsIgnoreCase("2")) {
                    if(hasPermission(sender,"gkb.admin.help",true)) {
                        sender.sendMessage(" ");
                        utils.sendMessage(sender,"&b------------ &aGuardian KP &b------------");
                        utils.sendMessage(sender,"&6Admin - Bow Hits Commands:");
                        utils.sendMessage(sender,cmdPrefix + " admin bh set (player) (coins) &e- &fSet bow hits of a player");
                        utils.sendMessage(sender,cmdPrefix + " admin bh add (player) (coins) &e- &fAdd bow hits to a player");
                        utils.sendMessage(sender,cmdPrefix + " admin bh remove (player) (coins) &e- &fRemove bow hits from a player");
                        utils.sendMessage(sender,"&6Admin - Plugin Commands:");
                        utils.sendMessage(sender,cmdPrefix + " admin reload &e- &fReload the plugin");
                        utils.sendMessage(sender,cmdPrefix + " admin setlobby &e- &fSet Main Lobby");
                        utils.sendMessage(sender,cmdPrefix + " admin modes &e- &fView all modes of the plugin");
                        utils.sendMessage(sender,"&b------------ &a(Page 2&l/4&a) &b------------");
                    }
                    return true;
                }
                if(args[1].equalsIgnoreCase("3")) {
                    if(hasPermission(sender,"gkb.admin.help",true)) {
                        sender.sendMessage(" ");
                        utils.sendMessage(sender,"&b------------ &aGuardian KP &b------------");
                        utils.sendMessage(sender,"&6Admin - Coins Commands:");
                        utils.sendMessage(sender,cmdPrefix + " admin coins set (player) (coins) &e- &fSet coins of a player");
                        utils.sendMessage(sender,cmdPrefix + " admin coins add (player) (coins) &e- &fAdd coins to a player");
                        utils.sendMessage(sender,cmdPrefix + " admin coins remove (player) (coins) &e- &fRemove coins of a player");
                        utils.sendMessage(sender,"&6Admin - Deaths Commands:");
                        utils.sendMessage(sender,cmdPrefix + " admin deaths set (player) (deaths) &e- &fSet deaths of a player");
                        utils.sendMessage(sender,cmdPrefix + " admin deaths add (player) (deaths) &e- &fAdd deaths to a player");
                        utils.sendMessage(sender,cmdPrefix + " admin deaths remove (player) (deaths) &e- &fRemove deaths from a player");
                        utils.sendMessage(sender,"&b------------ &a(Page 3&l/4&a) &b------------");
                    }
                    return true;
                }

                if(args[1].equalsIgnoreCase("4")) {
                    if(hasPermission(sender,"gkb.admin.help",true)) {
                        sender.sendMessage(" ");
                        utils.sendMessage(sender,"&b------------ &aGuardian KP &b------------");
                        utils.sendMessage(sender,"&6Admin - Exp Commands:");
                        utils.sendMessage(sender,cmdPrefix + " admin exp set (player) (exp) &e- &fSet exp of a player");
                        utils.sendMessage(sender,cmdPrefix + " admin exp add (player) (exp) &e- &fAdd exp to a player");
                        utils.sendMessage(sender,cmdPrefix + " admin exp remove (player) (exp) &e- &fRemove exp from a player");
                        utils.sendMessage(sender,"&6Admin - Kills Commands:");
                        utils.sendMessage(sender,cmdPrefix + " admin kills set (player) (kills) &e- &fSet kills of a player");
                        utils.sendMessage(sender,cmdPrefix + " admin kills add (player) (kills) &e- &fAdd kills to a player");
                        utils.sendMessage(sender,cmdPrefix + " admin kills remove (player) (kills) &e- &fRemove kills from a player");
                        utils.sendMessage(sender,"&b------------ &a(Page 3&l/4&a) &b------------");
                    }
                    return true;
                }

                if(args[1].equalsIgnoreCase("reload")) {
                    if(hasPermission(sender,"gkb.admin.reload",true)) {
                        plugin.getKitPvP().getFileStorage().reloadFile(SaveMode.ALL);
                        plugin.getKitPvP().getListenerController().reloadListeners();
                        utils.sendMessage(sender, "&3» &aReload completed!");
                    }
                }

                if(args[1].equalsIgnoreCase("coins") && args.length >= 4) {
                    if(hasPermission(sender,"gkb.admin.coins",true)) {
                        coinsCommand.usage(sender,getArguments(args));

                    }
                    return true;
                }

                if(args[1].equalsIgnoreCase("deaths") && args.length >= 4) {
                    if(hasPermission(sender,"gkb.admin.deaths",true)) {
                        deathsCommand.usage(sender,getArguments(args));

                    }
                    return true;
                }

                if(args[1].equalsIgnoreCase("exp") && args.length >= 4) {
                    if(hasPermission(sender,"gkb.admin.exp",true)) {
                        expCommand.usage(sender,getArguments(args));

                    }
                    return true;
                }

                if(args[1].equalsIgnoreCase("kills") && args.length >= 4) {
                    if(hasPermission(sender,"gkb.admin.kills",true)) {
                        killsCommand.usage(sender,getArguments(args));

                    }
                    return true;
                }

                if(args[1].equalsIgnoreCase("bh") && args.length >= 4) {
                    if(hasPermission(sender,"gkb.admin.bh",true)) {
                        bowHitsCommand.usage(sender,getArguments(args));

                    }
                    return true;
                }

            }
            return true;
        } catch (Throwable throwable) {
            plugin.getLogs().error(throwable);
        }
        return true;
    }


    //this.coins = 0;
    //        this.kills = 0;
    //        this.deaths = 0;
    //        this.dataExp = 0;
    //        this.projectiles_hit = 0;
    //        this.tournament_wins = 0;
    //        this.challenge_wins = 0;
    //        this.abilities_used = 0;
    //        this.soups_eaten = 0;
    //        this.killstreaks_earned = 0;

    private String[] getArguments(String[] args){
        String[] arguments = new String[args.length - 2];
        int argID = 0;
        int aID = 0;
        for(String arg : args) {
            if(aID != 0 && aID != 1) {
                arguments[argID] = arg;
                argID++;
            }
            aID++;
        }
        return arguments;
    }

}
