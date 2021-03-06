package nl.thedutchmc.uhcplus.modules.modules;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import nl.thedutchmc.uhcplus.events.UhcStartedEvent;
import nl.thedutchmc.uhcplus.teams.Team;
import nl.thedutchmc.uhcplus.teams.TeamHandler;
import nl.thedutchmc.uhcplus.UhcPlus;

public class ModuleOneHeartStart implements Listener {

	@EventHandler
	public void onUhcStartedEvent(UhcStartedEvent event) {

		for (Team team : TeamHandler.teams) {
			for (UUID uuid : team.getTeamMembers()) {

				Player p = Bukkit.getPlayer(uuid);

				new BukkitRunnable() {

					@Override
					public void run() {
						p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(2);
						p.setHealth(2);
					}
				}.runTaskLater(UhcPlus.INSTANCE, 100);

				new BukkitRunnable() {

					@Override
					public void run() {
						p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
					}
				}.runTaskLater(UhcPlus.INSTANCE, 460);
			}
		}
	}
}
