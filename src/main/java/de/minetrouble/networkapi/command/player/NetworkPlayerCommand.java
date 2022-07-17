package de.minetrouble.networkapi.command.player;

import de.minetrouble.networkapi.NetworkApi;
import de.minetrouble.networkapi.manager.chat.ChatPrefix;
import de.minetrouble.networkapi.manager.command.luckperms.LuckPermsUtility;
import de.minetrouble.networkapi.manager.player.NetworkPlayer;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;

/**
 * @author KeinByte
 * @since 08.07.2022
 */
public class NetworkPlayerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Player player = (Player) commandSender;
        ChatPrefix chatPrefix = NetworkApi.getNetworkApi().getChatPrefix();

        if (!player.hasPermission("network.playerinfo")) {
            player.sendMessage(chatPrefix.getNoRights());
            return true;
        }

        if (strings.length != 1) {
            player.sendMessage(chatPrefix.getUsage() + "networkplayer <Spieler>");
            return true;
        }

        Player target = Bukkit.getPlayer(strings[0]);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyy HH:mm:ss");

        if (target != null){
            User user = LuckPermsUtility.getLuckPermsUser(target.getUniqueId());
            NetworkPlayer networkPlayer = NetworkApi.getNetworkApi().getNetworkPlayerCache().getPlayerByUuid(target.getUniqueId());
            if (networkPlayer.existsPlayer()){
                player.sendMessage("§8============[§6PlayerInfo§8]============");
                player.sendMessage("§7Name: §6" + target.getName());
                player.sendMessage("§7UniqueId: §6" + target.getUniqueId());
                player.sendMessage("§7First-Join: §6" + simpleDateFormat.format(networkPlayer.getFirstJoin()));
                player.sendMessage("§7Last-Join: §6" + simpleDateFormat.format(networkPlayer.getLastJoin()));
                if (player.hasPermission("network.playerinfo.admin")){
                    player.sendMessage("§7Ip: §6" + target.getAddress());
                }
                if (user != null){
                    player.sendMessage("§7Rang:");
                    LuckPermsUtility.getUserGroups(user).forEach(group -> player.sendMessage("§7- §6" + group.getName()));
                }
                player.sendMessage("§8============[§6PlayerInfo§8]============");
            }else{
                player.sendMessage("Existiert nicht");
            }
        }

        return false;
    }
}
