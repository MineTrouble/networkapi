package de.minetrouble.networkapi.manager.command.staffrequest;

import net.pretronic.dkbans.api.DKBans;
import net.pretronic.dkbans.api.player.history.PunishmentType;
import org.bukkit.entity.Player;

/**
 * @author KeinByte
 * @since 26.06.2022
 */
public class StaffRequest {

    private static final DKBans dkBans = DKBans.getInstance();

    public void checkPlayerForStaff(Player player, Player staff) {
        if (dkBans.getPlayerManager().getPlayer(player.getUniqueId()).getHistory().getPlayer().hasActivePunish(PunishmentType.BAN)) {
            staff.sendMessage("§cDie Person, erfüllt nicht die Bedingungen, um in das Team aufgenommen zu werden.");
            return;
        }
        if (dkBans.getPlayerManager().getPlayer(player.getUniqueId()).getHistory().getPlayer().hasActivePunish(PunishmentType.MUTE)) {
            staff.sendMessage("§cDie Person, erfüllt nicht die Bedingungen, um in das Team aufgenommen zu werden.");
            return;
        }
        if (dkBans.getPlayerManager().getPlayer(player.getUniqueId()).getOnlineTime() < 10) {
            staff.sendMessage("§cDie Person, erfüllt nicht die Bedingungen, um in das Team aufgenommen zu werden.");
            return;
        }
        staff.sendMessage("§aDie Person, erfüllt die Bedingungen, um in das Team aufgenommen zu werden.");
    }

}
