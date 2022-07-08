package de.minetrouble.networkapi.manager.player;

import de.minetrouble.networkapi.NetworkApi;
import net.pretronic.databasequery.api.query.result.QueryResultEntry;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;


/**
 * @author KeinByte
 * @since 22.06.2022
 */
public class NetworkPlayer {

    private UUID uuid;
    private long firstJoin;
    private long lastJoin;
    private long coins;

    public NetworkPlayer(UUID uuid, long firstJoin, long lastJoin, long coins) {
        this.uuid = uuid;
        this.firstJoin = firstJoin;
        this.lastJoin = lastJoin;
        this.coins = coins;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean existsPlayer() {
        return !NetworkApi.getNetworkApi().getMySQLDatabaseEntry().getPlayerCollection()
                .find()
                .where("uuid", uuid)
                .execute()
                .isEmpty();
    }

    public long getCoins() {
        long coins = 0;
        try {
            ResultSet resultSet = NetworkApi.getNetworkApi().getMySQLEntry().getResult("SELECT * FROM network_player WHERE uuid = '" + uuid + "'");
            if (!resultSet.next() || Long.valueOf(resultSet.getLong("coins")) == null) ;
            coins = Long.valueOf(resultSet.getLong("coins"));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return coins;
    }

    /**
     * Coins
     */
    public void setCoins(long coins) {
        this.coins = coins;
        NetworkApi.getNetworkApi().getMySQLEntry().update("UPDATE network_player SET coins = '" + coins + "' WHERE uuid = '" + uuid + "';");
    }

    public void addCoins(long coins) {
        this.coins = coins;
        setCoins(getCoins() + coins);
    }

    public void removeCoins(long coins) {
        this.coins = coins;
        setCoins(getCoins() - coins);
    }

    /**
     * FirstJoin
     */
    public long getFirstJoin() {
        return firstJoin;
    }


    public long getLastJoin() {
        return lastJoin;
    }

}
