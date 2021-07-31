package dev.mruniverse.guardiankitpvp.interfaces.storage;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import org.bukkit.entity.Player;

import java.util.List;

@SuppressWarnings("unused")
public interface DataStorage {
    DataStorage setTable(String table);

    void setPlugin(GuardianKitPvP plugin);

    void setMySQL(MySQL mysql);

    void setSQL(SQL sql);

    void saveStats(final Player p, boolean sync);

    void createMultiTable(String tableName, List<String> intLists, List<String> sLists);

    void setInt(String tableName, String column, String where, String what, int integer);

    void setString(String tableName, String column, String where, String is, String result);

    String getString(String tableName, String column, String where, String what);

    String getTable();

    Integer getInt(String tableName, String column, String where, String what);

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean isRegistered(String tableName, String where, String what);

    void register(String tableName, List<String> values);

    void loadDatabase();

    void disableDatabase();

    MySQL getMySQL();
    SQL getSQL();
}
