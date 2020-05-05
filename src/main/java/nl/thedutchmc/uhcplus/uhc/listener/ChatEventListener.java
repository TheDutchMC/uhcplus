package nl.thedutchmc.uhcplus.uhc.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.players.PlayerHandler;
import nl.thedutchmc.uhcplus.players.PlayerObject;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatEventListener implements Listener {

	@EventHandler
	public void onPlayChatEvent(AsyncPlayerChatEvent event) {
		
		Player p = event.getPlayer();
		
		UUID playerUuid = p.getUniqueId();
		
		
		for(PlayerObject playerObject : PlayerHandler.playerObjects) {
			
			if(playerObject.getPlayerUuid().equals(playerUuid)) {
				
				String message = event.getMessage();
				
				if(playerObject.getTeamChatEnabled()) {
					event.setCancelled(true);
					
					for(UUID uuid : playerObject.getTeam().getTeamMembers()) {
						
						Player teamPlayer = Bukkit.getServer().getPlayer(uuid);
						teamPlayer.sendMessage(ChatColor.AQUA + "[Team] " + ChatColor.WHITE + p.getName() + ": " + message);
						
					}
				}
			}
		}
	}
}
