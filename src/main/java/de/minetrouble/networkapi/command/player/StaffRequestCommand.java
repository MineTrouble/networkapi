package de.minetrouble.networkapi.command.player;

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
 * @since 26.06.2022
 */
public class StaffRequestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Player player = (Player) commandSender;

        if (strings.length == 1){
            Player target = Bukkit.getPlayer(strings[0]);
            assert target != null;
            NetworkPlayer networkPlayer = NetworkApi.getNetworkApi().getNetworkPlayerCache().getPlayerByUuid(target.getUniqueId());
            if (networkPlayer.existsPlayer()){
                NetworkApi.getNetworkApi().getStaffRequest().checkPlayerForStaff(target, player);
            }
        }

        return false;
    }
}
