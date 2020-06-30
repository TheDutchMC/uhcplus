package nl.thedutchmc.uhcplus.uhc.listener;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import nl.thedutchmc.uhcplus.ConfigurationHandler;
import nl.thedutchmc.uhcplus.teams.Team;
import nl.thedutchmc.uhcplus.teams.TeamHandler;

public class PlayerCommandPreprocessEventListener implements Listener {

	@SuppressWarnings("deprecation") // event.getRecipients()
	@EventHandler
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
		
		String[] commandParts = event.getMessage().split(" ");
		
		//Check if spectators are not allowed to run commands
		if(!ConfigurationHandler.spectatorCanRunCommand) {
			
			//Check the gamemode, and if the sender is OP, because we always want to allow OP players
			if(event.getPlayer().getGameMode().equals(GameMode.SPECTATOR) && !event.getPlayer().isOp()) {
				
				//Cancel the event and let the sender know
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.RED + "You are not allowed to execute commands!");
			}
			
			
		//Check if spectators are not allowed to /msg, /w or /tell
		} else if(!ConfigurationHandler.spectatorCanWhisperToPlayer) {
			
			//Check if the sender is not OP, because OP's always are allowed
			if(!event.getPlayer().isOp()) {
				
				//Check if the sent command matches any of the below
				if(commandParts[0].equalsIgnoreCase("/msg") ||
						commandParts[0].equalsIgnoreCase("/w") ||
						commandParts[0].equalsIgnoreCase("/tell")) {
					
					//Check if the gamemode is spectator
					if(event.getPlayer().getGameMode().equals(GameMode.SPECTATOR)) {

						//Loop over the recipients
						for(Player recipient : event.getRecipients()) {
							
							//Loop over the teams alive
							for(Team team : TeamHandler.getAliveTeams()) {
								
								//Loop over the alive team members of the alive teams
								for(UUID uuid : team.getAliveTeamMembers()) {
									
									//If the recipient is an alive player, cancel the event and inform the player
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
			
		} else if(commandParts[0].equalsIgnoreCase("/me")) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.RED + "You're not allowed to use /me!");
		}
	}
}
