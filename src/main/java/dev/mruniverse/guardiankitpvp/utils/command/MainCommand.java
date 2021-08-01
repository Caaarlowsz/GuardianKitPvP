package dev.mruniverse.guardiankitpvp.utils.command;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import dev.mruniverse.guardiankitpvp.enums.SaveMode;
import dev.mruniverse.guardiankitpvp.utils.command.sub.CoinsCommand;
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

    public MainCommand(GuardianKitPvP plugin, String command) {
        this.plugin = plugin;
        this.cmdPrefix = "&e/" + command;
        coinsCommand = new CoinsCommand(plugin,command);
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
                sender.sendMessage("&bCreated by &fMrUniverse44 &bwith love &d❤");
                return true;
            }
            if (args[0].equalsIgnoreCase("admin")) {
                if(args.length == 1 || args[1].equalsIgnoreCase("1")) {
                    if(hasPermission(sender,"grftb.admin.help.game",true)) {
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
                    if(hasPermission(sender,"grftb.admin.addRotationLocation",true) && sender instanceof Player) {
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
                    if(hasPermission(sender,"grftb.admin.rotate",true)) {
                        utils.sendMessage(sender,"&aThe map has been rotated!");
                        plugin.getRotingRunnable().rotate();
                    }
                    return true;
                }

                if(args[1].equalsIgnoreCase("3")) {
                    if(hasPermission(sender,"grftb.admin.help.others",true)) {
                        sender.sendMessage(" ");
                        utils.sendMessage(sender,"&b------------ &aGuardian KP &b------------");
                        utils.sendMessage(sender,"&6Admin - Holograms Commands:");
                        utils.sendMessage(sender,cmdPrefix + " admin holo setHolo (kills-wins-stats) &e- &fSet Holo");
                        utils.sendMessage(sender,cmdPrefix + " admin holo delHolo (kills-wins-stats) &e- &fDel Holo");
                        utils.sendMessage(sender,cmdPrefix + " admin holo list &e- &fList of holograms");
                        utils.sendMessage(sender,"&6Admin - Plugin Commands:");
                        utils.sendMessage(sender,cmdPrefix + " admin reload &e- &fReload the plugin");
                        utils.sendMessage(sender,cmdPrefix + " admin setlobby &e- &fSet Main Lobby");
                        utils.sendMessage(sender,cmdPrefix + " admin modes &e- &fView all modes of the plugin");
                        utils.sendMessage(sender,"&b------------ &a(Page 3&l/4&a) &b------------");
                    }
                    return true;
                }
                if(args[1].equalsIgnoreCase("4")) {
                    if(hasPermission(sender,"grftb.admin.help.others",true)) {
                        sender.sendMessage(" ");
                        utils.sendMessage(sender,"&b------------ &aGuardian KP &b------------");
                        utils.sendMessage(sender,"&6Admin - NPC Commands:");
                        utils.sendMessage(sender,cmdPrefix + " admin npc setNPC (Mode) &e- &fSet NPC");
                        utils.sendMessage(sender,cmdPrefix + " admin npc delNPC (Mode) &e- &fDel NPC");
                        utils.sendMessage(sender,cmdPrefix + " admin npc list &e- &fList of NPCs");
                        utils.sendMessage(sender,"&6Admin - Coins Commands:");
                        utils.sendMessage(sender,cmdPrefix + " admin coins set (player) (coins) &e- &fSet coins of a player");
                        utils.sendMessage(sender,cmdPrefix + " admin coins add (player) (coins) &e- &fAdd coins to a player");
                        utils.sendMessage(sender,cmdPrefix + " admin coins remove (player) (coins) &e- &fRemove coins of a player");
                        utils.sendMessage(sender,"&b------------ &a(Page 4&l/4&a) &b------------");
                    }
                    return true;
                }

                if(args[1].equalsIgnoreCase("reload")) {
                    if(hasPermission(sender,"grftb.admin.reload",true)) {
                        plugin.getKitPvP().getFileStorage().reloadFile(SaveMode.ALL);
                        plugin.getKitPvP().getListenerController().reloadListeners();
                        utils.sendMessage(sender, "&3» &aReload completed!");
                    }
                }
            }
            return true;
        } catch (Throwable throwable) {
            plugin.getLogs().error(throwable);
        }
        return true;
    }

    @SuppressWarnings("unused")
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
