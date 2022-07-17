package de.minetrouble.networkapi.manager.database.interfaces;

/**
 * @author KeinByte
 * @since 14.07.2022
 */
public interface IMySQLCredentials {

    String getUsername();
    String getPassword();
    String getHost();
    String getDatabase();
    int getPort();

}
