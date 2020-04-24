package nl.thedutchmc.uhcplus;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigurationHandler {
private UhcPlus plugin;
	
	public static String activePreset;
	
	public ConfigurationHandler(UhcPlus plugin) {
		this.plugin = plugin;
	}
	
	private File configFile;
	private FileConfiguration config;
	
	public FileConfiguration getUHCPConfig() {
		return this.config;
	}
	
	public void loadConfig() {
		configFile = new File(plugin.getDataFolder(), "config.yml");
		
		//Check if the config file exists, if it doesnt, create it.
		if(!configFile.exists()) {
			configFile.getParentFile().mkdirs();
			
			plugin.saveResource("config.yml", false);
		}
		
		config = new YamlConfiguration();
		
		//Load the preset configuration file.
		try {
			config.load(configFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
			//TODO better error handling
		}
	}
	
	public void readConfig() {
		
		activePreset = this.getUHCPConfig().getString("activePreset");
				
	}
}