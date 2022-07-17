package de.minetrouble.networkapi.manager.settings;

import de.minetrouble.networkapi.manager.item.ItemBuilder;
import org.bukkit.entity.Player;

/**
 * @author KeinByte
 * @since 03.07.2022
 */
public class Settings {

    private String storageName;
    private SettingsValue[] availableValues;
    private SettingsValue defaultValue;
    private ItemBuilder displayItem;
    private String requiredPermission;
    private SettingsType settingsType;

    public Settings(SettingsType settingsType, String storageName, SettingsValue[] availableValues, ItemBuilder displayItem, String requiredPermission, SettingsValue defaultValue){
        this.storageName = storageName;
        this.settingsType = settingsType;
        this.defaultValue = defaultValue;
        this.availableValues = availableValues;
        this.displayItem = displayItem;
        this.requiredPermission = requiredPermission;
    }

    public String getStorageName() {
        return storageName;
    }

    public int getValueIndex(SettingsValue settingsValue){
        for (int i = 0; i < availableValues.length; i++) {
            if (availableValues[i].equals(settingsValue)) return i;
        }
        return 0;
    }

    public boolean hasPermission(Player player){
        if (requiredPermission == null || requiredPermission.equalsIgnoreCase("-")) return true;
        return player.hasPermission(requiredPermission);
    }

}
