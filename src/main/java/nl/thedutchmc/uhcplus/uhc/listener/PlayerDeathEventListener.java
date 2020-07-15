package nl.thedutchmc.uhcplus.uhc.listener;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import nl.thedutchmc.uhcplus.events.UhcEndedEvent;
import nl.thedutchmc.uhcplus.teams.Team;
import nl.thedutchmc.uhcplus.teams.TeamHandler;

public class PlayerDeathEventListener implements Listener {
 	
	TeamHandler teamHandler = new TeamHandler(null, false);
	
	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent event) {
		Player player = event.getEntity();
		
		teamHandler.playerDied(player.getUniqueId());
		
		for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0f, 1.0f);
		}
		
		if(event.getEntity().getKiller() != null) {
			Bukkit.broadcastMessage(ChatColor.AQUA + player.getName() + " was killed by " + player.getKiller().getName());
		} else {
			
			Bukkit.broadcastMessage(ChatColor.AQUA + player.getName() + " died!");
		}

		//Check if the killer is the last player alive
		List<Team> aliveTeams = TeamHandler.getAliveTeams();
		
		if(aliveTeams.size() == 1) {
			
			//Only one team is left alive, thus they won
			Bukkit.broadcastMessage(ChatColor.AQUA + "UHC Ended!");
			
			//Call the UhcEndedEvent
			Team winningTeam = aliveTeams.get(0);
			
			Bukkit.getServer().getPluginManager().callEvent(new UhcEndedEvent(winningTeam.getTeamMembers()));
		}
		
	}
}
