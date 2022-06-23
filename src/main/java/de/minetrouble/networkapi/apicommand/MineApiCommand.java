package de.minetrouble.networkapi.apicommand;

import de.dytanic.cloudnet.driver.service.ServiceId;
import de.minetrouble.networkapi.NetworkApi;
import de.minetrouble.networkapi.manager.command.AbstractCommand;
import de.minetrouble.networkapi.manager.logging.LogType;
import de.minetrouble.networkapi.manager.logging.NetworkLog;
import de.minetrouble.networkapi.manager.logging.ReasonType;
import de.minetrouble.networkapi.manager.player.NetworkPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

/**
 * @author KeinByte
 * @since 22.06.2022
 */
public class MineApiCommand extends AbstractCommand {


    public MineApiCommand() {
        super("mineapi", "network.mineapi", "Manage the network", null, true, null);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] strings) {

        Player player = (Player) commandSender;

        /**
         * /mineapi addcoins <Player> <Coins>
         * /mineapi setcoins <Player> <Coins>
         * /mineapi removecoins <Player> <Coins>
         *
         *     /mineapi playerinfo <Name>
         */

        if (strings.length == 3){
            Player target = Bukkit.getPlayer(strings[1]);
            long coins = Long.parseLong(strings[2]);
            if (target != null){
                NetworkPlayer networkPlayer = NetworkApi.getNetworkApi().getNetworkPlayerCache().getPlayerByUuid(target.getUniqueId());
                if (networkPlayer.existsPlayer(target.getUniqueId())){
                    if (strings[0].equalsIgnoreCase("addcoins")){
                        networkPlayer.addCoins(coins);
                        NetworkLog.create(player.getUniqueId(), target.getUniqueId(), LogType.COINS, coins, ReasonType.ADD_COINS, System.currentTimeMillis());
                        player.sendMessage("Eingabe: " + coins + " Stand: " + networkPlayer.getCoins());
                    }else if (strings[0].equalsIgnoreCase("removecoins")){
                        networkPlayer.removeCoins(coins);
                        player.sendMessage("Eingabe: " + coins + " Stand: " + networkPlayer.getCoins());
                    }else if (strings[0].equalsIgnoreCase("setcoins")){
                        networkPlayer.setCoins(coins);
                        player.sendMessage("Eingabe: " + coins + " Stand: " + networkPlayer.getCoins());
                    }
                }else{
                    player.sendMessage("Gibt es nicht");
                }
            }else{
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(strings[1]);
                NetworkPlayer networkPlayer = NetworkApi.getNetworkApi().getNetworkPlayerCache().getPlayerByUuid(offlinePlayer.getUniqueId());
                if (networkPlayer.existsPlayer(offlinePlayer.getUniqueId())){
                    if (strings[0].equalsIgnoreCase("addcoins")){
                        networkPlayer.addCoins(coins);
                        player.sendMessage("Eingabe: " + coins + " Stand: " + networkPlayer.getCoins());
                    }else if (strings[0].equalsIgnoreCase("removecoins")){
                        networkPlayer.removeCoins(coins);
                        player.sendMessage("Eingabe: " + coins + " Stand: " + networkPlayer.getCoins());
                    }else if (strings[0].equalsIgnoreCase("setcoins")){
                        networkPlayer.setCoins(coins);
                        player.sendMessage("Eingabe: " + coins + " Stand: " + networkPlayer.getCoins());
                    }
                }else{
                    player.sendMessage("Gibt es nicht");
                }
            }

        }else if (strings.length == 2){
            Player target = Bukkit.getPlayer(strings[1]);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyy HH:mm:ss");
            if (target != null){
                NetworkPlayer networkPlayer = NetworkApi.getNetworkApi().getNetworkPlayerCache().getPlayerByUuid(target.getUniqueId());
                if (networkPlayer.existsPlayer(target.getUniqueId())){
                    if (strings[0].equalsIgnoreCase("playerinfo")){
                        player.sendMessage("UUID: " + networkPlayer.getUuid());
                        player.sendMessage("Name: " + target.getName());
                        player.sendMessage("Coins: " + networkPlayer.getCoins());
                        player.sendMessage("FirstJoin: " + simpleDateFormat.format(networkPlayer.getFirstJoin()));
                        player.sendMessage("LastJoin: " + networkPlayer.getLastJoin());
                    }
                }else{
                    player.sendMessage("Gibt es nicht");
                }
            }else{
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(strings[1]);
                NetworkPlayer networkPlayer = NetworkApi.getNetworkApi().getNetworkPlayerCache().getPlayerByUuid(offlinePlayer.getUniqueId());
                if (networkPlayer.existsPlayer(offlinePlayer.getUniqueId())){
                    if (strings[0].equalsIgnoreCase("playerinfo")){
                        player.sendMessage("UUID: " + networkPlayer.getUuid());
                        player.sendMessage("Name: " + offlinePlayer.getName());
                        player.sendMessage("Coins: " + networkPlayer.getCoins());
                        player.sendMessage("FirstJoin: " + networkPlayer.getFirstJoin());
                        player.sendMessage("LastJoin: " + networkPlayer.getLastJoin());
                    }
                }else{
                    player.sendMessage("Gibt es nicht");
                }
            }
        }else if (strings.length == 1){
            if (strings[0].equalsIgnoreCase("serverid")){
                ServiceId serviceId = NetworkApi.getNetworkApi().getServiceId();
                if (serviceId == null){
                    player.sendMessage("Es konnten keine Informationen gefunden werden");
                }else{
                    String name = serviceId.getName();
                    UUID uniqueId = serviceId.getUniqueId();
                    String node = serviceId.getNodeUniqueId();
                    player.sendMessage("Name: " + name);
                    player.sendMessage("UUID: " + uniqueId);
                    player.sendMessage("Node: " + node);
                }
            }
        }

        return false;
    }
}
