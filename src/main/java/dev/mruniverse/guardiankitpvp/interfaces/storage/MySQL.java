package dev.mruniverse.guardiankitpvp.interfaces.storage;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import org.bukkit.entity.Player;

import java.sql.*;

public interface MySQL {

    void setPlugin(GuardianKitPvP plugin);

    void connect(String host, String db, String user, String password);

    void close();

    void pUpdate(String qry,String result,String ID);

    Connection getConnection();

    void Update(String qry);

    void setReceiverSender(String paramString);

    void addKit(String paramPlayer,String kit);

    void removeKit(String paramPlayer,String kit);

    void loadStats(final Player player);

    void saveStats(Player paramPlayer);

    void saveStats(Player paramPlayer,PlayerManager manager);

    String getReceiverSender();


    @SuppressWarnings("unused")
    ResultSet pQuery(String query);

    ResultSet Query(String qry);
}
