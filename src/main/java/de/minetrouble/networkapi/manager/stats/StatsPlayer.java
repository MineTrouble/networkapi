package de.minetrouble.networkapi.manager.stats;

import lombok.Getter;

import java.util.UUID;

/**
 * @author KeinByte
 * @since 17.07.2022
 */
public class StatsPlayer {

    @Getter private UUID uuid;
    @Getter private String gameType;
    @Getter private int kills;
    @Getter private int deaths;
    @Getter private int wins;
    @Getter private int looses;
    @Getter private int played;
    @Getter private int points;

    public StatsPlayer(UUID uuid, String gameType, int kills, int deaths, int wins, int looses, int played, int points){
        this.uuid = uuid;
        this.gameType = gameType;
        this.kills = kills;
        this.deaths = deaths;
        this.wins = wins;
        this.looses = looses;
        this.played = played;
        this.points = points;
    }

    public float getKd(){
        this.uuid = uuid;
        this.kills = kills;
        this.deaths = deaths;
        float kd = (deaths != 0) ? (kills/deaths) : 0.0F;
        return kd;
    }

}
