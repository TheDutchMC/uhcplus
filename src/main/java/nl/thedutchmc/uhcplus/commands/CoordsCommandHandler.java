package nl.thedutchmc.uhcplus.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.teams.Team;
import nl.thedutchmc.uhcplus.teams.TeamHandler;

public class CoordsCommandHandler implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		Player p = (Player) sender;

		if (!sender.hasPermission("uhcp.coords")) {
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
			return true;
		}

		if (!UhcPlus.UHC_STARTED) {
			sender.sendMessage(ChatColor.RED + "This command may only be used during the UHC!");
			return true;
		}

		if (!p.getGameMode().equals(GameMode.SURVIVAL)) {
			sender.sendMessage(ChatColor.RED + "This command may only be used by playing players!");
			return true;
		}

		for (Team team : TeamHandler.getAliveTeams()) {
			if (team.getAliveTeamMembers().contains(p.getUniqueId())) {

				String message = ChatColor.AQUA + "[Team] " + ChatColor.GOLD + "Coordinates from Team member "
						+ ChatColor.RED + p.getName() + ChatColor.GOLD + ": X: " + ChatColor.RED
						+ p.getLocation().getX() + ChatColor.GOLD + " Y: " + ChatColor.RED + p.getLocation().getY()
						+ ChatColor.GOLD + " Z: " + ChatColor.RED + p.getLocation().getZ();

				for (UUID uuid : team.getAliveTeamMembers()) {
					Player member = Bukkit.getPlayer(uuid);
					member.sendMessage(message);
				}

				break;
			}
		}

		return true;
	}

}
