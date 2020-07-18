package nl.thedutchmc.uhcplus.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BroadcastCommandHandler implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (command.getName().equalsIgnoreCase("broadcast")) {

			if (!sender.hasPermission("uhcp.broadcast")) {
				sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
				return true;
			}

			if (args.length > 0) {

				String message = "";

				for (String argument : args) {
					message += argument + " ";
				}

				for (Player player : Bukkit.getServer().getOnlinePlayers()) {

					player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "[Broadcast] " + message);
				}

			} else {
				sender.sendMessage(ChatColor.RED + "Missing argument!");
			}
		}

		return true;
	}

}
