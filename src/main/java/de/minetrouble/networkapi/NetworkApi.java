package de.minetrouble.networkapi;

import de.dytanic.cloudnet.driver.service.ServiceId;
import de.minetrouble.networkapi.command.apicommand.MineApiCommand;
import de.minetrouble.networkapi.command.player.NetworkPlayerCommand;
import de.minetrouble.networkapi.command.player.StaffRequestCommand;
import de.minetrouble.networkapi.listener.PlayerJoinListener;
import de.minetrouble.networkapi.manager.caseopening.CasePlayerCache;
import de.minetrouble.networkapi.manager.cloudnet.ServicePlayerCounting;
import de.minetrouble.networkapi.manager.color.ColorBuilder;
import de.minetrouble.networkapi.manager.command.AbstractCommand;
import de.minetrouble.networkapi.manager.command.staffrequest.StaffRequest;
import de.minetrouble.networkapi.manager.database.MySQLDatabaseEntry;
import de.minetrouble.networkapi.manager.database.MySQLEntry;
import de.minetrouble.networkapi.manager.player.NetworkPlayer;
import de.minetrouble.networkapi.manager.player.NetworkPlayerCache;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author KeinByte
 * @since 22.06.2022
 */
public class NetworkApi extends JavaPlugin {

    @Getter private static NetworkApi networkApi;
    @Getter private MySQLDatabaseEntry mySQLDatabaseEntry;
    @Getter private MySQLEntry mySQLEntry;
    @Getter private NetworkPlayer networkPlayer;
    @Getter private NetworkPlayerCache networkPlayerCache;
    @Getter private CasePlayerCache casePlayerCache;
    @Getter private ServiceId serviceId;
    @Getter private ServicePlayerCounting servicePlayerCounting;
    @Getter private StaffRequest staffRequest;
    @Getter private ColorBuilder colorBuilder;

    @Override
    public void onLoad() {
        networkApi = this;
    }

    @Override
    public void onEnable() {
        registerConfig();
        registerDatabase();
        getCommand("staffrequest").setExecutor(new StaffRequestCommand());
        getCommand("networkplayer").setExecutor(new NetworkPlayerCommand());
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        networkPlayerCache = new NetworkPlayerCache();
        casePlayerCache = new CasePlayerCache();
    }

    private void registerCommand(){
        AbstractCommand abstractCommand = new MineApiCommand();
        abstractCommand.register();
    }

    private void registerDatabase(){
        this.mySQLDatabaseEntry = new MySQLDatabaseEntry();
        this.mySQLDatabaseEntry.create();

        this.mySQLEntry = new MySQLEntry();
        this.mySQLEntry.connect();
    }

    private void registerConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }

}
