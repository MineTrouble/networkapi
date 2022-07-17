package de.minetrouble.networkapi.manager.discord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * @author KeinByte
 * @since 14.07.2022
 */
public class WebHook {

    private final Instant logTime;
    private final String playerName;
    private final String message;

    public WebHook(Instant logTime, String playerName, String message){
        this.logTime = logTime;
        this.playerName = playerName;
        this.message = message;
    }

    public static WebHookBuilder webHookBuilder(){
        try {
            return new WebHookBuilder();
        }catch (Throwable throwable){
            throw throwable;
        }
    }

    public Instant getLogTime() {
        return this.logTime;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public String getWebHookMessage() {
        return this.message;
    }

    public void push(WebHookLogType type) {
        if (type.getUrl() == null)
            return;
        try {
            URL url = new URL(type.getUrl());
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type", "application/json");
            String json = "{\"content\":\"**Eine neue Aktion wurde ausgef\",\"embeds\":[{\"title\":\"Aktion von %username%\",\"color\":11602699,\"fields\":[{\"name\":\"Time\",\"value\":\"%time%\"},{\"name\":\"Description\",\"value\":\"%description%\"}],\"author\":{\"name\":\"Lostify\",\"icon_url\":\"https://forum.lostify.net/images/styleLogo-3706a52d01d94763137dedbaea0584850b278cbd.png\"},\"footer\":{\"text\":\"MineCraft-WebHook by TheRealDomm\"}}]}";
            json = json.replaceAll("%time%", getFormattedLogTime()).replaceAll("%username%", this.playerName).replaceAll("%description%", this.message);
            OutputStreamWriter outputStream = new OutputStreamWriter(connection.getOutputStream());
            outputStream.write(json);
            outputStream.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                response.append(line);
            outputStream.close();
            reader.close();
            connection.disconnect();
            if (!response.toString().trim().isEmpty()) {
                System.out.println("WebHook call returned " + response);
            } else {
                System.out.println("WebHook call succeeded");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFormattedLogTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        return simpleDateFormat.format(Date.from(this.logTime));
    }

}
