package nl.thedutchmc.uhcplus;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import nl.thedutchmc.uhcplus.commands.CommandHandler;
import nl.thedutchmc.uhcplus.commands.UhcpTabCompleter;
import nl.thedutchmc.uhcplus.presets.PresetHandler;
import nl.thedutchmc.uhcplus.uhc.listener.PlayerLoginJoinEventListener;
import nl.thedutchmc.uhcplus.world.WorldHandler;

public class UhcPlus extends JavaPlugin {

	public static String VERSION = "1.0-BETA";
		
	public static boolean PLAYER_CAN_JOIN = false;
	
	
	@Override
	public void onEnable() {
		System.out.println("Welcome to UHCPlus - Version " + VERSION);
	
		//Register the LoginPlayerListener
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerLoginJoinEventListener(), this);
		
		//Set the executor for the /uhcp command
		getCommand("uhcp").setExecutor(new CommandHandler(this));
		getCommand("uhcp").setTabCompleter(new UhcpTabCompleter());
		
		//Load the configuration file
		ConfigurationHandler configurationHandler = new ConfigurationHandler(this);
		configurationHandler.loadConfig();
		
		//Load all the presets
		PresetHandler presetHandler = new PresetHandler(this);
		presetHandler.loadPresets();
		
		//Check if the files in the presets/ directory match what's in config.
		configurationHandler.readAvailablePresets();
		
		//Create a folder for the deathmatch schematics
		File file = new File(this.getDataFolder() + File.separator + "deathmatch");
		file.mkdir();
		
		UhcPlus plugin = this;
		
		new BukkitRunnable() {

			@Override
			public void run() {
				WorldHandler worldHandler = new WorldHandler(plugin);
				worldHandler.setupWorld();				
			}
			
		
		
		}.runTaskLater(plugin, 120);

	}
	
	@Override
	public void onDisable() {
		
	}
}
