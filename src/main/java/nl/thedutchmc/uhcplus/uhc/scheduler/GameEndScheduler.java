package nl.thedutchmc.uhcplus.uhc.scheduler;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.presets.PresetHandler;
import nl.thedutchmc.uhcplus.world.DeathmatchHandler;

public class GameEndScheduler {
	
	public void scheduleGameEnd() { 
		int gameTime = PresetHandler.gameTime;
		int gameTimeTicks = gameTime * 60 * 20;
		
		int fiveMinGameTimeTicks = (gameTime - 5) * 60 * 20;
		int oneminGameTimeTicks = (gameTime - 1) * 60 * 20;
		int thirtySecondGameTimeTicks = ((gameTime * 60) - 30) * 20;
		int tenSecGameTimeTicks = ((gameTime * 60) - 10) * 20;
		
		//game end
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				DeathmatchHandler deathmatchHandler = new DeathmatchHandler();
				deathmatchHandler.startDeathmatch();
			}
			
		}.runTaskLater(UhcPlus.INSTANCE, gameTimeTicks);
		
		//5 minutes until game end 
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				Bukkit.broadcastMessage(ChatColor.GRAY + "Deathmatch in " + ChatColor.RED + "5" + ChatColor.GRAY + " minutes!");
				
			}
		}.runTaskLaterAsynchronously(UhcPlus.INSTANCE, fiveMinGameTimeTicks);
		
		//1 minute until game end;
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				Bukkit.broadcastMessage(ChatColor.GRAY + "Deathmatch in " + ChatColor.RED + "1" + ChatColor.GRAY + " minute!");
				
			}
		}.runTaskLaterAsynchronously(UhcPlus.INSTANCE, oneminGameTimeTicks);
		
		//30 seconds until game end;
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				Bukkit.broadcastMessage(ChatColor.GRAY + "Deathmatch in " + ChatColor.RED + "30" + ChatColor.GRAY + " seconds!");
				
			}
		}.runTaskLaterAsynchronously(UhcPlus.INSTANCE, thirtySecondGameTimeTicks);
		
		//10 seconds until game end;
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				for(int i = 10; i > 0; i--) {
					Bukkit.broadcastMessage(ChatColor.AQUA + String.valueOf(i));
					
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.runTaskLaterAsynchronously(UhcPlus.INSTANCE, tenSecGameTimeTicks);
	
	}
}
