package dev.mruniverse.guardiankitpvp.interfaces.storage;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;

import java.sql.*;

public interface MySQL {

    void setPlugin(GuardianKitPvP plugin);

    void connect(String host, String db, String user, String password);

    void close();

    void pUpdate(String qry,String result,String ID);

    Connection getConnection();

    void Update(String qry);

    void setReceiverSender(String paramString);


    String getReceiverSender();


    @SuppressWarnings("unused")
    ResultSet pQuery(String query);

    ResultSet Query(String qry);
}
