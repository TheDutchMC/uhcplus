package nl.thedutchmc.uhcplus.presetHandler;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.thedutchmc.uhcplus.ConfigurationHandler;
import nl.thedutchmc.uhcplus.UhcPlus;

public class PresetHandler {

	public static String loadedPreset;
	
	public static String maxTeamCount, maxPlayerCountPerTeam;
	
	private UhcPlus plugin;
	
	public PresetHandler(UhcPlus plugin) {
		this.plugin = plugin;
	}
	
	
	public void loadPresets() {
		List<String> availablePresets = ConfigurationHandler.availablePresets;
		
		DefaultPreset defaultPreset = new DefaultPreset(plugin);
		
		defaultPreset = new DefaultPreset(plugin);

		for(String str : availablePresets) {
			defaultPreset.loadPreset(str, false);
		}
		
		loadPreset(ConfigurationHandler.defaultPreset);
	}
	
	
	public void loadPreset(String presetName) {
		HashMap<String, String> hasReturned;
		
		DefaultPreset defaultPreset = new DefaultPreset(plugin);
		
		hasReturned = defaultPreset.loadPreset(presetName, true);
		
		loadPresetValuesIntoVariables(hasReturned);
		
		loadedPreset = presetName;
	}
	
	public void loadPresetValuesIntoVariables(HashMap<String, String> presetValues) {
		
		for(Map.Entry<String, String> entry : presetValues.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			
			if(key == "maxTeamCount") {
				maxTeamCount = value;
			} else if(key == "maxPlayerCountPerTeam") {
				maxPlayerCountPerTeam = value;
			}
		}
	}
	
	public void createPreset(String presetName) {
		
		DefaultPreset defaultPreset = new DefaultPreset(plugin);
		defaultPreset.loadPreset(presetName, false);
	
		ConfigurationHandler configurationHandler = new ConfigurationHandler(plugin);
		configurationHandler.addNewPreset(presetName);
	}
	
	public void removePreset(String presetName) {
		
		ConfigurationHandler configurationHandler = new ConfigurationHandler(plugin);
		configurationHandler.removePreset(presetName);
		
		File file = new File(plugin.getDataFolder() + File.separator + "presets", presetName + ".yml");
		
		file.delete();
	}
}