package nl.thedutchmc.uhcplus.uhc;

import org.bukkit.scheduler.BukkitRunnable;

import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.presets.PresetHandler;

public class UhcTimeRemainingCalculator {

	private static int timeRemaining;

	public void startCountdown() {

		timeRemaining = (int) PresetHandler.getPrefabOption("gameTime") * 60;

		new BukkitRunnable() {

			
			
			@Override
			public void run() {
				
				if(UhcHandler.getGameState().equals(GameState.END)) {
					this.cancel();
					timeRemaining = 0;
					return;
				}

				timeRemaining--;

			}

		}.runTaskTimerAsynchronously(UhcPlus.INSTANCE, 0, 20);
	}

	public static int getTimeRemaining() {
		return timeRemaining;
	}
}