package nl.thedutchmc.uhcplus.uhc.scheduler;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.presets.PresetHandler;
import nl.thedutchmc.uhcplus.world.DeathmatchHandler;

public class GameEndScheduler {

	private UhcPlus plugin;
	
	public GameEndScheduler(UhcPlus plugin) {
		this.plugin = plugin;
	}
	
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
				
				DeathmatchHandler deathmatchHandler = new DeathmatchHandler(plugin);
				deathmatchHandler.startDeathmatch();
			}
			
		}.runTaskLater(plugin, gameTimeTicks);
		
		//5 minutes until game end 
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				Bukkit.broadcastMessage(ChatColor.AQUA + "Deathmatch in " + ChatColor.RED + "5" + ChatColor.AQUA + " minutes!");
				
			}
		}.runTaskLaterAsynchronously(plugin, fiveMinGameTimeTicks);
		
		//1 minute until game end;
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				Bukkit.broadcastMessage(ChatColor.AQUA + "Deathmatch in " + ChatColor.RED + "1" + ChatColor.AQUA + " minute!");
				
			}
		}.runTaskLaterAsynchronously(plugin, oneminGameTimeTicks);
		
		//30 seconds until game end;
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				Bukkit.broadcastMessage(ChatColor.AQUA + "Deathmatch in " + ChatColor.RED + "30" + ChatColor.AQUA + " seconds!");
				
			}
		}.runTaskLaterAsynchronously(plugin, thirtySecondGameTimeTicks);
		
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
		}.runTaskLaterAsynchronously(plugin, tenSecGameTimeTicks);
	
	}
}
