package dev.mruniverse.guardiankitpvp.storage;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import dev.mruniverse.guardiankitpvp.interfaces.storage.MySQL;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;

public class MySQLBuilder implements MySQL {
    private GuardianKitPvP plugin;


    //settings:
    //    //  mysql:
    //    //    toggle: false
    //    //    jdbc-url: 'jdbc:mysql://[host]:[port]/[db]?autoReconnect=true'
    //    //    table-prefix: 'guardiankitpvp_'
    //    //    host: 'localhost'
    //    //    port: '3306'
    //    //    database: 'database'
    //    //    username: 'root'
    //    //    password: 'root'

    private String MYSQL_PORT_RECEIVER = "player_name";
    private String TABLE_PREFIX = "";

    private boolean isUUID = false;


    public MySQLBuilder(GuardianKitPvP main) {
        plugin = main;
    }
    public Connection con;

    @Override
    public void setPlugin(GuardianKitPvP plugin) {
        this.plugin = plugin;
    }

    @Override
    public void connect(String host, String db, String user, String password) {
        try {
            FileConfiguration settings = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.SETTINGS);
            String url= settings.getString("settings.mysql.jdbc-url");
            boolean porterReceiver = settings.getBoolean("settings.game.user-uuid-on-data",false);
            if(porterReceiver) {
                MYSQL_PORT_RECEIVER = "player_uuid";
                isUUID = true;
            }

            this.TABLE_PREFIX = settings.getString("settings.mysql.table-prefix","guardiankitpvp_");

            int port = settings.getInt("settings.mysql.port",3306);
            if(url == null) url = "jdbc:mysql://" + host + ":" + settings.getInt("settings.mysql.port") + "/" + db + "?autoReconnect=true";
            url = url.replace("[host]",host)
                    .replace("[port]",port + "")
                    .replace("[db]",db);
            plugin.getLogs().info("");
            plugin.getLogs().info("--------------------");
            plugin.getLogs().info("Trying to connect to database with jdbc:");
            plugin.getLogs().info(url);
            plugin.getLogs().info("User:");
            plugin.getLogs().info(user);
            plugin.getLogs().info("Password length:");
            plugin.getLogs().info(password.length() + "");
            plugin.getLogs().info("--------------------");
            plugin.getLogs().info("");
            con = DriverManager.getConnection(url,user,password);
            plugin.getLogs().info("Connected with MySQL!");
        } catch (SQLException e) {
            plugin.getLogs().error("Plugin can't connect to MySQL or cant initialize tables.");
            plugin.getLogs().error(e);
            plugin.getLogs().error("-------------------------");
            plugin.getLogs().info(" ");
            plugin.getLogs().info("-------------------------");
            plugin.getLogs().info("Using SQL instead MySQL.");
            plugin.getLogs().info("-------------------------");
            plugin.getKitPvP().disableMySQL();
        }
    }

    @Override
    public void addKit(String paramPlayer,String kit) {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    Statement statement = con.createStatement();
                    String query = "SELECT Kits FROM " + TABLE_PREFIX + " WHERE " + MYSQL_PORT_RECEIVER + " = '" + paramPlayer + "';";
                    ResultSet resultSet = statement.executeQuery(query);
                    resultSet.next();
                    String kits = resultSet.getString("Kits");
                    kits = kits + "," + kit;
                    String updateQuery = "UPDATE " + TABLE_PREFIX + " SET Kits='" + kits + "' WHERE " + MYSQL_PORT_RECEIVER + "='" + paramPlayer + "';";
                    con.prepareStatement(updateQuery).executeUpdate();
                } catch (Throwable throwable) {
                    plugin.getLogs().error("Can't add kit '" + kit + "' to player '" + paramPlayer + "'");
                    plugin.getLogs().error(throwable);
                }
            }
        };
        runnable.runTaskAsynchronously(plugin);
    }
    @Override
    public void removeKit(String paramPlayer,String kit) {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    Statement statement = con.createStatement();
                    String query = "SELECT Kits FROM " + TABLE_PREFIX + " WHERE " + MYSQL_PORT_RECEIVER + " = '" + paramPlayer + "';";
                    ResultSet resultSet = statement.executeQuery(query);
                    resultSet.next();
                    String kits = resultSet.getString("Kits");
                    kits = kits.replace("," + kit, "").replace(kit + ",", "");
                    String updateQuery = "UPDATE " + TABLE_PREFIX + " SET Kits='" + kits + "' WHERE " + MYSQL_PORT_RECEIVER + "='" + paramPlayer + "';";
                    con.prepareStatement(updateQuery).executeUpdate();
                } catch (Throwable throwable) {
                    plugin.getLogs().error("Can't remove kit '" + kit + "' from player '" + paramPlayer + "'");
                    plugin.getLogs().error(throwable);
                }
            }
        };
        runnable.runTaskAsynchronously(plugin);
    }


    @Override
    public void loadStats(final Player player) {
        plugin.getLogs().debug("loading stats of " + player.getName());
        final String playerName =  isUUID ? player.getUniqueId().toString() : player.getName();
        (new BukkitRunnable() {
            public void run() {
                try {
                    PlayerManager manager = plugin.getKitPvP().getPlayers().getUser(player.getUniqueId());

                    Statement statement = con.createStatement();
                    if (statement.executeQuery("SELECT * FROM " + TABLE_PREFIX + " WHERE " + MYSQL_PORT_RECEIVER + " = '" + playerName + "';").next()) {
                        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_PREFIX + " WHERE " + MYSQL_PORT_RECEIVER + " = '" + playerName + "';");
                        resultSet.next();
                        manager.setKits(resultSet.getString("Kits"));
                        manager.setStatsFromString(resultSet.getString("Statistics"));
                        statement.close();
                        resultSet.close();
                    } else {
                        manager.resetPlayer();
                    }
                }catch(Throwable throwable) {
                    throwable.printStackTrace();
                    if(plugin.getKitPvP() != null) {
                        if(plugin.getKitPvP().getPlayers() != null) {
                            if(plugin.getKitPvP().getPlayers().getUser(player.getUniqueId()) != null) {
                                plugin.getKitPvP().getPlayers().getUser(player.getUniqueId()).resetPlayer();
                            }
                        }
                    }
                }
            }
        }).runTaskLaterAsynchronously(plugin, 2L);
    }

    @SuppressWarnings("UnnecessaryToStringCall")
    @Override
    public void saveStats(Player paramPlayer) {
        String kits = "NO_KITS";
        PlayerManager manager = plugin.getKitPvP().getPlayers().getUser(paramPlayer.getUniqueId());
        if(!manager.getKitsString().equalsIgnoreCase("")) kits = manager.getKitsString();
        String call = isUUID ? paramPlayer.getUniqueId().toString() : paramPlayer.getName();
        String stats = manager.getStatsString();
        try {
            Statement statement = con.createStatement();
            if (statement.executeQuery("SELECT * FROM " + TABLE_PREFIX + " WHERE " + MYSQL_PORT_RECEIVER + " = '" + call + "';").next()) {
                con.prepareStatement("UPDATE " + TABLE_PREFIX + " SET player_uuid='" + paramPlayer.getUniqueId().toString() + "', player_name='" + paramPlayer.getName() + "', Kits='" + kits + "', Statistics='" + stats + "' WHERE " + MYSQL_PORT_RECEIVER + "='" + call + "';").executeUpdate();
            } else {
                statement.executeUpdate("INSERT INTO " + TABLE_PREFIX + " (player_uuid, player_name, Kits, Statistics) VALUES ('" + paramPlayer.getUniqueId().toString() + "', '" + paramPlayer.getName() + "', '" + kits + "', '" + stats + "')");
            }
            statement.close();
        } catch (Throwable throwable) {
            plugin.getLogs().error("Can't save stats for " + paramPlayer.getName());
            plugin.getLogs().error(throwable);
        }
    }

    @Override
    public void setReceiverSender(String paramString) {
        this.MYSQL_PORT_RECEIVER = paramString;
    }

    @Override
    public String getReceiverSender() {
        return MYSQL_PORT_RECEIVER;
    }

    @Override
    public void close() {
        if (con != null)
            try {
                con.close();
            } catch (SQLException ignored) { }
    }

    @Override
    public void pUpdate(String qry,String result,String ID) {
        try {
            PreparedStatement statement = con.prepareStatement(qry);
            statement.setString(1,result);
            statement.setString(2,ID);
            statement.executeUpdate();
            statement.close();
        }catch (Throwable throwable) {
            plugin.getLogs().error("Can't update query(s)!");
            plugin.getLogs().error(throwable);
        }
    }

    @Override
    public Connection getConnection() {
        return con;
    }


    @Override
    public void Update(String qry) {
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(qry);
        } catch (SQLException e) {
            plugin.getLogs().error("Can't update query(s)!");
            plugin.getLogs().error(e);
        }
    }

    @Override
    @SuppressWarnings("unused")
    public ResultSet pQuery(String query) {
        ResultSet rs = null;
        try {
            PreparedStatement statement = con.prepareStatement(query);
            rs = statement.executeQuery();
            statement.close();
        }catch (Throwable throwable) {
            plugin.getLogs().error("Can't execute query(s)!");
            plugin.getLogs().error(throwable);
        }
        return rs;
    }


    @Override
    public ResultSet Query(String qry) {
        ResultSet rs = null;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(qry);
        } catch (SQLException e) {
            plugin.getLogs().error("Can't execute query(s)!");
            plugin.getLogs().error(e);
        }
        return rs;
    }
}
