package de.minetrouble.networkapi.manager.database;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import net.pretronic.databasequery.api.Database;
import net.pretronic.databasequery.api.collection.DatabaseCollection;
import net.pretronic.databasequery.api.collection.field.FieldOption;
import net.pretronic.databasequery.api.datatype.DataType;
import net.pretronic.databasequery.api.driver.DatabaseDriver;
import net.pretronic.databasequery.api.driver.DatabaseDriverFactory;
import net.pretronic.databasequery.sql.dialect.Dialect;
import net.pretronic.databasequery.sql.driver.config.SQLDatabaseDriverConfigBuilder;

import java.net.InetSocketAddress;

/**
 * @author KeinByte
 * @since 22.06.2022
 */
public class MySQLDatabaseEntry {

    private DatabaseDriver databaseDriver;
    private Database database;

    @Getter private DatabaseCollection playerCollection;
    @Getter private DatabaseCollection logCollection;

    public void create(){
        databaseDriver = DatabaseDriverFactory.create("network_api", new SQLDatabaseDriverConfigBuilder()
                .setDialect(Dialect.MYSQL)
                .setAddress(new InetSocketAddress("localhost", 3306))
                .setDataSourceClassName(HikariDataSource.class.getName())
                .setUsername("root")
                .setPassword("_]tUCNxck3tQ4gFk")
                .build());
        databaseDriver.connect();
        database = databaseDriver.getDatabase("network_api");
        createPlayerCollection();
        createLogCollection();
    }

    public void delete(){
        databaseDriver.disconnect();
    }

    private boolean isConnected(){
        return databaseDriver.isConnected();
    }

    public void createPlayerCollection(){
        playerCollection = database.createCollection("network_player")
                .field("id", DataType.INTEGER, FieldOption.PRIMARY_KEY, FieldOption.AUTO_INCREMENT)
                .field("uuid", DataType.UUID)
                .field("firstJoin", DataType.LONG)
                .field("lastJoin", DataType.LONG)
                .field("coins", DataType.LONG)
                .create();
    }

    public void createLogCollection(){
        logCollection = database.createCollection("network_log")
                .field("id", DataType.INTEGER, FieldOption.PRIMARY_KEY, FieldOption.AUTO_INCREMENT)
                .field("uuidTeam", DataType.UUID)
                .field("uuidPlayer", DataType.UUID)
                .field("type", DataType.STRING)
                .field("amount", DataType.LONG)
                .field("reason", DataType.STRING)
                .field("time", DataType.LONG)
                .create();
    }

}