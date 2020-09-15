package nl.thedutchmc.uhcplus.uhc.scheduler;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.presets.PresetHandler;
import nl.thedutchmc.uhcplus.uhc.GameState;

public class PvpScheduler {

	private World overworld;

	public PvpScheduler(World overworld) {
		this.overworld = overworld;
	}

	public void schedulePvp() {

		long timeToPvpMin = Long.valueOf(PresetHandler.timeToPvp);

		long timeToPvpTick = timeToPvpMin * 60 * 20;

		long oneMinuteToPvpTick = (timeToPvpMin - 1) * 60 * 20;
		long thirtySecondsToPvptick = ((timeToPvpMin * 60) - 30) * 20;
		long tenSecondsToPvpTick = ((timeToPvpMin * 60) - 10) * 20;

		// Enable PVP
		new BukkitRunnable() {

			@Override
			public void run() {
				
				if(UhcPlus.currentState.equals(GameState.END)) {
					this.cancel();
					return;
				}
								
				overworld.setPVP(true);

				Bukkit.broadcastMessage(ChatColor.GRAY + "PVP is now enabled!");

			}
		}.runTaskLaterAsynchronously(UhcPlus.INSTANCE, timeToPvpTick);

		// 1 min until PVP
		new BukkitRunnable() {

			@Override
			public void run() {
				
				if(UhcPlus.currentState.equals(GameState.END)) {
					this.cancel();
					return;
				}
				
				UhcPlus.logInfo("GameState:" + UhcPlus.currentState);

				
				Bukkit.broadcastMessage(ChatColor.GRAY + "One minute until PVP will be enabled!");
			}
		}.runTaskLaterAsynchronously(UhcPlus.INSTANCE, oneMinuteToPvpTick);

		// 30 sec until PVP
		new BukkitRunnable() {

			@Override
			public void run() {
				
				if(UhcPlus.currentState.equals(GameState.END)) {
					this.cancel();
					return;
				}
				
				UhcPlus.logInfo("GameState:" + UhcPlus.currentState);

				
				Bukkit.broadcastMessage(ChatColor.GRAY + "Thirty seconds until PVP will be enabled!");
			}
		}.runTaskLaterAsynchronously(UhcPlus.INSTANCE, thirtySecondsToPvptick);

		// 10-0 sec until PVP
		new BukkitRunnable() {

			@Override
			public void run() {

				if(UhcPlus.currentState.equals(GameState.END)) {
					this.cancel();
					return;
				}
				
				UhcPlus.logInfo("GameState:" + UhcPlus.currentState);
				
				for (int i = 10; i > 0; i--) {
					Bukkit.broadcastMessage(ChatColor.GRAY + "PVP starting in " + String.valueOf(i));

					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.runTaskLaterAsynchronously(UhcPlus.INSTANCE, tenSecondsToPvpTick);

	}

}
