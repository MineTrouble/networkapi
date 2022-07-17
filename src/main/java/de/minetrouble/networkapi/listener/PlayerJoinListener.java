package de.minetrouble.networkapi.listener;

import de.minetrouble.networkapi.NetworkApi;
import de.minetrouble.networkapi.manager.caseopening.CasePlayer;
import de.minetrouble.networkapi.manager.player.NetworkPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author KeinByte
 * @since 23.06.2022
 */
public class PlayerJoinListener implements Listener {

    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        NetworkPlayer networkPlayer = NetworkApi.getNetworkApi().getNetworkPlayerCache().getPlayerByUuid(player.getUniqueId());

        NetworkApi.getNetworkApi().getPrefix().applyPrefix(player);
        NetworkApi.getNetworkApi().getPrefix().getPlayerPrefix(player);

        if (!networkPlayer.existsPlayer()) {
            new NetworkPlayer(player.getUniqueId(), System.currentTimeMillis(), 0, 0);
            new CasePlayer(player.getUniqueId(), 0, 0, 0);
        }

        player.sendMessage("coins " + networkPlayer.getCoins());
        networkPlayer.setLastJoin(System.currentTimeMillis());
    }
}
