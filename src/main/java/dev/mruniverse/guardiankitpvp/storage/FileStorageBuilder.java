package dev.mruniverse.guardiankitpvp.storage;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import dev.mruniverse.guardiankitpvp.enums.SaveMode;
import dev.mruniverse.guardiankitpvp.interfaces.storage.FileStorage;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileStorageBuilder implements FileStorage {
    private GuardianKitPvP plugin;
    private FileConfiguration settings,messages,achievements,titles,data,menus,items,games,boards,abilities,kits,signs,sounds,holograms,ranks;
    private final File rxSettings;
    private final File rxAchievements;
    private final File rxAbilities;
    private final File rxMessages;
    private final File rxData;
    private final File rxMenus;
    private final File rxItems;
    private final File rxRanks;
    private final File rxGames;
    private final File rxSounds;
    private final File rxBoards;
    private final File rxTitles;
    private final File rxHolograms;
    private final File rxSigns;
    private final File rxKits;
    public FileStorageBuilder(GuardianKitPvP plugin) {
        this.plugin = plugin;
        File dataFolder = plugin.getDataFolder();
        rxRanks = new File(dataFolder,"ranks.yml");
        rxSettings = new File(dataFolder, "settings.yml");
        rxTitles = new File(dataFolder, "titles.yml");
        rxAchievements = new File(dataFolder, "achievements.yml");
        rxAbilities = new File(dataFolder, "abilities.yml");
        rxMessages = new File(dataFolder, "messages.yml");
        rxSigns = new File(dataFolder, "signs.yml");
        rxData = new File(dataFolder, "data.yml");
        rxMenus = new File(dataFolder, "menus.yml");
        rxHolograms = new File(dataFolder,"holograms.yml");
        rxItems = new File(dataFolder, "items.yml");
        rxGames = new File(dataFolder, "games.yml");
        rxBoards = new File(dataFolder, "scoreboards.yml");
        rxKits = new File(dataFolder, "kits.yml");
        rxSounds = new File(dataFolder,"sounds.yml");
        settings = loadConfig("settings");
        ranks = loadConfig("ranks");
        menus = loadConfig("menus");
        titles = loadConfig("titles");
        holograms = loadConfig("holograms");
        messages = loadConfig("messages");
        achievements = loadConfig("achievements");
        abilities = loadConfig("abilities");
        items = loadConfig("items");
        signs = loadConfig("signs");
        data = loadConfig("data");
        games = loadConfig("games");
        boards = loadConfig("scoreboards");
        sounds = loadConfig("sounds");
        kits = loadConfig("kits");

    }

    @Override
    public void setPlugin(GuardianKitPvP plugin) {
        this.plugin = plugin;
    }

    @Override
    public File getFile(GuardianFiles fileToGet) {
        switch (fileToGet) {
            case RANKS:
                return rxRanks;
            case MESSAGES:
                return rxMessages;
            case HOLOGRAMS:
                return rxHolograms;
            case GAMES:
                return rxGames;
            case SOUNDS:
                return rxSounds;
            case DATA:
                return rxData;
            case KITS:
                return rxKits;
            case ITEMS:
                return rxItems;
            case MENUS:
                return rxMenus;
            case SIGNS:
                return rxSigns;
            case TITLES:
                return rxTitles;
            case ABILITIES:
                return rxAbilities;
            case SCOREBOARD:
                return rxBoards;
            case ACHIEVEMENTS:
                return rxAchievements;
            case SETTINGS:
            default:
                return rxSettings;
        }
    }

    /**
     * Creates a config File if it doesn't exists,
     * reloads if specified file exists.
     *
     * @param configName config to create/reload.
     */
    @Override
    public FileConfiguration loadConfig(String configName) {
        File configFile = new File(plugin.getDataFolder(), configName + ".yml");

        if (!configFile.exists()) {
            saveConfig(configName);
        }

        FileConfiguration cnf = null;
        try {
            cnf = YamlConfiguration.loadConfiguration(configFile);
        } catch (Exception e) {
            plugin.getLogs().warn(String.format("A error occurred while loading the settings file. Error: %s", e));
            e.printStackTrace();
        }

        plugin.getLogs().info(String.format("&7File &e%s.yml &7has been loaded", configName));
        return cnf;
    }
    /**
     * Creates a config File if it doesn't exists,
     * reloads if specified file exists.
     *
     * @param rigoxFile config to create/reload.
     */
    @Override
    public FileConfiguration loadConfig(File rigoxFile) {
        if (!rigoxFile.exists()) {
            saveConfig(rigoxFile);
        }

        FileConfiguration cnf = null;
        try {
            cnf = YamlConfiguration.loadConfiguration(rigoxFile);
        } catch (Exception e) {
            plugin.getLogs().warn(String.format("A error occurred while loading the settings file. Error: %s", e));
            e.printStackTrace();
        }

        plugin.getLogs().info(String.format("&7File &e%s &7has been loaded", rigoxFile.getName()));
        return cnf;
    }

    /**
     * Reload plugin file(s).
     *
     * @param Mode mode of reload.
     */
    @Override
    public void reloadFile(SaveMode Mode) {
        switch (Mode) {
            case RANKS:
                ranks = YamlConfiguration.loadConfiguration(rxRanks);
            case SETTINGS:
                settings = YamlConfiguration.loadConfiguration(rxSettings);
                break;
            case SCOREBOARDS:
                boards = YamlConfiguration.loadConfiguration(rxBoards);
                break;
            case GAMES_FILES:
                games = YamlConfiguration.loadConfiguration(rxGames);
                break;
            case ALL:
            default:
                messages = YamlConfiguration.loadConfiguration(rxMessages);
                holograms = YamlConfiguration.loadConfiguration(rxHolograms);
                data = YamlConfiguration.loadConfiguration(rxData);
                items = YamlConfiguration.loadConfiguration(rxItems);
                kits = YamlConfiguration.loadConfiguration(rxKits);
                menus = YamlConfiguration.loadConfiguration(rxMenus);
                sounds = YamlConfiguration.loadConfiguration(rxSounds);
                settings = YamlConfiguration.loadConfiguration(rxSettings);
                boards = YamlConfiguration.loadConfiguration(rxBoards);
                games = YamlConfiguration.loadConfiguration(rxGames);
                break;
        }
    }

    /**
     * Save config File using FileStorageImpl
     *
     * @param fileToSave config to save/create with saveMode.
     */
    @Override
    public void save(SaveMode fileToSave) {
        try {
            switch (fileToSave) {
                case RANKS:
                    getControl(GuardianFiles.RANKS).save(rxRanks);
                case ACHIEVEMENTS:
                    getControl(GuardianFiles.ACHIEVEMENTS).save(rxAchievements);
                    break;
                case ABILITIES:
                    getControl(GuardianFiles.ABILITIES).save(rxAbilities);
                case TITLES:
                    getControl(GuardianFiles.TITLES).save(rxTitles);
                case SIGNS:
                    getControl(GuardianFiles.SIGNS).save(rxSigns);
                case HOLOGRAMS:
                    getControl(GuardianFiles.HOLOGRAMS).save(rxHolograms);
                    break;
                case SOUNDS:
                    getControl(GuardianFiles.SOUNDS).save(rxSounds);
                    break;
                case ITEMS:
                    getControl(GuardianFiles.ITEMS).save(rxItems);
                    break;
                case DATA:
                    getControl(GuardianFiles.DATA).save(rxData);
                    break;
                case KITS:
                    getControl(GuardianFiles.KITS).save(rxKits);
                    break;
                case GAMES_FILES:
                    getControl(GuardianFiles.GAMES).save(rxGames);
                    break;
                case MENUS:
                    getControl(GuardianFiles.MENUS).save(rxMenus);
                    break;
                case SCOREBOARDS:
                    getControl(GuardianFiles.SCOREBOARD).save(rxBoards);
                    break;
                case MESSAGES:
                    getControl(GuardianFiles.MESSAGES).save(rxMessages);
                    break;
                case SETTINGS:
                    getControl(GuardianFiles.SETTINGS).save(rxSettings);
                    break;
                case ALL:
                default:
                    getControl(GuardianFiles.RANKS).save(rxRanks);
                    getControl(GuardianFiles.SIGNS).save(rxSigns);
                    getControl(GuardianFiles.TITLES).save(rxTitles);
                    getControl(GuardianFiles.ABILITIES).save(rxAbilities);
                    getControl(GuardianFiles.ACHIEVEMENTS).save(rxAchievements);
                    getControl(GuardianFiles.HOLOGRAMS).save(rxHolograms);
                    getControl(GuardianFiles.SETTINGS).save(rxSettings);
                    getControl(GuardianFiles.DATA).save(rxData);
                    getControl(GuardianFiles.KITS).save(rxKits);
                    getControl(GuardianFiles.GAMES).save(rxGames);
                    getControl(GuardianFiles.SOUNDS).save(rxSounds);
                    getControl(GuardianFiles.SCOREBOARD).save(rxBoards);
                    getControl(GuardianFiles.ITEMS).save(rxItems);
                    getControl(GuardianFiles.MENUS).save(rxMenus);
                    getControl(GuardianFiles.MESSAGES).save(rxMessages);
                    break;
            }
        } catch (Throwable throwable) {
            plugin.getLogs().error("Can't save a file!");

        }
    }
    /**
     * Save config File Changes & Paths
     *
     * @param configName config to save/create.
     */
    @Override
    public void saveConfig(String configName) {
        File folderDir = plugin.getDataFolder();
        File file = new File(plugin.getDataFolder(), configName + ".yml");
        if (!folderDir.exists()) {
            boolean createFile = folderDir.mkdir();
            if(createFile) plugin.getLogs().info("&7Folder created!");
        }

        if (!file.exists()) {
            try (InputStream in = plugin.getResource(configName + ".yml")) {
                if(in != null) {
                    Files.copy(in, file.toPath());
                }
            } catch (Throwable throwable) {
                plugin.getLogs().error(String.format("A error occurred while copying the config %s to the plugin data folder. Error: %s", configName, throwable));
                plugin.getLogs().error(throwable);
            }
        }
    }
    /**
     * Save config File Changes & Paths
     *
     * @param fileToSave config to save/create.
     */
    @Override
    public void saveConfig(File fileToSave) {
        if (!fileToSave.getParentFile().exists()) {
            boolean createFile = fileToSave.mkdir();
            if(createFile) plugin.getLogs().info("&7Folder created!!");
        }

        if (!fileToSave.exists()) {
            plugin.getLogs().debug(fileToSave.getName());
            try (InputStream in = plugin.getResource(fileToSave.getName() + ".yml")) {
                if(in != null) {
                    Files.copy(in, fileToSave.toPath());
                }
            } catch (Throwable throwable) {
                plugin.getLogs().error(String.format("A error occurred while copying the config %s to the plugin data folder. Error: %s", fileToSave.getName(), throwable));
                plugin.getLogs().error(throwable);
            }
        }
    }

    /**
     * Control a file, getControl() will return a FileConfiguration.
     *
     * @param fileToControl config to control.
     */
    @Override
    public FileConfiguration getControl(GuardianFiles fileToControl) {
        switch (fileToControl) {
            case RANKS:
                if(ranks == null) ranks = loadConfig(rxRanks);
                return ranks;
            case ACHIEVEMENTS:
                if(achievements == null) achievements = loadConfig(rxAchievements);
                return achievements;
            case ABILITIES:
                if(abilities == null) abilities = loadConfig(rxAbilities);
                return abilities;
            case TITLES:
                if(titles == null) titles = loadConfig(rxTitles);
                return titles;
            case SIGNS:
                if(signs == null) signs = loadConfig(rxSigns);
                return signs;
            case HOLOGRAMS:
                if(holograms == null) holograms = loadConfig(rxHolograms);
                return holograms;
            case SOUNDS:
                if(sounds == null) sounds = loadConfig(rxSounds);
                return sounds;
            case ITEMS:
                if(items == null) items = loadConfig(rxItems);
                return items;
            case DATA:
                if(data == null) data = loadConfig(rxData);
                return data;
            case KITS:
                if(kits == null) kits = loadConfig(rxKits);
                return kits;
            case GAMES:
                if(games == null) games = loadConfig(rxGames);
                return games;
            case MENUS:
                if(menus == null) menus = loadConfig(rxMenus);
                return menus;
            case SCOREBOARD:
                if(boards == null) boards = loadConfig(rxBoards);
                return boards;
            case MESSAGES:
                if(messages == null) messages = loadConfig(rxMessages);
                return messages;
            case SETTINGS:
            default:
                if(settings == null) settings = loadConfig(rxSettings);
                return settings;
        }
    }

    @Override
    public List<String> getContent(GuardianFiles file, String path, boolean getKeys) {
        List<String> rx = new ArrayList<>();
        ConfigurationSection section = getControl(file).getConfigurationSection(path);
        if(section == null) return rx;
        rx.addAll(section.getKeys(getKeys));
        return rx;
    }
}
