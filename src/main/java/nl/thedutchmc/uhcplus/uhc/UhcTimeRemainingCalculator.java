package nl.thedutchmc.uhcplus.uhc;

import org.bukkit.scheduler.BukkitRunnable;

import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.presets.PresetHandler;

public class UhcTimeRemainingCalculator {
	
	UhcPlus plugin;
	
	public UhcTimeRemainingCalculator(UhcPlus plugin) {
		this.plugin = plugin;
	}
	
	private static int timeRemaining;
	
	public void startCountdown() {
		
		timeRemaining = PresetHandler.gameTime * 60;
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				timeRemaining--;
				
			}
			
		}.runTaskTimerAsynchronously(plugin, 0, 20);	
	}
	
	public static int getTimeRemaining() {
		return timeRemaining;
	}
}