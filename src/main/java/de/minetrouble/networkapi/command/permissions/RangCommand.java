package de.minetrouble.networkapi.command.permissions;

import de.minetrouble.networkapi.manager.command.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author KeinByte
 * @since 22.06.2022
 */
public class RangCommand extends AbstractCommand {

    public RangCommand(String command, String permission, String description, String usage, boolean active, List<String> alias) {
        super(command, permission, description, usage, active, alias);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] strings) {
        return false;
    }
}
