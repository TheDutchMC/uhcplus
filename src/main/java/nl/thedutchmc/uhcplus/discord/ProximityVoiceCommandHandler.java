package nl.thedutchmc.uhcplus.discord;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.md_5.bungee.api.ChatColor;

public class ProximityVoiceCommandHandler implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		// /discord <subcommand>
		if(args.length > 0) {			
			Player player = (Player) sender;
			
			JDA jda = JdaSetup.getJda();
			
			System.out.println(args[0]);
			System.out.println(jda);

			try {
				User user = jda.getUserByTag(args[0]);
				
				if(user != null) {
					String userId = user.getId();
					ModuleProximityVoice.discordNicknames.put(player.getUniqueId(), userId);
					
					sender.sendMessage(ChatColor.GOLD + "Thank you! Your discord name has been added to your player profile.");
				} else {
					sender.sendMessage(ChatColor.RED + "Invalid username!");
				}
			} catch(IllegalArgumentException e) {
				player.sendMessage(ChatColor.RED + "Invalid tag format! Make sure capitalization is correct and that you include your 4 digit tag!");
			}
			
			
			
		// /discord <nosubcommand>
		} else {
			sender.sendMessage(ChatColor.RED + "Missing Discord username!");
		}
		
		return true;
	}
}
