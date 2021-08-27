package dev.mruniverse.guardiankitpvp.utils.command.sub;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import dev.mruniverse.guardiankitpvp.enums.GuardianHolograms;
import dev.mruniverse.guardiankitpvp.enums.HoloPath;
import dev.mruniverse.guardiankitpvp.enums.SaveMode;
import dev.mruniverse.guardianlib.core.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class HologramsCommand {
    private final GuardianKitPvP main;
    private final String command;

    public HologramsCommand(GuardianKitPvP kitPvP, String command) {
        this.command = command;
        main = kitPvP;
    }
    //utils.sendMessage(sender,cmdPrefix + " admin holograms add (ks-xp-d-k-w-c) &e- &fLocations");

    public void usage(CommandSender sender, String[] arguments) {
        Utils utils = main.getUtils().getUtils();
        if(!(sender instanceof Player)) {
            utils.sendMessage(sender,"&cThis command is only for players");
            return;
        }
        Player player = (Player)sender;
        if (arguments[0].equalsIgnoreCase("add")) {
            if (arguments.length == 2) {
                if(arguments[1].equalsIgnoreCase("ks")) {
                    GuardianHolograms holo = GuardianHolograms.TOP_KILL_STREAK;
                    List<String> locations = main.getKitPvP().getFileStorage().getControl(GuardianFiles.HOLOGRAMS).getStringList(holo.getPath(HoloPath.LOCATIONS));
                    locations.add(utils.getStringFromLocation(player.getLocation()));
                    main.getKitPvP().getFileStorage().getControl(GuardianFiles.HOLOGRAMS).set(holo.getPath(HoloPath.LOCATIONS),locations);
                    main.getKitPvP().getFileStorage().save(SaveMode.HOLOGRAMS);
                    utils.sendMessage(player,"&aThis location has been added to hologram: &bKillStreak");
                    return;
                }
                if(arguments[1].equalsIgnoreCase("xp")) {
                    GuardianHolograms holo = GuardianHolograms.TOP_EXPERIENCE;
                    List<String> locations = main.getKitPvP().getFileStorage().getControl(GuardianFiles.HOLOGRAMS).getStringList(holo.getPath(HoloPath.LOCATIONS));
                    locations.add(utils.getStringFromLocation(player.getLocation()));
                    main.getKitPvP().getFileStorage().getControl(GuardianFiles.HOLOGRAMS).set(holo.getPath(HoloPath.LOCATIONS),locations);
                    main.getKitPvP().getFileStorage().save(SaveMode.HOLOGRAMS);
                    utils.sendMessage(player,"&aThis location has been added to hologram: &bExperience");
                    return;
                }
                if(arguments[1].equalsIgnoreCase("s")) {
                    GuardianHolograms holo = GuardianHolograms.STATICS;
                    List<String> locations = main.getKitPvP().getFileStorage().getControl(GuardianFiles.HOLOGRAMS).getStringList(holo.getPath(HoloPath.LOCATIONS));
                    locations.add(utils.getStringFromLocation(player.getLocation()));
                    main.getKitPvP().getFileStorage().getControl(GuardianFiles.HOLOGRAMS).set(holo.getPath(HoloPath.LOCATIONS),locations);
                    main.getKitPvP().getFileStorage().save(SaveMode.HOLOGRAMS);
                    utils.sendMessage(player,"&aThis location has been added to hologram: &bStatics");
                    return;
                }
                if(arguments[1].equalsIgnoreCase("d")) {
                    GuardianHolograms holo = GuardianHolograms.TOP_DEATHS;
                    List<String> locations = main.getKitPvP().getFileStorage().getControl(GuardianFiles.HOLOGRAMS).getStringList(holo.getPath(HoloPath.LOCATIONS));
                    locations.add(utils.getStringFromLocation(player.getLocation()));
                    main.getKitPvP().getFileStorage().getControl(GuardianFiles.HOLOGRAMS).set(holo.getPath(HoloPath.LOCATIONS),locations);
                    main.getKitPvP().getFileStorage().save(SaveMode.HOLOGRAMS);
                    utils.sendMessage(player,"&aThis location has been added to hologram: &bDeaths");
                    return;
                }
                if(arguments[1].equalsIgnoreCase("k")) {
                    GuardianHolograms holo = GuardianHolograms.TOP_KILLS;
                    List<String> locations = main.getKitPvP().getFileStorage().getControl(GuardianFiles.HOLOGRAMS).getStringList(holo.getPath(HoloPath.LOCATIONS));
                    locations.add(utils.getStringFromLocation(player.getLocation()));
                    main.getKitPvP().getFileStorage().getControl(GuardianFiles.HOLOGRAMS).set(holo.getPath(HoloPath.LOCATIONS),locations);
                    main.getKitPvP().getFileStorage().save(SaveMode.HOLOGRAMS);
                    utils.sendMessage(player,"&aThis location has been added to hologram: &bKills");
                    return;
                }
                if(arguments[1].equalsIgnoreCase("w")) {
                    GuardianHolograms holo = GuardianHolograms.TOP_WINS;
                    List<String> locations = main.getKitPvP().getFileStorage().getControl(GuardianFiles.HOLOGRAMS).getStringList(holo.getPath(HoloPath.LOCATIONS));
                    locations.add(utils.getStringFromLocation(player.getLocation()));
                    main.getKitPvP().getFileStorage().getControl(GuardianFiles.HOLOGRAMS).set(holo.getPath(HoloPath.LOCATIONS),locations);
                    main.getKitPvP().getFileStorage().save(SaveMode.HOLOGRAMS);
                    utils.sendMessage(player,"&aThis location has been added to hologram: &bWins");
                    return;
                }
                if(arguments[1].equalsIgnoreCase("c")) {
                    GuardianHolograms holo = GuardianHolograms.TOP_COINS;
                    List<String> locations = main.getKitPvP().getFileStorage().getControl(GuardianFiles.HOLOGRAMS).getStringList(holo.getPath(HoloPath.LOCATIONS));
                    locations.add(utils.getStringFromLocation(player.getLocation()));
                    main.getKitPvP().getFileStorage().getControl(GuardianFiles.HOLOGRAMS).set(holo.getPath(HoloPath.LOCATIONS),locations);
                    main.getKitPvP().getFileStorage().save(SaveMode.HOLOGRAMS);
                    utils.sendMessage(player,"&aThis location has been added to hologram: &bCoins");
                    return;
                }
                argumentsIssue(sender);
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
        utils.sendMessage(sender,"&6Admin - Holograms Commands:");
        utils.sendMessage(sender,cmdPrefix + " admin holograms add (ks-xp-s-d-k-w-c) &e- &fLocations");
        utils.sendMessage(sender,"&b------------ &aGuardian KP &b------------");
    }
    private void argumentsIssue(CommandSender sender) {
        main.getUtils().getUtils().sendMessage(sender,"&aInvalid arguments, please use &b/" + command + " admin &ato see all commands.");
    }
}
