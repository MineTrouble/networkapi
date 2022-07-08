package de.minetrouble.networkapi.manager.caseopening;

import java.util.UUID;

/**
 * @author KeinByte
 * @since 23.06.2022
 */
public class CaseLog {

    private UUID uuid;
    private String winningItemDisplay;
    private String chestType;
    private long time;

    public CaseLog(UUID uuid, String winningItemDisplay, String chestType, long time){
        this.uuid = uuid;
        this.winningItemDisplay = winningItemDisplay;
        this.chestType = chestType;
        this.time = time;
    }

    public UUID getUuid() {
        return uuid;
    }

    public long getTime() {
        return time;
    }

    public String getChestType() {
        return chestType;
    }

    public String getWinningItemDisplay() {
        return winningItemDisplay;
    }
}
