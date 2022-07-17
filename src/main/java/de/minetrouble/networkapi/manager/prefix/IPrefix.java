package de.minetrouble.networkapi.manager.prefix;

import org.bukkit.entity.Player;

/**
 * @author KeinByte
 * @since 15.07.2022
 */
public interface IPrefix {

    void applyPrefix(Player player);
    String getPlayerPrefix(Player player);

}
