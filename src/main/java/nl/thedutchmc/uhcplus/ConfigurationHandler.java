package nl.thedutchmc.uhcplus;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigurationHandler {
private UhcPlus plugin;
	
	public static String defaultPreset;
	
	public static List<String> availablePresets = new ArrayList<String>();
	
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
			readConfig();
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
			//TODO better error handling
		}
	}
	
	@SuppressWarnings("unchecked")
	public void readConfig() {
		defaultPreset = this.getUHCPConfig().getString("defaultPreset");
		availablePresets = (List<String>) this.getUHCPConfig().getList("availablePresets");
	}
	
	public void setDefaultPreset(String presetName) {
		loadConfig();
		this.getUHCPConfig().set("defaultPreset", presetName);
		saveConfig();
	}
	
	public void addNewPreset(String presetName) {
		loadConfig();
		
		availablePresets.add(presetName);
		this.getUHCPConfig().set("availablePresets", availablePresets);
		saveConfig();
	}
	
	public void saveConfig() {
		try {
			this.getUHCPConfig().save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	//Read all filenames in the presets/ directory and compary them to the list stored in config, update the list in config where necessary
	public void readAvailablePresets() {
		String[] filenames;
		
		File file = new File(plugin.getDataFolder() + File.separator + "presets");
		
		//A filter, since we only want the files ending in .yml
		FilenameFilter filter = new FilenameFilter() {
			
			@Override
			public boolean accept(File file, String name) {
				return name.endsWith(".yml");
			}
		};
		
		filenames = file.list(filter);
		
		//Iterate over all the files in folder
		for(String filename : filenames) {
						
			//Split the name because we dont want the .yml extension
			String[] filenameParts = filename.split(".yml");
						
			//Check if it's in config, if not, add it.
			if(!availablePresets.contains(filenameParts[0])) {
				addNewPreset(filenameParts[0]);
			}
		}
	}
	
	public void removePreset(String presetName) {
		loadConfig();
		
		availablePresets.remove(presetName);
		
		this.getUHCPConfig().set("availablePresets", availablePresets);
		
		saveConfig();
	}
}