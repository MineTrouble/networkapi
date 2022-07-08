package de.minetrouble.networkapi.manager.caseopening;

import de.minetrouble.networkapi.NetworkApi;

import java.util.UUID;

/**
 * @author KeinByte
 * @since 23.06.2022
 */
public class CasePlayer {

    private UUID uuid;
    private long caseLite;
    private long caseMiddle;
    private long caseExtreme;

    public CasePlayer(UUID uuid, long caseLite, long caseMiddle, long caseExtreme){
        this.uuid = uuid;
        this.caseLite = caseLite;
        this.caseMiddle = caseMiddle;
        this.caseExtreme = caseExtreme;
    }

    public UUID getUuid() {
        return uuid;
    }

    public long getCaseExtreme() {
        return caseExtreme;
    }

    public long getCaseLite() {
        return caseLite;
    }

    public long getCaseMiddle() {
        return caseMiddle;
    }

    public void setCaseExtreme(long caseExtreme) {
        this.caseExtreme = caseExtreme;
        NetworkApi.getNetworkApi().getMySQLDatabaseEntry().getCaseCollection()
                .update()
                .set("caseExtreme", caseExtreme)
                .where("uuid", uuid)
                .executeAsync();
    }

    public void setCaseLite(long caseLite) {
        this.caseLite = caseLite;
        NetworkApi.getNetworkApi().getMySQLDatabaseEntry().getCaseCollection()
                .update()
                .set("caseMiddle", caseExtreme)
                .where("uuid", uuid)
                .executeAsync();
    }

    public void setCaseMiddle(long caseMiddle) {
        this.caseMiddle = caseMiddle;
        NetworkApi.getNetworkApi().getMySQLDatabaseEntry().getCaseCollection()
                .update()
                .set("caseLite", caseExtreme)
                .where("uuid", uuid)
                .executeAsync();
    }

    public boolean existsCasePlayer(UUID uuid){
        this.uuid = uuid;
        return !NetworkApi.getNetworkApi().getMySQLDatabaseEntry().getCaseCollection()
                .find()
                .where("uuid", uuid)
                .execute()
                .isEmpty();
    }
}
