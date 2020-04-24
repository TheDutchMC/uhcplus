package nl.thedutchmc.uhcplus;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandHandler implements CommandExecutor {


	@SuppressWarnings("unused")
	private UhcPlus plugin;

	public CommandHandler(UhcPlus plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		//Check if the entered command is equal to uhcp, because this is our base command we dont need to handle anything else
		if(command.getName().equalsIgnoreCase("uhcp")) {
			
			//Check if the player gave any arguments ('subcommands'), if none are given, they just entered /uhcp, show them how to get the help page.
			if(args.length == 0) {
				sender.sendMessage(ChatColor.RED + "Invalid usage! See /uhcp help for help.");
			} else {
				
				//Check if args[0] (the first 'subcommand') is help, if so send the player the help page
				if(args[0].equalsIgnoreCase("help")) {
					
					ChatColor cg = ChatColor.GOLD;
					ChatColor cw = ChatColor.WHITE;
					
					sender.sendMessage(cg + "UHCPlus Help Page");
					sender.sendMessage(cg + "---------------");
					sender.sendMessage("- " + cg + "/uhcp: " + cw + "UHCPlus base command.");
					sender.sendMessage("- " + cg + "/uhcp help: " + cw + "Shows this page.");
					sender.sendMessage("- " + cg + "/uhcp version: " + cw + "Shows you the version of UHCPlus you are on.");
					
					return true;
				} else if(args[0].equalsIgnoreCase("version")) { //Returns the version to the sender
					
					sender.sendMessage(ChatColor.GOLD + "You are on UHCPlus version " + ChatColor.RED + UhcPlus.VERSION + ChatColor.GOLD + ".");
					return true;
				}
			}
		}
		
		return false;
	}
}
