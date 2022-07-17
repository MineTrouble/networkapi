package de.minetrouble.networkapi.manager.database;

import de.minetrouble.networkapi.manager.database.interfaces.IMySQLCredentials;

/**
 * @author KeinByte
 * @since 14.07.2022
 */
public class MySQLCredentials implements IMySQLCredentials {

    private final String username;
    private final String password;
    private final String host;
    private final String database;
    private final int port;

    public MySQLCredentials(String username, String password, String host, String database, int port){
        this.username = username;
        this.password = password;
        this.host = host;
        this.database = database;
        this.port = port;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public String getDatabase() {
        return database;
    }

    @Override
    public int getPort() {
        return port;
    }
}
