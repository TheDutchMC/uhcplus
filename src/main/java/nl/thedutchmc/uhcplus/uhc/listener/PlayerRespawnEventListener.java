package nl.thedutchmc.uhcplus.uhc.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import nl.thedutchmc.uhcplus.UhcPlus;

public class PlayerRespawnEventListener implements Listener {
	
	@EventHandler
	public void onPlayerRespawnEvent(PlayerRespawnEvent event) {
		
		Player player = event.getPlayer();
		
		event.setRespawnLocation(new Location(Bukkit.getServer().getWorld("uhcworld"), 0, 100, 0));
				 
		new BukkitRunnable() {
			
			@Override
			public void run() {
				player.setGameMode(GameMode.SPECTATOR);
			}
		}.runTaskLater(UhcPlus.INSTANCE, 1);

	}
}
