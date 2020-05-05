package nl.thedutchmc.uhcplus;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import nl.thedutchmc.uhcplus.commands.ChatCommandHandler;
import nl.thedutchmc.uhcplus.commands.UhcpCommandHandler;
import nl.thedutchmc.uhcplus.commands.UhcpTabCompleter;
import nl.thedutchmc.uhcplus.presets.PresetHandler;
import nl.thedutchmc.uhcplus.uhc.listener.EntityDamageByEntityEventListener;
import nl.thedutchmc.uhcplus.uhc.listener.PlayerLoginJoinEventListener;
import nl.thedutchmc.uhcplus.world.WorldHandler;

public class UhcPlus extends JavaPlugin {

	public static String VERSION = "1.0-BETA";
		
	public static boolean PLAYER_CAN_JOIN = false;	
	
	public static boolean UHC_STARTED = false;
	
	public static Scoreboard scoreboard;
	
	@Override
	public void onEnable() {
		System.out.println("Welcome to UHCPlus - Version " + VERSION);
		
		//Register the LoginPlayerListener
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerLoginJoinEventListener(this), this);
		
		ScoreboardManager sbManager = Bukkit.getScoreboardManager();
		scoreboard = sbManager.getNewScoreboard();
		Objective healthObjective = scoreboard.registerNewObjective("health", "health", "health");
		healthObjective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
		healthObjective.setRenderType(RenderType.HEARTS);
		
		//Register the EntityDamageByEntityEventListener
		Bukkit.getServer().getPluginManager().registerEvents(new EntityDamageByEntityEventListener(this), this);
		
		//Set the executor and tab completer for the /uhcp command
		getCommand("uhcp").setExecutor(new UhcpCommandHandler(this));
		getCommand("uhcp").setTabCompleter(new UhcpTabCompleter());
		
		//Set the executor for the /chat command
		getCommand("chat").setExecutor(new ChatCommandHandler());
		
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
