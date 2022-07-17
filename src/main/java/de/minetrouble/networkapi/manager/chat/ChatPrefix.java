package de.minetrouble.networkapi.manager.chat;

/**
 * @author KeinByte
 * @since 14.07.2022
 */
public class ChatPrefix {

    public String getPrefix(){
        return "§8➥ §6MineTrouble §8» §r";
    }

    public String getUsage(){
        return getPrefix() + "§7Bitte verwende §6/";
    }

    public String getNoRights(){
        return getPrefix() + "§7Dazu hast du §ckeine Rechte §7aktuell.";
    }

}
