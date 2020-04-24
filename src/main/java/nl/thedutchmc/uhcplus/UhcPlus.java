package nl.thedutchmc.uhcplus;

import org.bukkit.plugin.java.JavaPlugin;

import nl.thedutchmc.uhcplus.presetHandler.PresetHandler;

public class UhcPlus extends JavaPlugin {

	public static String VERSION = "0.1-ALPHA";
	
	CommandHandler commandHandler = new CommandHandler(this);
	
	@Override
	public void onEnable() {
		System.out.println("Welcome to UHCPlus - Version " + VERSION);
	
		//Set the executor for the /uhcp command
		getCommand("uhcp").setExecutor(commandHandler);
		
		//Load the configuration file
		ConfigurationHandler configurationHandler = new ConfigurationHandler(this);
		configurationHandler.loadConfig();
		
		//Load all the presets
		PresetHandler presetHandler = new PresetHandler(this);
		presetHandler.loadPresets();
	}
	
	@Override
	public void onDisable() {
		
	}
}
