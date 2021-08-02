package dev.mruniverse.guardiankitpvp.storage;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianBoard;
import dev.mruniverse.guardiankitpvp.enums.KitType;
import dev.mruniverse.guardiankitpvp.enums.PlayerStatus;
import dev.mruniverse.guardiankitpvp.interfaces.Game;
import dev.mruniverse.guardiankitpvp.interfaces.kits.KitMenu;
import dev.mruniverse.guardiankitpvp.interfaces.rank.Rank;
import dev.mruniverse.guardiankitpvp.interfaces.rank.RankManager;
import dev.mruniverse.guardiankitpvp.interfaces.storage.DataStorage;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings({"FieldCanBeLocal","unused"})
public class PlayerManagerBuilder implements PlayerManager {

    private final HashMap<String,Long> abilityCountdown = new HashMap<>();

    private BukkitRunnable abilityCountdownPB = null;

    private int leaveDelay = 0;

    private int kills = 0;

    private int wins = 0;

    private int coins = 0;

    private int deaths = 0;

    private int kitUnlockers = 0;

    private int dataExp = 0;

    private int projectiles_hit = 0;

    private int tournament_wins = 0;

    private int challenge_wins = 0;

    private int abilities_used = 0;

    private int soups_eaten = 0;

    private int killstreaks_earned = 0;

    private int killStreak = 0;

    private boolean autoPlay = false;

    private boolean disableChat = false;

    private Rank currentRank = null;

    private Rank nextRank = null;

    private GuardianKitPvP plugin;

    private Player player;

    private PlayerStatus playerStatus = PlayerStatus.IN_LOBBY;

    private GuardianBoard guardianBoard;

    private Game currentGame = null;

    private String selectedKit = "";

    private String kits = "";

    private String lastAbilityKey = "";


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
    public void setKs(int killStreak) {
        this.killStreak = killStreak;
    }


    @Override
    public int getKs() {
        return killStreak;
    }

    @Override
    public void setStatsFromString(String paramString) {
        plugin.getLogs().debug(paramString);
        String[] arrayString = paramString.split(":");
        this.kills = Integer.parseInt(arrayString[0]);
        this.deaths = Integer.parseInt(arrayString[1]);
        this.coins = Integer.parseInt(arrayString[2]);
        this.kitUnlockers = Integer.parseInt(arrayString[3]);
        this.dataExp = Integer.parseInt(arrayString[4]);
        this.projectiles_hit = Integer.parseInt(arrayString[5]);
        this.tournament_wins = Integer.parseInt(arrayString[6]);
        this.challenge_wins = Integer.parseInt(arrayString[7]);
        this.abilities_used = Integer.parseInt(arrayString[8]);
        this.soups_eaten = Integer.parseInt(arrayString[9]);
        this.killstreaks_earned = Integer.parseInt(arrayString[10]);
        updateRank();
    }

    @Override
    public int getXP() {
        return dataExp;
    }

    @Override
    public void updateRank() {
        RankManager rankManager = plugin.getKitPvP().getRankManager();
        for (byte b = 0; b < rankManager.getRanks().size(); b++) {
            Rank rank = rankManager.getRanks().get(b);
            if (this.dataExp >= rank.getRequiredExp()) {
                this.currentRank = rank;
                this.nextRank = (rankManager.getRanks().size() <= b + 1) ? null : rankManager.getRanks().get(b + 1);
            }
        }
    }

    @Override
    public void resetPlayer() {
        this.coins = 0;
        this.kills = 0;
        this.deaths = 0;
        this.dataExp = 0;
        this.projectiles_hit = 0;
        this.tournament_wins = 0;
        this.challenge_wins = 0;
        this.abilities_used = 0;
        this.soups_eaten = 0;
        this.killstreaks_earned = 0;
        updateRank();
    }

    @Override
    public Rank getRank() {
        return currentRank;
    }

    @Override
    public Rank getNextRank() {
        return nextRank;
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
            plugin.getKitPvP().getDataStorage().getMySQL().loadStats(player);
        }
        plugin.getKitPvP().getDataStorage().getSQL().loadStats(player);
    }

    @Override
    public String getKitsString() {
        return kits;
    }

    @Override
    public String getStatsString() {
        return kills + ":" + deaths + ":" + coins + ":" + kitUnlockers + ":" + dataExp + ":" + projectiles_hit + ":" + tournament_wins + ":" + challenge_wins + ":" + abilities_used + ":" + soups_eaten + ":" + killstreaks_earned;
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
    public boolean toggleChat() {
        disableChat = !disableChat;
        return disableChat;
    }

    @Override
    public boolean isDisableChat() {
        return disableChat;
    }

    @Override
    public void clearCountdowns() {
        abilityCountdown.clear();
        if(abilityCountdownPB != null) {
            abilityCountdownPB.cancel();
            abilityCountdownPB = null;
        }

    }

    @Override
    public String getProgressBar(int paramInt) {
        int checkInt = (int)(paramInt * 0.20);
        if (paramInt >= 100)
            return ChatColor.translateAlternateColorCodes('&', plugin.getColorComplete() + plugin.getProgressBar());
        if (paramInt < 1)
            return ChatColor.translateAlternateColorCodes('&', plugin.getColorPending() + plugin.getProgressBar());
        return getBar(plugin.getColorComplete() + plugin.getProgressBar().substring(0, paramInt) + plugin.getColorPending() + plugin.getProgressBar().substring(paramInt));
    }

    @Override
    public boolean isCountdown(final String countdownName) {
        long l = this.abilityCountdown.containsKey(countdownName) ? (this.abilityCountdown.get(countdownName) - System.currentTimeMillis()) : 0L;
        if (l > 0L) {
            plugin.getUtils().getUtils().sendTitle(player,20,20,20,null,"&8⚫⚫⚫⚫⚫⚫⚫⚫⚫");
            return true;
        }
        return false;
    }

    @Override
    public void setCountdown(final String countdownName,final int seconds,final boolean progressBar) {
        int math = seconds * 1000;
        abilityCountdown.put(countdownName,System.currentTimeMillis() + math);
        lastAbilityKey = countdownName;
        if(abilityCountdownPB != null) {
            abilityCountdownPB.cancel();
        }
        if(progressBar) {
            abilityCountdownPB = new BukkitRunnable() {
                private final int max = seconds;
                private final String ability =countdownName;
                private int count = 0;
                @Override
                public void run() {
                    count++;
                    int remaining = max - count;
                    double percentage = (double)remaining / max;
                    int value = (int)percentage * 100;
                    plugin.getUtils().getUtils().sendActionbar(player,getProgressBar(value));
                    if(count == max) {
                        cancel();
                        abilityCountdown.remove(ability);
                        abilityCountdownPB = null;
                    }
                }
            };
        }

    }

    @Override
    public String getBar(String bar) {
        return ChatColor.translateAlternateColorCodes('&',plugin.getLeftPB() + bar + plugin.getRightPB());
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
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setSelectedKit(String kitID) {
        selectedKit = kitID;
    }

    public void setKits(String kits) {
        this.kits = kits;
    }

    public String getSelectedKit() {
        return selectedKit;
    }

    public List<String> getKits() {
        String[] kitShortList = kits.split(",");
        return Arrays.asList(kitShortList);
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

}
