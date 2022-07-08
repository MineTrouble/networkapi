package de.minetrouble.networkapi.manager.caseopening;

import de.minetrouble.networkapi.NetworkApi;
import de.minetrouble.networkapi.manager.player.NetworkPlayer;
import net.pretronic.databasequery.api.collection.DatabaseCollection;
import net.pretronic.databasequery.api.query.result.QueryResultEntry;
import net.pretronic.libraries.caching.ArrayCache;
import net.pretronic.libraries.caching.Cache;
import net.pretronic.libraries.caching.CacheQuery;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author KeinByte
 * @since 23.06.2022
 */
public class CasePlayerCache {
    private final Cache<CasePlayer> casePlayerCache;

    public CasePlayerCache() {
        casePlayerCache = new ArrayCache<CasePlayer>()
                .setExpireAfterAccess(1, TimeUnit.MINUTES)
                .setMaxSize(500)
                .registerQuery(
                        "byUUID",
                        new CacheQuery<CasePlayer>() {
                            @Override
                            public CasePlayer load(Object[] identifiers) {
                                UUID uuid = (UUID) identifiers[0];
                                DatabaseCollection databaseCollection = NetworkApi.getNetworkApi().getMySQLDatabaseEntry().getPlayerCollection();
                                QueryResultEntry queryResultEntry = databaseCollection.find().where("uuid", uuid).execute().firstOrNull();

                                if (queryResultEntry == null){
                                    insertCasePlayer(uuid);
                                    return new CasePlayer(uuid,0, 0,0);
                                }

                                return new CasePlayer(
                                        uuid,
                                        queryResultEntry.getLong("caseLite"),
                                        queryResultEntry.getLong("caseMiddle"),
                                        queryResultEntry.getLong("caseExtreme"));
                            }

                            @Override
                            public boolean check(CasePlayer casePlayer, Object[] objects) {
                                return casePlayer.getUuid().equals(objects[0]);
                            }
                        });
    }

    public void insertCasePlayer(UUID uuid){
        NetworkApi.getNetworkApi().getMySQLDatabaseEntry().getCaseCollection()
                .insert()
                .set("uuid", uuid)
                .set("caseLite", 0)
                .set("caseMiddle", 0)
                .set("caseExtreme", 0)
                .execute();
    }


    public CasePlayer getPlayerByUuid(UUID uuid){
        return casePlayerCache.get("byUUID", uuid);
    }

}
