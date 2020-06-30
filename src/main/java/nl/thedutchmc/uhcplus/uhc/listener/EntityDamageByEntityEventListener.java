package nl.thedutchmc.uhcplus.uhc.listener;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import nl.thedutchmc.uhcplus.teams.Team;
import nl.thedutchmc.uhcplus.teams.TeamHandler;

public class EntityDamageByEntityEventListener implements Listener {
	
	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		
		if(event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			Player wasHit = (Player) event.getEntity();
			Player hit = (Player) event.getDamager();
			
			List<Team> teamsAlive = TeamHandler.getAliveTeams();
			
			for(Team team : teamsAlive) {

				if(team.getAliveTeamMembers().contains(wasHit.getUniqueId()) && team.getAliveTeamMembers().contains(hit.getUniqueId())) {
					event.setCancelled(true);
					return;
				}
			}
		}
	}
}
