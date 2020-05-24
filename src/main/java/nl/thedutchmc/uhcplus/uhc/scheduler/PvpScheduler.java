package nl.thedutchmc.uhcplus.uhc.scheduler;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.presets.PresetHandler;

public class PvpScheduler {

	private World overworld;
	private UhcPlus plugin;
	
	public PvpScheduler(World overworld, UhcPlus plugin) {
		this.overworld = overworld;
		this.plugin = plugin;
	}
	
	public void schedulePvp() {
		
		long timeToPvpMin = Long.valueOf(PresetHandler.timeToPvp);
		
		long timeToPvpTick = timeToPvpMin * 60 * 20;
		
		long oneMinuteToPvpTick = (timeToPvpMin - 1) * 60 * 20;
		long thirtySecondsToPvptick = ((timeToPvpMin * 60) - 30) * 20;
		long tenSecondsToPvpTick = ((timeToPvpMin * 60) - 10) * 20;
		
		//Enable PVP
		new BukkitRunnable() {
			
			@Override
			public void run() {
				overworld.setPVP(true);
				
				Bukkit.broadcastMessage(ChatColor.AQUA + "PVP is now enabled!");
				
			}
		}.runTaskLaterAsynchronously(plugin, timeToPvpTick);
		
		//1 min until PVP
		new BukkitRunnable() {
			
			@Override
			public void run() {
				Bukkit.broadcastMessage(ChatColor.AQUA + "One minute until PVP will be enabled!");
			}
		}.runTaskLaterAsynchronously(plugin, oneMinuteToPvpTick);
		
		//30 sec until PVP
		new  BukkitRunnable() {
			
			@Override
			public void run() {
				Bukkit.broadcastMessage(ChatColor.AQUA + "Thirty seconds until PVP will be enabled!");
			}
		}.runTaskLaterAsynchronously(plugin, thirtySecondsToPvptick);
		
		//10-0 sec until PVP
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				for(int i = 10; i > 0; i--) {
					Bukkit.broadcastMessage(ChatColor.AQUA + "PVP starting in " + String.valueOf(i));
					
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.runTaskLaterAsynchronously(plugin, tenSecondsToPvpTick);

	}

}
