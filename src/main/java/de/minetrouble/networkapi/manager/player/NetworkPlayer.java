package de.minetrouble.networkapi.manager.player;

import de.minetrouble.networkapi.NetworkApi;
import net.pretronic.databasequery.api.query.result.QueryResultEntry;

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

    public NetworkPlayer(UUID uuid, long firstJoin, long lastJoin, long coins){
        this.uuid = uuid;
        this.firstJoin = firstJoin;
        this.lastJoin = lastJoin;
        this.coins = coins;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean existsPlayer(UUID uuid){
        this.uuid = uuid;
       return !NetworkApi.getNetworkApi().getMySQLDatabaseEntry().getPlayerCollection()
               .find()
               .where("uuid", uuid)
               .execute()
               .isEmpty();
    }

    public long getCoins() {
        return coins;
    }

    /**
     * Coins
     */
    public void setCoins(long coins){
        this.coins = coins;
        NetworkApi.getNetworkApi().getMySQLDatabaseEntry().getPlayerCollection()
                .update()
                .set("coins", coins)
                .where("uuid", uuid)
                .executeAsync();
    }

    public void addCoins(long coins){
        this.coins = coins;
        NetworkApi.getNetworkApi().getMySQLDatabaseEntry().getPlayerCollection()
                .update()
                .set("coins", getCoins() + coins)
                .where("uuid", uuid)
                .executeAsync();
    }

    public void removeCoins(long coins){
        this.coins = coins;
        NetworkApi.getNetworkApi().getMySQLDatabaseEntry().getPlayerCollection()
                .update()
                .set("coins", getCoins() - coins)
                .where("uuid", uuid)
                .executeAsync();
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
