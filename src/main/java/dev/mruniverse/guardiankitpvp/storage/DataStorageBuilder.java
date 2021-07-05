package dev.mruniverse.guardiankitpvp.storage;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import dev.mruniverse.guardiankitpvp.interfaces.storage.DataInfo;
import dev.mruniverse.guardiankitpvp.interfaces.storage.DataStorage;
import dev.mruniverse.guardiankitpvp.interfaces.storage.MySQL;
import dev.mruniverse.guardiankitpvp.interfaces.storage.SQL;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class DataStorageBuilder implements DataStorage {
    private GuardianKitPvP plugin;
    private DataInfo dataInfoImpl;
    private MySQL mySQLImpl;
    private SQL sqlImpl;
    private String table;


    public DataStorageBuilder(GuardianKitPvP main) {
        plugin = main;
        mySQLImpl = new MySQLBuilder(main);
        dataInfoImpl = new DataInfoBuilder(main);
        sqlImpl = new SQLBuilder(main);
    }

    @Override
    public DataStorage setTable(String table) {
        this.table = table;
        return this;
    }

    @Override
    public String getTable() {
        return table;
    }

    @Override
    public void setMySQL(MySQL mysql){
        this.mySQLImpl = mysql;
    }

    @Override
    public void setDataInfo(DataInfo dataInfo) {
        this.dataInfoImpl = dataInfo;
    }

    @Override
    public void setSQL(SQL sql) {
        this.sqlImpl = sql;
    }

    @Override
    public void setPlugin(GuardianKitPvP plugin) { this.plugin = plugin; }

    @Override
    public void createMultiTable(String tableName, List<String> intLists, List<String> sLists) {
        StringBuilder intList = new StringBuilder();
        for (String text : intLists)
            intList.append(", ").append(text).append(" INT(255)");
        StringBuilder vList = new StringBuilder();
        for (String string : sLists)
            vList.append(", ").append(string).append(" VARCHAR(255)");
        mySQLImpl.Update("CREATE TABLE IF NOT EXISTS " + tableName + " (id INT AUTO_INCREMENT PRIMARY KEY" + vList + intList + ")");
    }

    @Override
    public void setInt(String tableName, String column, String where, String what, int integer) {
        what = what.replace("-","");
        mySQLImpl.Update("UPDATE " + tableName + " SET " + column + "= '" + integer + "' WHERE " + where + "= '" + what + "';");
    }

    @Override
    public void setString(String tableName, String column, String where, String is, String result) {
        is = is.replace("-","");
        mySQLImpl.pUpdate("UPDATE `" + tableName + "` SET " + column + "=? WHERE " + where + "=?;",result,is);
    }

    @Override
    public String getString(String tableName, String column, String where, String what) {
        what = what.replace("-","");
        String string = "";
        try {
            String query = "SELECT " + column + " FROM " + tableName + " WHERE " + where + "= '" + what + "';";
            Connection connection = mySQLImpl.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet results = statement.executeQuery();
            if (results.next()) return results.getString(column);
        } catch (Throwable throwable) {
            plugin.getLogs().error("Can't get a string from the database.");
            plugin.getLogs().error(throwable);
        }
        return string;
    }

    @Override
    public Integer getInt(String tableName, String column, String where, String what) {
        what = what.replace("-","");
        int integer = 0;
        try {
            ResultSet rs = mySQLImpl.Query("SELECT " + column + " FROM " + tableName + " WHERE " + where + "= '" + what + "';");
            while (rs.next())
                integer = rs.getInt(1);
            rs.close();
        } catch (Throwable throwable) {
            plugin.getLogs().error("Can't get an integer from the database.");
            plugin.getLogs().error(throwable);
        }
        return integer;
    }

    @Override
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isRegistered(String tableName, String where, String what) {
        what = what.replace("-","");
        try (ResultSet rs = mySQLImpl.Query("SELECT id FROM " + tableName + " WHERE " + where + "= '" + what + "';")) {
            boolean bol = rs.next();
            rs.close();
            return bol;
        } catch (Throwable throwable) {
            plugin.getLogs().error("Can't check internal data in database.");
            plugin.getLogs().error(throwable);
        }
        return false;
    }

    @Override
    public void register(String tableName, List<String> values) {
        StringBuilder names = new StringBuilder();
        StringBuilder names2 = new StringBuilder();
        for (String value : values) {
            String[] valSplit = value.split("-");
            names.append(valSplit[0]).append(", ");
            names2.append("'").append(valSplit[1]).append("', ");
        }
        names = new StringBuilder(names.substring(0, names.length() - 2));
        names2 = new StringBuilder(names2.substring(0, names2.length() - 2));
        mySQLImpl.Update("INSERT INTO " + tableName + " (" + names + ") VALUES (" + names2 + ")");
    }

    @Override
    public void loadDatabase() {
        FileConfiguration settings = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.SETTINGS);
        if (settings.getBoolean("settings.game.mysql.toggle")) {
            mySQLImpl.connect(settings.getString("settings.game.mysql.host"),settings.getString("settings.game.mysql.database"),settings.getString("settings.game.mysql.username"),settings.getString("settings.game.mysql.password"));
        } else {
            sqlImpl.loadData();
            plugin.getLogs().info("MySQLImpl is disabled, using data.yml");
        }
    }

    @Override
    public void disableDatabase() {
        if (plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.SETTINGS).getBoolean("settings.game.mysql.toggle")) {
            dataInfoImpl.save();
            mySQLImpl.close();
        } else {
            sqlImpl.putData();
        }
    }

    @Override
    public DataInfo getData() {
        return dataInfoImpl;
    }

    @Override
    public MySQL getMySQL() {
        return mySQLImpl;
    }

    @Override
    public SQL getSQL() {
        return sqlImpl;
    }
}
