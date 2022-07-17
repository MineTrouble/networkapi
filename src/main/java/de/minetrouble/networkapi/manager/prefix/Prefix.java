package de.minetrouble.networkapi.manager.prefix;

import de.minetrouble.networkapi.manager.command.luckperms.LuckPermsUtility;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.List;

/**
 * @author KeinByte
 * @since 15.07.2022
 */
public class Prefix implements IPrefix{

    private Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    @Override
    public void applyPrefix(Player player) {
        User user = LuckPermsUtility.getLuckPermsUser(player.getUniqueId());
        Group group = LuckPermsProvider.get().getGroupManager().getGroup(user.getPrimaryGroup());
        String id = ((List<String>)group.getCachedData().getMetaData().getMeta().get("team")).get(0);
        Team team = (this.scoreboard.getTeam(id + group.getName()) != null) ? this.scoreboard.getTeam(id + group.getName()) : this.scoreboard.registerNewTeam(id + group.getName());
        team.setPrefix(ChatColor.translateAlternateColorCodes('&', getPlayerPrefix(player)));
        if (user.getCachedData().getMetaData().getSuffix() != null)
            team.setSuffix(ChatColor.translateAlternateColorCodes('&', user.getCachedData().getMetaData().getSuffix()));
        team.addEntry(player.getName());
        player.setDisplayName(ChatColor.translateAlternateColorCodes('&', ((List<String>)user.getCachedData().getMetaData().getMeta().get("display")).get(0)) + player.getName());
        for (Player all : Bukkit.getOnlinePlayers())
            all.setScoreboard(this.scoreboard);
    }

    @Override
    public String getPlayerPrefix(Player player) {
        User user = LuckPermsUtility.getLuckPermsUser(player.getUniqueId());
        CachedMetaData cachedMetaData = user.getCachedData().getMetaData();
        return ChatColor.translateAlternateColorCodes('&', cachedMetaData.getPrefix());
    }
}
