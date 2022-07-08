package de.minetrouble.networkapi.command.coins;

import de.minetrouble.networkapi.NetworkApi;
import de.minetrouble.networkapi.manager.player.NetworkPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author KeinByte
 * @since 02.07.2022
 */
public class CoinAdminCommand implements CommandExecutor {
    @Deprecated
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Player player = (Player) commandSender;

        if (!player.hasPermission("network.command.coinadmin")) {
            player.sendMessage("Keine Rechte");
            return true;
        }

        if (strings.length != 3) {
            player.sendMessage("/coinadmin addcoins <Name> <Coins>");
            player.sendMessage("/coinadmin removecoins <Name> <Coins>");
            player.sendMessage("/coinadmin setcoins <Name> <Coins>");
            return true;
        }

        Player target = Bukkit.getPlayer(strings[1]);
        long value = Long.parseLong(strings[2]);
        if (target != null) {
            NetworkPlayer networkPlayer = NetworkApi.getNetworkApi().getNetworkPlayerCache().getPlayerByUuid(target.getUniqueId());
            if (networkPlayer.existsPlayer()) {
                switch (strings[0].toLowerCase()) {
                    case "addcoins":
                        networkPlayer.addCoins(value);
                        player.sendMessage("Gesetzt: " + value + " Gesamt: " + networkPlayer.getCoins());
                        break;
                    case "removecoins":
                        networkPlayer.removeCoins(value);
                        player.sendMessage("Gesetzt: " + value + " Gesamt: " + networkPlayer.getCoins());
                        break;
                    case "setcoins":
                        networkPlayer.setCoins(value);
                        player.sendMessage("Gesetzt: " + value + " Gesamt: " + networkPlayer.getCoins());
                        break;
                }
            } else {
                player.sendMessage("NICHT DA LOL FEHLT");
            }
        } else {
            OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(strings[1]);
            NetworkPlayer networkPlayer = NetworkApi.getNetworkApi().getNetworkPlayerCache().getPlayerByUuid(offlineTarget.getUniqueId());
            if (networkPlayer.existsPlayer()) {
                switch (strings[0].toLowerCase()) {
                    case "addcoins":
                        networkPlayer.addCoins(value);
                        player.sendMessage("Gesetzt: " + value + " Gesamt: " + networkPlayer.getCoins());
                        break;
                    case "removecoins":
                        networkPlayer.removeCoins(value);
                        player.sendMessage("Gesetzt: " + value + " Gesamt: " + networkPlayer.getCoins());
                        break;
                    case "setcoins":
                        networkPlayer.setCoins(value);
                        player.sendMessage("Gesetzt: " + value + " Gesamt: " + networkPlayer.getCoins());
                        break;
                }
            }
        }

        return false;
    }
}
