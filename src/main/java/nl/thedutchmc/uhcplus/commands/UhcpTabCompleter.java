package nl.thedutchmc.uhcplus.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import nl.thedutchmc.uhcplus.ConfigurationHandler;

public class UhcpTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		
		if(args.length == 1) {
			List<String> possibleCommands = new ArrayList<>();
			possibleCommands.add("help");
			possibleCommands.add("version");
			possibleCommands.add("preset");
			
			return possibleCommands;
			
		} else if(args.length == 2) {
			
			if(args[0].equalsIgnoreCase("preset")) {
				
				List<String> possibleCommands = new ArrayList<>();
				possibleCommands.add("create");
				possibleCommands.add("list");
				possibleCommands.add("setdefault");
				possibleCommands.add("load");
				possibleCommands.add("seeloaded");
				possibleCommands.add("delete");
				possibleCommands.add("options");
				
				return possibleCommands;	
			}
		} else if(args.length == 3) {
			
			if(args[0].equalsIgnoreCase("preset")) {
				
				if(args[1].equalsIgnoreCase("setdefault") || args[1].equalsIgnoreCase("load") || args[1].equalsIgnoreCase("delete")) {
					return ConfigurationHandler.availablePresets;
					
				} else if (args[1].equalsIgnoreCase("options")) {
					List<String> availableCommands = new ArrayList<>();
					availableCommands.add("list");
					availableCommands.add("maxTeamCount");
					availableCommands.add("maxPlayersPerTeam");
					availableCommands.add("moduleOreAutoSmelt");
					availableCommands.add("ingotDropCount");

					return availableCommands;
				}
			}
		}
		
		List<String> emptyList = new ArrayList<>();
		emptyList.add("");
		return emptyList;
	}

}
