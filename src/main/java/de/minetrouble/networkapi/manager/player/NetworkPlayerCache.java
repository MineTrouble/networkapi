package de.minetrouble.networkapi.manager.player;

import de.minetrouble.networkapi.NetworkApi;
import net.pretronic.databasequery.api.collection.DatabaseCollection;
import net.pretronic.databasequery.api.query.result.QueryResultEntry;
import net.pretronic.libraries.caching.ArrayCache;
import net.pretronic.libraries.caching.Cache;
import net.pretronic.libraries.caching.CacheQuery;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author KeinByte
 * @since 22.06.2022
 */
public class NetworkPlayerCache {

    private final Cache<NetworkPlayer> networkPlayerCache;

    public NetworkPlayerCache() {
        networkPlayerCache = new ArrayCache<NetworkPlayer>()
                .setExpireAfterAccess(1, TimeUnit.MINUTES)
                .setMaxSize(500)
                .registerQuery(
                        "byUUID",
                        new CacheQuery<NetworkPlayer>() {
                            @Override
                            public NetworkPlayer load(Object[] identifiers) {
                                UUID uuid = (UUID) identifiers[0];
                                DatabaseCollection databaseCollection = NetworkApi.getNetworkApi().getMySQLDatabaseEntry().getPlayerCollection();
                                QueryResultEntry queryResultEntry = databaseCollection.find().where("uuid", uuid).execute().firstOrNull();

                                if (queryResultEntry == null){
                                    insertNetworkPlayer(uuid);
                                    return new NetworkPlayer(uuid, System.currentTimeMillis(), 0,0);
                                }

                                return new NetworkPlayer(
                                        uuid,
                                        queryResultEntry.getLong("firstJoin"),
                                        queryResultEntry.getLong("lastJoin"),
                                        queryResultEntry.getLong("coins"));
                            }

                            @Override
                            public boolean check(NetworkPlayer networkPlayer, Object[] objects) {
                                return networkPlayer.getUuid().equals(objects[0]);
                            }
                        });
    }

    public void insertNetworkPlayer(UUID uuid){
        NetworkApi.getNetworkApi().getMySQLDatabaseEntry().getPlayerCollection()
                .insert()
                .set("uuid", uuid)
                .set("firstJoin", System.currentTimeMillis())
                .set("lastJoin", 0)
                .set("coins", 0)
                .execute();
    }

    
    public NetworkPlayer getPlayerByUuid(UUID uuid){
        return networkPlayerCache.get("byUUID", uuid);
    }

}
