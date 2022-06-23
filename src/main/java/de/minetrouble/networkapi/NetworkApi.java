package de.minetrouble.networkapi;

import de.dytanic.cloudnet.driver.service.ServiceId;
import de.minetrouble.networkapi.command.apicommand.MineApiCommand;
import de.minetrouble.networkapi.listener.PlayerJoinListener;
import de.minetrouble.networkapi.manager.cloudnet.ServicePlayerCounting;
import de.minetrouble.networkapi.manager.command.AbstractCommand;
import de.minetrouble.networkapi.manager.database.MySQLDatabaseEntry;
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
    @Getter private NetworkPlayer networkPlayer;
    @Getter private NetworkPlayerCache networkPlayerCache;
    @Getter private ServiceId serviceId;
    @Getter private ServicePlayerCounting servicePlayerCounting;

    @Override
    public void onLoad() {
        networkApi = this;
    }

    @Override
    public void onEnable() {
        registerConfig();
        registerDatabase();
        registerCommand();
        getCommand("test").setExecutor(this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        networkPlayerCache = new NetworkPlayerCache();
    }

    private void registerCommand(){
        AbstractCommand abstractCommand = new MineApiCommand();
        abstractCommand.register();
    }

    private void registerDatabase(){
        this.mySQLDatabaseEntry = new MySQLDatabaseEntry();
        this.mySQLDatabaseEntry.create();
    }

    private void registerConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }

}
