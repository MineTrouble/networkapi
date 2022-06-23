package de.minetrouble.networkapi.manager.cloudnet;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.service.ServiceInfoSnapshot;
import de.dytanic.cloudnet.ext.bridge.BridgeServiceProperty;

/**
 * @author KeinByte
 * @since 23.06.2022
 */
public class ServicePlayerCounting {

    public int countPlayer(String cloudService){
        int players = 0;
        for (ServiceInfoSnapshot serviceInfoSnapshot : CloudNetDriver.getInstance().getCloudServiceProvider().getCloudServices(cloudService)){
            players += serviceInfoSnapshot.getProperty(BridgeServiceProperty.ONLINE_COUNT).orElse(0);
        }

        return players;
    }

}
