package nl.thedutchmc.uhcplus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.presets.PresetHandler;
import nl.thedutchmc.uhcplus.teams.Team;
import nl.thedutchmc.uhcplus.teams.TeamHandler;
import nl.thedutchmc.uhcplus.uhc.UhcHandler;

public class TeamInventoryCommandHandler implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("uhcp.teaminventory")) {
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
			return true;
		}

		if (!UhcHandler.isPlaying()) {
			sender.sendMessage(ChatColor.RED + "The UHC hasn't started yet!");
			return true;
		}

		if (!(boolean) PresetHandler.getPrefabOption("moduleTeamInventory")) {
			sender.sendMessage(ChatColor.RED + "This module is not enabled!");
			return true;
		}

		Player player = (Player) sender;

		for (Team team : TeamHandler.teams) {
			if (team.getTeamMembers().contains(player.getUniqueId())) {
				sender.sendMessage(ChatColor.GOLD + "Opening Team Inventory...");

				HumanEntity ent = (HumanEntity) sender;
				team.getTeamInventory().openGui(ent);
				return true;
			}
		}

		return false;
	}

}
