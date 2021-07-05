package dev.mruniverse.guardiankitpvp.storage;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import dev.mruniverse.guardiankitpvp.interfaces.storage.MySQL;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLBuilder implements MySQL {
    private GuardianKitPvP plugin;
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
            String url= settings.getString("settings.game.mysql.jdbc-url");
            int port = settings.getInt("settings.game.mysql.port");
            if(url == null) url = "jdbc:mysql://" + host + ":" + settings.getInt("settings.game.mysql.port") + "/" + db + "?autoReconnect=true";
            url = url.replace("[host]",host)
                    .replace("[port]",port + "")
                    .replace("[db]",db);
            con = DriverManager.getConnection(url,user,password);
            plugin.getLogs().info("Connected with MySQLImpl! creating tables");
            List<String> integers = new ArrayList<>();
            integers.add("Coins");
            integers.add("Wins");
            integers.add("Deaths");
            integers.add("Kills");
            List<String> strings = new ArrayList<>();
            strings.add("Player");
            strings.add("Kits");
            strings.add("SelectedKit");
            plugin.getKitPvP().getDataStorage().createMultiTable(settings.getString("settings.game.mysql.table-prefix"), integers, strings);
            plugin.getLogs().info("Tables created!");
        } catch (SQLException e) {
            plugin.getLogs().error("Plugin can't connect to MySQLImpl or cant initialize tables.");
            plugin.getLogs().error(e);
            plugin.getLogs().error("Using SQLImpl instead MySQLImpl.");
            plugin.getLogs().error("-------------------------");
            plugin.getKitPvP().getDataStorage().getSQL().loadData();
        }
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
