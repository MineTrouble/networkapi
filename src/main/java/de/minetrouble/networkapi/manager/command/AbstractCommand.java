package de.minetrouble.networkapi.manager.command;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author KeinByte
 * @since 22.06.2022
 */
public abstract class AbstractCommand implements CommandExecutor, TabExecutor {

    protected final String command;
    protected final String description;
    protected final List<String> alias;
    protected final String usage;
    protected final String permission;
    protected final boolean active;
    protected static CommandMap commandMap;

    public AbstractCommand(String command, String permission, String description, String usage, boolean active, List<String> alias){
        this.command = command.toLowerCase();
        this.permission = permission;
        this.description = description;
        this.usage = usage;
        this.active = active;
        this.alias = alias;
    }

    public void register(){
        ReflectCommand reflectCommand = new ReflectCommand(this.command);
        if (this.active){
            if (this.alias != null){
                reflectCommand.setAliases(this.alias);
            }
            if (this.description != null){
                reflectCommand.setDescription(this.description);
            }
            if (this.permission != null){
                reflectCommand.setPermission(this.permission);
            }
            if (this.usage != null){
                reflectCommand.setUsage(this.usage);
            }
            reflectCommand.setPermissionMessage("Keine Rechte");
            this.getCommandMap().register("", reflectCommand);
            reflectCommand.setExecutor(this);
        }
    }

    final CommandMap getCommandMap() {
        if (commandMap == null) {
            try {
                Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                f.setAccessible(true);
                commandMap = (CommandMap)f.get(Bukkit.getServer());
                return this.getCommandMap();
            } catch (Exception var2) {
                var2.printStackTrace();
            }
        } else if (commandMap != null) {
            return commandMap;
        }

        return this.getCommandMap();
    }

    public abstract boolean onCommand(CommandSender commandSender, Command command, String label, String[] strings);

    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] strings){
        return null;
    }

    private final class ReflectCommand extends Command {
        private AbstractCommand exe = null;

        protected ReflectCommand(String command) {
            super(command);
        }

        public void setExecutor(AbstractCommand exe) {
            this.exe = exe;
        }

        public boolean execute(CommandSender sender, String commandLabel, String[] args) {
            return this.exe != null ? this.exe.onCommand(sender, this, commandLabel, args) : false;
        }

        public List<String> tabComplete(CommandSender sender, String alais, String[] args) {
            return this.exe != null ? this.exe.onTabComplete(sender, this, alais, args) : null;
        }
    }

}
