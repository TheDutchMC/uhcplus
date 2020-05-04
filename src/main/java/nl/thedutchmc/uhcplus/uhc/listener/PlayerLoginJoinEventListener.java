package nl.thedutchmc.uhcplus.uhc.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.teams.Team;
import nl.thedutchmc.uhcplus.teams.TeamHandler;

public class PlayerLoginJoinEventListener implements Listener {

	private UhcPlus plugin;
	
	public PlayerLoginJoinEventListener(UhcPlus plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerLoginEvent(PlayerLoginEvent event) {

		if(!UhcPlus.PLAYER_CAN_JOIN) { 
			event.disallow(Result.KICK_OTHER, ChatColor.RED + "You shall not pass!\n\n" + ChatColor.GOLD + "(you are not allowed to join just yet)");
		}
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		
		if(UhcPlus.PLAYER_CAN_JOIN) {
			
			Player player = event.getPlayer();
			
			World uhcWorld = Bukkit.getServer().getWorld("uhcworld");
						
			player.setScoreboard(UhcPlus.scoreboard);
			player.getInventory().clear();
			player.setExp(0);

			//If the UHC has started, we dont want the players to spawn in survival, so put them in spectator and tp them to 0,100,0. Else TP them to the lobby
			if(UhcPlus.UHC_STARTED) {
				
				boolean playerWasPlaying = false;
				
				TeamHandler teamHandler = new TeamHandler(plugin, null, false);
				
				//We want to check if the player was already playing in the UHC, because then we just want them to join like they usually would, and not TP them or change their gamemode
				for(Team team : teamHandler.getAliveTeams()) {
					if(team.isPlayerInTeam(player.getUniqueId())) {
						playerWasPlaying = true;
						break;
					}
				}
				
				if(!playerWasPlaying) {
					player.teleport(new Location(uhcWorld, 0, 100, 0));
					player.setGameMode(GameMode.SPECTATOR);
					
					player.sendMessage(ChatColor.GOLD + "The UHC has already started. You are a spectator.");
				}
			} else {
				player.setGameMode(GameMode.ADVENTURE);
				player.teleport(new Location(uhcWorld, 0, 201, 0));
			}
			
		}
	}
}