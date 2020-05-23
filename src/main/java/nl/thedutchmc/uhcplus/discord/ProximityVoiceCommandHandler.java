package nl.thedutchmc.uhcplus.discord;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class ProximityVoiceCommandHandler implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		// /discord <subcommand>
		if(args.length > 0) {			
			Player player = (Player) sender;
			
			ModuleProximityVoice.discordNicknames.put(player.getUniqueId(), args[0]);
			
			sender.sendMessage(ChatColor.GOLD + "Thank you! Your discord name has been added to your player profile.");
			
		// /discord <nosubcommand>
		} else {
			sender.sendMessage(ChatColor.RED + "Missing Discord nickname!");
		}
		
		return true;
	}
}
