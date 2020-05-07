package nl.thedutchmc.uhcplus.uhc.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.players.PlayerHandler;
import nl.thedutchmc.uhcplus.players.PlayerObject;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class ChatEventListener implements Listener {

	@EventHandler
	public void onPlayChatEvent(AsyncPlayerChatEvent event) {
		
		Player p = event.getPlayer();
		
		UUID playerUuid = p.getUniqueId();
		
		//Spectator chat
		//Check if the gamemode is spectator
		if(p.getGameMode().equals(GameMode.SPECTATOR)) {
			
			event.setCancelled(true);
			
			//loop over all the online players
			for(Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
				
				//check if the gamemode is spectator and that the player isnt the sender
				if(onlinePlayer.getGameMode().equals(GameMode.SPECTATOR)) {
					
					//send the message to the spectator
					onlinePlayer.sendMessage(ChatColor.AQUA + "[Spectator] " + ChatColor.WHITE + p.getName() + ": " + event.getMessage());
				}	
			}
		} else {
			
			//Loop over all the playerObjects
			for(PlayerObject playerObject : PlayerHandler.playerObjects) {
				
				//if the current playerObject is the sender
				if(playerObject.getPlayerUuid().equals(playerUuid)) {
					
					String message = event.getMessage();
					
					//Check if the sender has teamchat enabled
					if(playerObject.getTeamChatEnabled()) {
						event.setCancelled(true);
						
						//loop over the team and send the message to them
						for(UUID uuid : playerObject.getTeam().getTeamMembers()) {
							
							Player teamPlayer = Bukkit.getServer().getPlayer(uuid);
							teamPlayer.sendMessage(ChatColor.AQUA + "[Team] " + ChatColor.WHITE + p.getName() + ": " + message);
							
						}
					}
				}
			}
		}
	}
}
