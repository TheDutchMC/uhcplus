package nl.thedutchmc.uhcplus.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import nl.thedutchmc.uhcplus.ConfigurationHandler;

public class UhcpTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		
		if(args.length == 1) {
			List<String> possibleCommands = new ArrayList<>();
			possibleCommands.add("help");
			possibleCommands.add("version");
			possibleCommands.add("preset");
			possibleCommands.add("teams");
			possibleCommands.add("start");
			
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
				
			} else if(args[0].equalsIgnoreCase("teams")) {
				
				List<String> possibleCommands = new ArrayList<>();
				possibleCommands.add("help");
				possibleCommands.add("randomfill");
				possibleCommands.add("getteams");
				possibleCommands.add("whichteam");
				
				return possibleCommands;
				
			}
		} else if(args.length == 3) {
			
			if(args[0].equalsIgnoreCase("preset")) {
				
				if(args[1].equalsIgnoreCase("setdefault") || args[1].equalsIgnoreCase("load") || args[1].equalsIgnoreCase("delete")) {
					return ConfigurationHandler.availablePresets;
					
				} else if (args[1].equalsIgnoreCase("options")) {
					List<String> possibleCommands = new ArrayList<>();
					
					//Mostly the boolean variables
					possibleCommands.add("list");
					possibleCommands.add("maxTeamCount");
					possibleCommands.add("maxPlayersPerTeam");
					possibleCommands.add("moduleOreAutoSmelt");
					possibleCommands.add("moduleTreeFullRemove");
					possibleCommands.add("moduleLeaveDecay");
					possibleCommands.add("moduleEnchantedTools");
					possibleCommands.add("moduleInfiniteEnchanting");
					possibleCommands.add("moduleSheepDropString");
					possibleCommands.add("moduleGravelDropArrow");
					possibleCommands.add("moduleDissalowGrindingEnchantedTools");
					possibleCommands.add("moduleLightGoldenApple");
					possibleCommands.add("moduleLightAnvil");
					possibleCommands.add("moduleDioriteDamage");
					possibleCommands.add("moduleAntiCheat");
					possibleCommands.add("moduleAxeOfDestruction");
					possibleCommands.add("axeOfDestructionLevelling");
					possibleCommands.add("moduleSwordOfDivinity");
					possibleCommands.add("swordOfDivinityLevelling");
					possibleCommands.add("moduleTeamInventory");
					
					//Most of the integer variables
					possibleCommands.add("ingotDropCount");
					possibleCommands.add("timeToPvp");
					possibleCommands.add("worldBorderSize");
					possibleCommands.add("worldBorderShrinkAfter");
					possibleCommands.add("worldBorderShrinkTo");
					possibleCommands.add("gameTime");
					possibleCommands.add("moduleAntiCheatTime");
					possibleCommands.add("moduleAxeOfDestructionLevelOneTime");
					possibleCommands.add("moduleAxeOfDestructionLevelTwoTime");
					possibleCommands.add("moduleSwordOfDivinityLevelOneTime");
					possibleCommands.add("moduleSwordOfDivinityLevelTwoTime");

					return possibleCommands;
				}
			} else if(args[0].equalsIgnoreCase("teams")) {
				if(args[1].equalsIgnoreCase("whichteam")) {
					Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().toArray().length];
					
					Bukkit.getServer().getOnlinePlayers().toArray(players);
					
					List<String> playerNames = new ArrayList<>();
					for(Player player : players) {
						playerNames.add(player.getName());
					}
					
					return playerNames;
				}
			}
		}
		
		List<String> emptyList = new ArrayList<>();
		emptyList.add("");
		return emptyList;
	}

}
