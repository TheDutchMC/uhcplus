package nl.thedutchmc.uhcplus.commands.subcommands;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.modules.modules.kits.Kit;
import nl.thedutchmc.uhcplus.modules.modules.kits.KitHandler;

public class Kits {

	public boolean kitCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(!(args.length >= 2)) {
			sender.sendMessage(ChatColor.RED + "Missing argument! See /uhcp kits help for available commands!");
			return true;
		}
		
		
		//uhcp kits create <kitname>
		if(args[1].equalsIgnoreCase("create")) {
			
			//Check if a name was given
			if(!(args.length >= 3)) {
				sender.sendMessage(ChatColor.RED + "Missing arguments! See /uhcp kits help!");
				return true;
			}
			
			Player p = (Player) sender;
			Inventory i = p.getInventory();
			
			//Check if the kit already exists
			if(KitHandler.getKit(args[2]) != null) {
				sender.sendMessage(ChatColor.RED + "This kit already exists!");
				return true;
			}
			
			//Create the new kit
			KitHandler.newKit(args[2], Arrays.asList(i.getContents()));
			
			sender.sendMessage(ChatColor.GOLD + "New kit " + ChatColor.RED + args[2] + ChatColor.GOLD + " has been created!");
			return true;
		}
		
		//uhcp kits modify <kitname>
		if(args[1].equalsIgnoreCase("modify")) {
			
			//Check if a kit name was given
			if(!(args.length >= 3)) {
				sender.sendMessage(ChatColor.RED + "Missing arguments! See /uhcp kits help!");
				return true;
			}
			
			Player p = (Player) sender;
			Inventory i = p.getInventory();
			
			//Check if the kit does not exist (ie there's nothing to modify)
			if(KitHandler.getKit(args[2]) == null) {
				sender.sendMessage(ChatColor.RED + "This kit does not exist! You have to create it first with /uhcp kits create <kitname>");
				return true;
			}
			
			//Modify the kit
			KitHandler.modifyKit(args[2], Arrays.asList(i.getContents()));
			
			sender.sendMessage(ChatColor.GOLD + "Kit " + ChatColor.RED + args[2] + ChatColor.GOLD + " has been modified!");
			return true;
		}
		
		//uhcp kits list
		if(args[1].equalsIgnoreCase("list")) {
			
			sender.sendMessage(ChatColor.GOLD + "List of all kits");
			sender.sendMessage(ChatColor.GOLD + "--------------");
			
			for(Kit k : KitHandler.kits) {
				sender.sendMessage(ChatColor.GOLD + "- " + k.getKitName());
			}
			
			return true;
		}
		
		//uhcp kits remove <kitname>
		if(args[1].equalsIgnoreCase("remove")) {
			if(!(args.length >= 3)) {
				sender.sendMessage(ChatColor.RED + "Missing arguments! See /uhcp kits help!");
				return true;
			}
			
			if(KitHandler.getKit(args[2]) == null) {
				sender.sendMessage(ChatColor.RED + "This kit does not exist! Cannot remove a nonexistant kit!");
				return true;
			}
			
			KitHandler.removeKit(args[2]);
			
			sender.sendMessage(ChatColor.GOLD + "The kit " + ChatColor.RED + args[2] + ChatColor.GOLD + " was removed successfully!");
			return true;
			
		}
		
		//uhcp kits help
		if(args[1].equalsIgnoreCase("help")) {
			ChatColor cg = ChatColor.GOLD;
			ChatColor cw = ChatColor.WHITE;
			
			sender.sendMessage(cg + "Kits help menu");
			sender.sendMessage(cg + "--------------");
			sender.sendMessage("- " + cg + "/uhcp kits create <kit name>" + cw + " Create a kit with the given name, the kit will have the items in your inventory as the kit items");
			sender.sendMessage("- " + cg + "/uhcp kits modify <kit name>" + cw + " Modify a kit with the given name to have the items in your inventory to be the kit items");
			sender.sendMessage("- " + cg + "/uhcp kits list" + cw + " Shows a list of all kits");
			sender.sendMessage("- " + cg + "/uhcp kits help" + cw + " Shows this page");
			sender.sendMessage("- " + cg + "/uhcp kits remove <kit name>" + cw + " Remove the kit with the given name");
			
			return true;
		}
		
		sender.sendMessage(ChatColor.RED + "Unknown option. Use /uhcp kits help for a list of available commands!");
		return true;
	}
	
}
