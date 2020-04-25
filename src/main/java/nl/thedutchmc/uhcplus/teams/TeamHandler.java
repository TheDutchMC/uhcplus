package nl.thedutchmc.uhcplus.teams;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.presets.PresetHandler;

public class TeamHandler {

	@SuppressWarnings("unused")
	private UhcPlus plugin;

	private int maxTeamCount = Integer.valueOf(PresetHandler.maxTeamCount);
	private int maxPlayerCountPerTeam = Integer.valueOf(PresetHandler.maxPlayerCountPerTeam);
	
	private CommandSender sender;
	static List<Team> teams = new ArrayList<>();
	
	public TeamHandler(UhcPlus plugin, CommandSender sender) {
		this.plugin = plugin;
		this.sender = sender;
	}
	
	public void createTeams() {		
		for(int i = 0; i < maxTeamCount; i++) {
			teams.add(new Team(i));
		}	
	}
	
	public void playerTeamJoiner() {
		Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
		List<UUID> playersNotInTeam = new ArrayList<>();
		Bukkit.getServer().getOnlinePlayers().toArray(players);
		
		if((Bukkit.getServer().getOnlinePlayers().size() / maxPlayerCountPerTeam) % 2 != 0) {
			sender.sendMessage(ChatColor.RED + "Cannot evenly divide players over all the teams! You might get teams of unequal size!");
		}
		
		if((Bukkit.getServer().getOnlinePlayers().size() / maxTeamCount) > maxPlayerCountPerTeam) {
			sender.sendMessage(ChatColor.RED + "There are more players than that can be fit into all the teams! There might be spectators!");
		}
		
		for(int i = 0; i <= players.length; i++) {
			
			UUID playerUuid = players[i].getUniqueId();
			
			boolean isPlayerInTeam = false;
			
			for(Team team : teams) {
				
				System.out.println(team.getTeamId());
				System.out.println(teams.size());
				
				if(team.getTeamSize() != maxPlayerCountPerTeam) {
					team.playerJoinTeam(playerUuid);
					isPlayerInTeam = true;
					Bukkit.getServer().getPlayer(playerUuid).sendMessage(ChatColor.GOLD + "You are now in team " + ChatColor.RED + team.getTeamId());
				}
			}
			
			if(!isPlayerInTeam) {
				playersNotInTeam.add(playerUuid);
			}
			
		}
		
		String playersNotInTeamNames = "";
		
		for(UUID uuid : playersNotInTeam) {
			Player player = Bukkit.getServer().getPlayer(uuid);
			player.setGameMode(GameMode.SPECTATOR);
			
			playersNotInTeamNames += new String(player.getName() + ", ");
		}
		
		sender.sendMessage(ChatColor.GOLD + "The following players were not added to a team, and have been put in spectator mode: " + playersNotInTeamNames);
	}
	
	
	boolean isTeamFull(int teamId) {
		
		for(Team team : teams) {
			
			if(team.getTeamId() == teamId) {
				return !(team.getTeamSize() <= maxPlayerCountPerTeam);
			}
		}
		return false;
	}
}
