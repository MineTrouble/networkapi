package de.minetrouble.networkapi.manager.customevents;

import de.minetrouble.networkapi.manager.settings.Settings;
import de.minetrouble.networkapi.manager.settings.SettingsValue;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * @author KeinByte
 * @since 03.07.2022
 */
public class PlayerSettingsChangeEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Settings settings;
    private SettingsValue settingsValue;
    private boolean cancelled = false;

    public PlayerSettingsChangeEvent(Player player, Settings settings, SettingsValue settingsValue) {
        this.player = player;
        this.settings = settings;
        this.settingsValue = settingsValue;
    }

    public Player getPlayer() {
        return player;
    }

    public Settings getSetting() {
        return settings;
    }

    public SettingsValue getNewValue() {
        return settingsValue;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
