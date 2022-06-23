package de.minetrouble.networkapi.manager.logging;

import de.minetrouble.networkapi.NetworkApi;

import java.util.UUID;

/**
 * @author KeinByte
 * @since 23.06.2022
 */
public class NetworkLog {


    public static void create(UUID uuidTeam, UUID uuidPlayer, LogType logType, long amount, ReasonType reason, long time){
        NetworkApi.getNetworkApi().getMySQLDatabaseEntry().getLogCollection()
                .insert()
                .set("uuidTeam", uuidTeam)
                .set("uuidPlayer", uuidPlayer)
                .set("type", logType)
                .set("amount", amount)
                .set("reason", reason)
                .set("time", time)
                .execute();
    }

}
