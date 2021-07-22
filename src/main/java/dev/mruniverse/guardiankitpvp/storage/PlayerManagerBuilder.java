package dev.mruniverse.guardiankitpvp.storage;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianBoard;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import dev.mruniverse.guardiankitpvp.enums.KitType;
import dev.mruniverse.guardiankitpvp.enums.PlayerStatus;
import dev.mruniverse.guardiankitpvp.interfaces.Game;
import dev.mruniverse.guardiankitpvp.interfaces.kits.KitMenu;
import dev.mruniverse.guardiankitpvp.interfaces.storage.DataStorage;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerManager;
import dev.mruniverse.guardiankitpvp.interfaces.storage.SQL;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerManagerBuilder implements PlayerManager {

    private int leaveDelay;

    private int kills = 0;

    private int wins = 0;

    private int coins = 0;

    private int deaths = 0;

    private boolean autoPlay = false;

    private GuardianKitPvP plugin;

    private Player player;

    private PlayerStatus playerStatus;

    private GuardianBoard guardianBoard;

    private Game currentGame;

    private String selectedKit;
    private String kits;

    @Override
    public PlayerManager setPlayer(Player player) {
        this.player = player;
        return this;
    }

    @Override
    public PlayerManager setPlugin(GuardianKitPvP plugin) {
        this.plugin = plugin;
        return this;
    }

    @Override
    public void finish() {
        if(player == null) return;
        leaveDelay = 0;
        guardianBoard = GuardianBoard.NORMAL;
        playerStatus = PlayerStatus.IN_LOBBY;
        currentGame = null;
        DataStorage dataStorage = plugin.getKitPvP().getDataStorage();
        if (plugin.getKitPvP().isUsingMySQL()) {
            String table = plugin.getKitPvP().getDataStorage().getTable();
            String portReceiver = plugin.getKitPvP().getDataStorage().getMySQL().getReceiverSender();
            if (!dataStorage.isRegistered(table, portReceiver, getID())) {
                dataStorage.getData().addPlayer(player.getUniqueId());
                coins = dataStorage.getData().getCoins(player.getUniqueId());
                selectedKit = dataStorage.getData().getSelectedKit(player.getUniqueId());
                kits = dataStorage.getData().getKits(player.getUniqueId());
                coins = dataStorage.getData().getCoins(player.getUniqueId());
            }
            return;
        }
        SQL sql = dataStorage.getSQL();
        if(!sql.exist(player.getUniqueId())) sql.createPlayer(player);
        coins = sql.getCoins(player.getUniqueId());
        selectedKit = sql.getSelectedKit(player.getUniqueId());
        kits = sql.getKits(player.getUniqueId());
        coins = sql.getCoins(player.getUniqueId());
    }

    @Override
    public boolean hasSelectedKit() {
        return (!selectedKit.equalsIgnoreCase("NONE"));
    }

    @Override
    public boolean getAutoPlayStatus() { return autoPlay; }

    @Override
    public boolean toggleAutoplay() {
        autoPlay = !autoPlay;
        return autoPlay;
    }

    @Override
    public KitMenu getKitMenu(KitType kitType) {
        return null;
    }

    @Override
    public void setLeaveDelay(int delay) { leaveDelay = delay; }

    @Override
    public void setStatus(PlayerStatus status) {
        playerStatus = status;
    }

    @Override
    public void setBoard(GuardianBoard board) {
        guardianBoard = board;
    }

    @Override
    public void setGame(Game game) { currentGame = game; }

    @Override
    public GuardianBoard getBoard() {
        return guardianBoard;
    }

    @Override
    public PlayerStatus getStatus() {
        return playerStatus;
    }

    @Override
    public String getName() { return getPlayer().getName(); }

    @Override
    public Game getGame() { return currentGame; }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public int getLeaveDelay() { return leaveDelay; }

    @Override
    public int getWins() {
        return wins;
    }

    @Override
    public void setWins(int wins) {
        this.wins = wins;
    }

    @Override
    public void addWins() {
        setWins(getWins() + 1);
    }

    @Override
    public void addCoins(int coins) {
        setCoins(getCoins() + coins);
    }

    @Override
    public void removeCoins(int coins) {
        setCoins(getCoins() - coins);
    }

    @Override
    public int getCoins() {
        return coins;
    }

    @Override
    public void updateCoins(int addOrRemove) {
        setCoins(getCoins() + addOrRemove);
    }

    @Override
    public void setCoins(int coinCounter) {
        coins = coinCounter;
        plugin.getKitPvP().getDataStorage().getData().setCoins(player.getUniqueId(),coinCounter);
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setSelectedKit(String kitID) {
        selectedKit = kitID;
        plugin.getKitPvP().getDataStorage().getData().setSelectedKit(player.getUniqueId(),kitID);
    }

    public void setKits(String kits) {
        this.kits = kits;
    }

    public String getSelectedKit() {
        return selectedKit;
    }

    public void addKit(String kitID) {
        if (plugin.getKitPvP().isUsingMySQL()) {
            if(!kits.equalsIgnoreCase("")) {
                plugin.getKitPvP().getDataStorage().getData().setKits(player.getUniqueId(),kits + ",K" + kitID);
                kits = kits + ",K" + kitID;
            } else {
                plugin.getKitPvP().getDataStorage().getData().setKits(player.getUniqueId(),"K" + kitID);
                kits = "K" + kitID;
            }
        }
        SQL sql = plugin.getKitPvP().getDataStorage().getSQL();
        if(sql.getKits().get(getID()) != null) {
            String lastResult = sql.getKits().get(getID());
            if(!lastResult.equalsIgnoreCase("")) {
                sql.getKits().put(getID(), lastResult + ",K" + kitID);
                kits = lastResult + ",K" + kitID;
            } else {
                sql.getKits().put(getID(), "K" + kitID);
                kits = "K" + kitID;
            }
        }
    }

    public List<String> getKits() {
        if (plugin.getKitPvP().isUsingMySQL()) {
            String[] kitShortList = kits.split(",");
            return Arrays.asList(kitShortList);
        }
        SQL sql = plugin.getKitPvP().getDataStorage().getSQL();
        if(sql.getKits().get(getID()) != null) {
            String kitsBuy = sql.getKits().get(getID());
            kitsBuy = kitsBuy.replace(" ","");
            String[] kitShortList = kitsBuy.split(",");
            return Arrays.asList(kitShortList);
        }
        return new ArrayList<>();
    }

    @SuppressWarnings("unused")
    public void addKills() {
        setKills(getKills() + 1);
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public String getID() {
        return player.getUniqueId().toString().replace("-","");
    }

    public void addDeaths() {
        setDeaths(getDeaths() + 1);
    }

    public void create() {
        FileConfiguration settings = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.SETTINGS);
        String table = settings.getString("settings.game.mysql.table");
        String defaultKit = settings.getString("settings.game.default-kits.default");
        if (!plugin.getKitPvP().getDataStorage().isRegistered(table, "Player", getID())) {
            List<String> values = new ArrayList<>();
            values.add("Player-" + getID());
            values.add("Coins-0");
            if(settings.getBoolean("settings.game.default-kits.toggle")) {

                values.add("Kits-K" + defaultKit);
            } else{
                values.add("Kits-NONE");
            }
            values.add("SelectedKit-NONE");
            plugin.getKitPvP().getDataStorage().register(table, values);
            return;
        }
        if(settings.getBoolean("settings.game.default-kits.toggle")) {
            kits = "Kits-K" + defaultKit;
        } else {
            kits = "Kits-NONE";
        }
        selectedKit = "NONE";
        coins = 0;
        plugin.getKitPvP().getDataStorage().getData().addPlayer(player.getUniqueId());
    }
}
