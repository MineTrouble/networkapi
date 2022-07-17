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

    /*public long getCoins() {
        return coins;
    }

    /**
     * Coins

    public void setCoins(long coins) {
        this.coins = coins;
        NetworkApi.getNetworkApi().getMySQLDatabaseEntry().getPlayerCollection()
                .update()
                .set("coins", coins)
                .where("uuid", uuid)
                .executeAsync();
    }

    public void addCoins(long coins) {
        NetworkApi.getNetworkApi().getMySQLDatabaseEntry().getPlayerCollection()
                .update()
                .set("coins", coins)
                .where("uuid", uuid)
                .executeAsync();

    }

    public void removeCoins(long coins) {
        this.coins = coins;
        setCoins(getCoins() - coins);
    }**/

    /**
     * FirstJoin
     */
    public long getFirstJoin() {
        return firstJoin;
    }


    public long getLastJoin() {
        return lastJoin;
    }

    public void setLastJoin(long lastJoin) {
        this.lastJoin = lastJoin;
        NetworkApi.getNetworkApi().getMySQLDatabaseEntry().getPlayerCollection()
                .update()
                .set("lastJoin", lastJoin)
                .where("uuid", uuid)
                .executeAsync();
    }

    public long getCoins(){
        return NetworkApi.getNetworkApi().getMySQLEntry().getKeyAsLong("network_player", "uuid", uuid.toString(), "coins");
    }

    public void setCoins(long coins){
        this.coins = coins;
        NetworkApi.getNetworkApi().getMySQLEntry().update("UPDATE network_player SET coins='" + coins + "' WHERE uuid='" + uuid + "'");
    }

    public void addCoins(long coins){
        this.coins = coins;
        NetworkApi.getNetworkApi().getMySQLEntry().update("UPDATE network_player SET coins='" + (getCoins() + coins) + "' WHERE uuid='" + uuid + "'");
    }

    public void removeCoins(long coins){
        this.coins = coins;
        NetworkApi.getNetworkApi().getMySQLEntry().update("UPDATE network_player SET coins='" + (getCoins() - coins) + "' WHERE uuid='" + uuid + "'");
    }

}
