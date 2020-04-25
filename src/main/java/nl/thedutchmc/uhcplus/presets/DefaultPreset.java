package nl.thedutchmc.uhcplus.presets;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import nl.thedutchmc.uhcplus.UhcPlus;

public class DefaultPreset {
	
	private UhcPlus plugin;

	public static String maxTeamCount, maxPlayerCountPerTeam, moduleOreAutoSmelt, moduleOreAutoSmeltIngotDrop;
	
	public DefaultPreset(UhcPlus plugin) {
		this.plugin = plugin;
	}
	
	private File presetConfigFile;
	private FileConfiguration presetConfig;
	
	public FileConfiguration getPresetConfig() {
		return this.presetConfig;
	} 
	
	public HashMap<String, String> loadPreset(String presetName, boolean shouldReturn) {
		
		presetConfigFile = new File(plugin.getDataFolder() + File.separator + "presets", presetName + ".yml");
		
		presetConfig = YamlConfiguration.loadConfiguration(presetConfigFile);
		
		//Check if the config file exists, if it doesnt, create it.		
		if(!presetConfigFile.exists()) {
			presetConfigFile.getParentFile().mkdirs();
			
			try {
				FileUtils.copyToFile(plugin.getResource("default.yml"), presetConfigFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//Load the preset configuration file.
		try {
			presetConfig.load(presetConfigFile);
			
			if(shouldReturn) {
				readPreset();
			}
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
			//TODO better error handling
		}
		
		if(shouldReturn) {
			HashMap<String, String> toReturn = new HashMap<String, String>();
			
			toReturn.put("maxTeamCount", maxTeamCount);
			toReturn.put("maxPlayerCountPerTeam", maxPlayerCountPerTeam);
			toReturn.put("moduleOreAutoSmelt", moduleOreAutoSmelt);
			toReturn.put("moduleOreAutoSmeltIngotDrop", moduleOreAutoSmeltIngotDrop);
			
			return toReturn;
		} else {
			return null;
		}
	}
	
	public void readPreset() {
		
		maxTeamCount = this.getPresetConfig().getString("maxTeamCount");
		maxPlayerCountPerTeam = this.getPresetConfig().getString("maxPlayerCountPerTeam");
		moduleOreAutoSmelt = this.getPresetConfig().getString("moduleOreAutoSmelt");
		moduleOreAutoSmeltIngotDrop = this.getPresetConfig().getString("moduleOreAutoSmeltIngotDrop");		
	}
	
	public void writePreset(String presetName) {
		loadPreset(presetName, false);
		
		this.getPresetConfig().set("maxTeamCount", maxTeamCount);
		this.getPresetConfig().set("maxPlayerCountPerTeam", maxPlayerCountPerTeam);
		this.getPresetConfig().set("moduleOreAutoSmelt", moduleOreAutoSmelt);
		this.getPresetConfig().set("moduleOreAutoSmeltIngotDrop", moduleOreAutoSmeltIngotDrop);
	
		savePreset();
	}
	
	public void savePreset() {
		try {
			this.getPresetConfig().save(presetConfigFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}