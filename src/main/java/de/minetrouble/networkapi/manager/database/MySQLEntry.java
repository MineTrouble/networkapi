package de.minetrouble.networkapi.manager.database;

import de.minetrouble.networkapi.NetworkApi;

import java.sql.*;

/**
 * @author KeinByte
 * @since 24.06.2022
 */
public class MySQLEntry {

    public String host = "localhost";

    public String port = "3306";

    public String database = "network_api";

    public String username = "root";

    public String passwort = NetworkApi.getNetworkApi().getConfig().getString("password");

    public Connection connection;

    public void connect() {
        if (!isConnectet())
            try {
                this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true", this.username, this.passwort);
                System.out.println("=====================");
                System.out.println(" ");
                System.out.println(" ");
                System.out.println("[MySQL]");
                System.out.println("Status: Verbindung hergestellt");
                System.out.println(" ");
                System.out.println(" ");
                System.out.println("=====================");
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public void disconnect() {
        if (isConnectet())
            try {
                this.connection.close();
                System.out.println("=====================");
                System.out.println(" ");
                System.out.println(" ");
                System.out.println("[MySQL]");
                System.out.println("Status: Verbindung getrent");
                System.out.println(" ");
                System.out.println(" ");
                System.out.println("=====================");
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public boolean isConnectet() {
        return !(this.connection == null);
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void update(String qry) {
        try {
            this.connection.createStatement().executeUpdate(qry);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getResult(String qry) {
        try {
            return this.connection.createStatement().executeQuery(qry);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public long getKeyAsLong(String table, String whereKey, String setWhereKey, String getKey) {
        long value = 0L;
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `" + table + "` WHERE `" + whereKey + "` = ?")) {
            preparedStatement.setString(1, setWhereKey);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                value = resultSet.getLong(getKey);
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return value;
    }

}
