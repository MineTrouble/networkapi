package de.minetrouble.networkapi.manager.prefix;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/**
 * @author KeinByte
 * @since 23.06.2022
 */
public class Prefix {

    private LuckPerms luckPerms = LuckPermsProvider.get();

    public Prefix(Player player) {
        setScoreboard(player);
    }

    public String getPrefix(Player player) {
        User user = this.luckPerms.getUserManager().getUser(player.getUniqueId());
        QueryOptions queryOptions = this.luckPerms.getContextManager().getStaticQueryOptions();
        CachedMetaData cachedMetaData = user.getCachedData().getMetaData(queryOptions);
        String prefix = cachedMetaData.getPrefix().replaceAll("&", "§");
        return prefix;
    }

    public String getTeam(Player player) {
        User user = this.luckPerms.getUserManager().getUser(player.getUniqueId());
        QueryOptions queryOptions = this.luckPerms.getContextManager().getStaticQueryOptions();
        CachedMetaData cachedMetaData = user.getCachedData().getMetaData(queryOptions);
        String team = cachedMetaData.getMetaValue("team");
        return team;
    }

    public void setScoreboard(Player player) {
        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            String teamString = getTeam(onlinePlayers);
            for (Player players : Bukkit.getOnlinePlayers()) {
                Scoreboard scoreboard = players.getScoreboard();
                Team team = (scoreboard.getTeam(teamString) == null) ? scoreboard.registerNewTeam(teamString) : scoreboard.getTeam(teamString);
                team.setPrefix(getPrefix(onlinePlayers));
                team.addEntry(onlinePlayers.getName());
                players.setScoreboard(scoreboard);
            }
        }
        String prefix = getPrefix(player);
        player.setDisplayName(prefix + player.getName());
        player.setPlayerListName(prefix + player.getName());
    }
}
