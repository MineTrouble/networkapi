package de.minetrouble.networkapi.manager.stats;

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
 * @since 17.07.2022
 */
public class StatsPlayerCache {

    private final Cache<StatsPlayer> statsPlayerCache;

    public StatsPlayerCache() {
        statsPlayerCache = new ArrayCache<StatsPlayer>()
                .setExpireAfterAccess(1, TimeUnit.MINUTES)
                .setMaxSize(500)
                .registerQuery(
                        "byUUID",
                        new CacheQuery<StatsPlayer>() {
                            @Override
                            public StatsPlayer load(Object[] identifiers) {
                                UUID uuid = (UUID) identifiers[0];
                                DatabaseCollection databaseCollection = NetworkApi.getNetworkApi().getMySQLDatabaseEntry().getStatsCollection();
                                QueryResultEntry queryResultEntry = databaseCollection.find().where("uuid", uuid).execute().firstOrNull();

                                if (queryResultEntry == null){
                                    insertStatsPlayer(uuid, null);
                                    return new StatsPlayer(uuid, null, 0,0,0,0,0,0);
                                }
                                return new StatsPlayer(
                                        uuid,
                                        queryResultEntry.getString("gameType"),
                                        queryResultEntry.getInt("kills"),
                                        queryResultEntry.getInt("deaths"),
                                        queryResultEntry.getInt("wins"),
                                        queryResultEntry.getInt("looses"),
                                        queryResultEntry.getInt("played"),
                                        queryResultEntry.getInt("points"));
                            }

                            @Override
                            public boolean check(StatsPlayer statsPlayer, Object[] objects) {
                                return statsPlayer.getUuid().equals(objects[0]);
                            }
                        });
    }

    public void insertStatsPlayer(UUID uuid, String gameType){
        NetworkApi.getNetworkApi().getMySQLDatabaseEntry().getStatsCollection()
                .insert()
                .set("uuid", uuid)
                .set("gameType", gameType)
                .set("kills", 0)
                .set("deaths", 0)
                .set("wins", 0)
                .set("looses", 0)
                .set("played", 0)
                .set("points", 0)
                .execute();
    }

    public StatsPlayer getPlayerByUuid(UUID uuid) {
        return statsPlayerCache.get("byUUID", uuid);
    }

}
