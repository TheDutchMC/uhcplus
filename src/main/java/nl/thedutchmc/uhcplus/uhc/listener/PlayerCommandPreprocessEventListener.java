package nl.thedutchmc.uhcplus.uhc.listener;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.teams.Team;
import nl.thedutchmc.uhcplus.teams.TeamHandler;

public class PlayerCommandPreprocessEventListener implements Listener {

	@EventHandler
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
		
		String[] commandParts = event.getMessage().split(" ");
		
		if(commandParts[0].equalsIgnoreCase("/msg") ||
				commandParts[0].equalsIgnoreCase("/w") ||
				commandParts[0].equalsIgnoreCase("/tell")) {
			
			if(event.getPlayer().getGameMode().equals(GameMode.SPECTATOR)) {

				for(Player recipient : event.getRecipients()) {
					
					for(Team team : TeamHandler.getAliveTeams()) {
						
						for(UUID uuid : team.getAliveTeamMembers()) {
							
							if(recipient.getUniqueId().equals(uuid)) {
								event.setCancelled(true);
								event.getPlayer().sendMessage(ChatColor.RED + "You may not whisper to players who are still playing!");
								
							}
						}
					}
				}
			}
		}
	}
}
