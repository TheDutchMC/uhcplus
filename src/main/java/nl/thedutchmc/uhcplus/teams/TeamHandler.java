package nl.thedutchmc.uhcplus.teams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.players.PlayerHandler;
import nl.thedutchmc.uhcplus.players.PlayerObject;
import nl.thedutchmc.uhcplus.presets.PresetHandler;

public class TeamHandler {
	
	@SuppressWarnings("unused")
	private UhcPlus plugin;

	private int maxTeamCount = Integer.valueOf(PresetHandler.maxTeamCount);
	private int maxPlayerCountPerTeam = Integer.valueOf(PresetHandler.maxPlayerCountPerTeam);
	
	private boolean shouldReturn = false;
	
	private CommandSender sender;
	public static List<Team> teams = new ArrayList<>();
	
	public TeamHandler(UhcPlus plugin, CommandSender sender, boolean shouldReturn) {
		this.plugin = plugin;
		this.sender = sender;
		this.shouldReturn = shouldReturn;
	}
	
	public void createTeams() {		
		for(int i = 0; i < maxTeamCount; i++) {
			teams.add(new Team(i));
		}	
	}
	
	public void playerTeamJoiner() {
		
		teams.clear();
		createTeams();
		
		Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
		List<UUID> playersNotInTeam = new ArrayList<>();
		Bukkit.getServer().getOnlinePlayers().toArray(players);
		
		if((Bukkit.getServer().getOnlinePlayers().size() / maxTeamCount) > maxPlayerCountPerTeam && shouldReturn) {
			sender.sendMessage(ChatColor.RED + "There are more players than that can be fit into all the teams! There might be spectators!");
		}
		
		for(int i = 0; i < players.length; i++) {
			
			UUID playerUuid = players[i].getUniqueId();
			
			boolean isPlayerInTeam = false;
			
			for(Team team : teams) {
				
				if(team.getTeamSize() != maxPlayerCountPerTeam && !team.isPlayerInTeam(playerUuid)) {
					team.playerJoinTeam(playerUuid);
					
					PlayerHandler playerHandler = new PlayerHandler();
					PlayerObject playerObject = playerHandler.addPlayerToListAndReturn(playerUuid);
					playerObject.setTeam(team);
					playerObject.setTeamChatEnabled(true);
					
					
					isPlayerInTeam = true;
					Bukkit.getServer().getPlayer(playerUuid).sendMessage(ChatColor.GOLD + "You are now in team " + ChatColor.RED + team.getTeamId());
					break;
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
			player.sendMessage(ChatColor.GOLD + "You are not put into a team, and are now a spectator.");
			
			playersNotInTeamNames += new String(player.getName() + ", ");
		}
		
		if(playersNotInTeamNames.length() > 1 && shouldReturn) {
			sender.sendMessage(ChatColor.GOLD + "The following players were not added to a team, and have been put in spectator mode: " + playersNotInTeamNames);
		}
	}
	
	
	boolean isTeamFull(int teamId) {
		
		for(Team team : teams) {
			
			if(team.getTeamId() == teamId) {
				return !(team.getTeamSize() <= maxPlayerCountPerTeam);
			}
		}
		return false;
	}
	
	public HashMap<Integer, Integer> getTeamSizes() {
		
		HashMap<Integer, Integer> returnTeamSizes = new HashMap<>();
		
		for(Team team : teams) {
			returnTeamSizes.put(team.getTeamId(), team.getTeamSize());
		}
		
		return returnTeamSizes;
		
	}
	
	public int teamsWithPlayers() {
		
		int teamsWithPlayers = 0;
		
		for(Team team : teams) {
			if(team.getTeamMembers().size() > 0) {
				teamsWithPlayers++;
			}
		}
		
		return teamsWithPlayers;
	}

	public int teamsAlive() {
		
		int teamsAlive = 0;
		
		for(Team team : teams) {
			if(team.getAliveTeamMembers().size() > 0) {
				teamsAlive++;
			}
		}
		
		return teamsAlive;
	}
	
	public static List<Team> getAliveTeams() {
		
		List<Team> aliveTeams = new ArrayList<>();
		
		for(Team team : teams) {
			if(team.getAliveTeamMembers().size() > 0) {
				aliveTeams.add(team);
			}
		}
		
		return aliveTeams;
	}

	public void playerDied(UUID uuid) {
		
		for(Team team : teams) {
			
			if(team.getAliveTeamMembers().contains(uuid)) {
				List<UUID> newAliveTeamMembers = new ArrayList<>();
				newAliveTeamMembers = team.getAliveTeamMembers();
				newAliveTeamMembers.remove(uuid);
				
				team.setAliveTeamMembers(newAliveTeamMembers);
			}
		}
	}
}
