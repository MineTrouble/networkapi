package de.minetrouble.networkapi.apicommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * @author KeinByte
 * @since 23.06.2022
 */
public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        try {
            Class<?> clazz = Class.forName(strings[0]);
            commandSender.sendMessage("Klasse kommt aus " + clazz.getProtectionDomain().getCodeSource().getLocation().getPath());
        }catch (ClassNotFoundException exception){
            commandSender.sendMessage("Klasse gibts nicht.");
        }

        return false;
    }
}
