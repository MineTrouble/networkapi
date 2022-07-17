package de.minetrouble.networkapi.manager.discord;

import java.time.Instant;

/**
 * @author KeinByte
 * @since 14.07.2022
 */
public class WebHookBuilder {

    private Instant logTime;
    private String playerName;
    private String message;

    WebHookBuilder(){}

    public WebHookBuilder logTime(Instant logTime) {
        this.logTime = logTime;
        return this;
    }

    public WebHookBuilder playerName(String playerName) {
        this.playerName = playerName;
        return this;
    }

    public WebHookBuilder webHookMessage(String webHookMessage) {
        this.message = webHookMessage;
        return this;
    }

    public WebHook build() {
        return new WebHook(this.logTime, this.playerName, this.message);
    }

}
