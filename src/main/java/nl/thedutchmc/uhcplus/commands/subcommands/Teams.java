package nl.thedutchmc.uhcplus.commands.subcommands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.presets.PresetHandler;
import nl.thedutchmc.uhcplus.teams.Team;
import nl.thedutchmc.uhcplus.teams.TeamHandler;

public class Teams {

	private UhcPlus plugin;
	
	public Teams(UhcPlus plugin) {
		this.plugin = plugin;
	}
	
	public boolean teamsSubcommand(CommandSender sender, Command command, String label, String[] args) {
		
		ChatColor cr = ChatColor.RED;
		ChatColor cg = ChatColor.GOLD;
		ChatColor cw = ChatColor.WHITE;
		
		if(args.length >= 2) {
			
			
			//uhcp teams help
			if(args[1].equalsIgnoreCase("help")) {
				
				sender.sendMessage(cg + "UHCPlus Teams Help Page");
				sender.sendMessage(cg + "---------------------");
				sender.sendMessage("- " + cg + "/uchp teams teamCount " + cw + "Show the amount of teams configured");
				sender.sendMessage("- " + cg + "/uhcp teams randomfill " + cw + "Randomly fill the teams with player");
				sender.sendMessage("- " + cg + "/uhcp teams getteams " + cw + "Returns how many players are in each team.");
				sender.sendMessage("- " + cg + "/uchp teams whichteam <player name> " + cw + "Returns in which team the player is.");

				
			//uhcp teams teamcount
			} else if(args[1].equalsIgnoreCase("teamcount")) {
				
				sender.sendMessage(cg + "You have configured that there may be a maximum of " + cr + PresetHandler.maxTeamCount + cg + " teams.");
			
				
			//uhcp teams randomfill
			} else if(args[1].equalsIgnoreCase("randomfill")) {
				
				TeamHandler teamHandler = new TeamHandler(plugin, sender, true);
				teamHandler.playerRandomTeamJoiner();
				
				
			//uhcp teams getteams
			} else if(args[1].equalsIgnoreCase("getteams")) {
				
				TeamHandler teamHandler = new TeamHandler(plugin, sender, true);
				HashMap<Integer, Integer> teamSizes = teamHandler.getTeamSizes();
				
				for(Map.Entry<Integer, Integer> entry : teamSizes.entrySet()) {
					int value = entry.getValue();
					String playerSinglePlural = (value == 1) ? " player." : " players.";
					sender.sendMessage(cg + "Team " + cr + entry.getKey() + cg + " has " + cr + value + cg + playerSinglePlural);
				}
				
				
			//uhcp teams whichteam
			} else if(args[1].equalsIgnoreCase("whichteam")) {
				List<Team> teams = TeamHandler.teams;
				
				if(args.length >= 3) {
					UUID uuid = Bukkit.getServer().getPlayer(args[2]).getUniqueId();
					
					int teamId = -1;
					
					for(Team team : teams) {
						if(team.getTeamMembers().contains(uuid)) {
							teamId = team.getTeamId();
						}
					}
					
					sender.sendMessage(cg + "The player " + cr + args[2] + cg + " is in team " + cr + teamId);
				} else {
					sender.sendMessage(cr + "Missing arguments! Usage: /uhcp teams whichteam <player name>");
				}

			//uhcp teams jointeam <arg>
			} else if(args[1].equalsIgnoreCase("jointeam")) {
				
				//uhcp teams jointeam <team id>
				if(args.length >= 3) {
					
					int teamId = 0;
					
					try {
						teamId = Integer.valueOf(args[2]);
					} catch (Exception e) {
						sender.sendMessage(ChatColor.RED + "Invalid ID!");
					}
					
					TeamHandler teamHandler = new TeamHandler(plugin, null, false);
					
					Player playerSender = (Player) sender;
					
					teamHandler.playerJoinTeam(teamId, playerSender.getUniqueId());
					
					TeamHandler.teamManuallySelect = true;
					
				//uchp teams jointeam <no args>	
				} else {
					
					sender.sendMessage(ChatColor.RED + "Missing argument! Usage: /uhcp teams join <team id>");
				}
			}
		
		//uhcp teams
		//no argument supplied, tell the user how to get to the help page.
		} else {
			sender.sendMessage(cr + "Missing arguments! See /uhcp teams help, for help");
		}
		

		return true;
	}
	
	
	
}
