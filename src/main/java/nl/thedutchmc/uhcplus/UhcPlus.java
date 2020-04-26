package nl.thedutchmc.uhcplus;

import org.bukkit.plugin.java.JavaPlugin;

import nl.thedutchmc.uhcplus.commands.CommandHandler;
import nl.thedutchmc.uhcplus.commands.UhcpTabCompleter;
import nl.thedutchmc.uhcplus.presets.PresetHandler;
import nl.thedutchmc.uhcplus.teams.TeamHandler;

public class UhcPlus extends JavaPlugin {

	public static String VERSION = "0.1-ALPHA";
		
	@Override
	public void onEnable() {
		System.out.println("Welcome to UHCPlus - Version " + VERSION);
	
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
	}
	
	@Override
	public void onDisable() {
		
	}
}
