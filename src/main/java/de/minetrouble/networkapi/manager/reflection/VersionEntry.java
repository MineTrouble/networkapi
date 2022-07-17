package de.minetrouble.networkapi.manager.reflection;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;

/**
 * @author KeinByte
 * @since 14.07.2022
 */
public class VersionEntry {
    public static final int BUKKIT_VERSION;

    public static final long SECOND_INTERVAL_NANOS = 1000000000L;

    public static boolean PLACEHOLDER_SUPPORT = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");

    static {
        BUKKIT_VERSION = NumberUtils.toInt(getFormattedBukkitPackage());
    }

    public static boolean versionNewer(int version) {
        return (BUKKIT_VERSION >= version);
    }

    public static String getBukkitPackage() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    public static String getFormattedBukkitPackage() {
        String version = getBukkitPackage().replace("v", "").replace("R", "");
        return version.substring(2, version.length() - 2);
    }


}
