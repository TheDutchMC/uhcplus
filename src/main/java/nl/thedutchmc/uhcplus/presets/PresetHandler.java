package nl.thedutchmc.uhcplus.presets;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.thedutchmc.uhcplus.ConfigurationHandler;
import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.modules.ModuleHandler;

public class PresetHandler {

	public static String loadedPreset;
	
	public static String maxTeamCount, maxPlayerCountPerTeam;
	public static boolean moduleOreAutoSmelt;
	public static int moduleOreAutoSmeltIngotDrop;
	
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
		
		ModuleHandler moduleHandler = new ModuleHandler(plugin);
		moduleHandler.unloadModules();
		moduleHandler.loadModules();
		
		
		
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
			} else if(key == "moduleOreAutoSmelt") {
				moduleOreAutoSmelt = Boolean.valueOf(value);
			} else if(key == "moduleOreAutoSmeltIngotDrop") {
				moduleOreAutoSmeltIngotDrop = Integer.valueOf(value);
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
	
	public void changePresetOption() {
		
		DefaultPreset.maxTeamCount = maxTeamCount;
		DefaultPreset.maxPlayerCountPerTeam = maxPlayerCountPerTeam;
		DefaultPreset.moduleOreAutoSmelt = String.valueOf(moduleOreAutoSmelt);
		DefaultPreset.moduleOreAutoSmeltIngotDrop = String.valueOf(moduleOreAutoSmeltIngotDrop);
		
		
		DefaultPreset defaultPreset = new DefaultPreset(plugin);
		defaultPreset.writePreset(loadedPreset);
		
		ModuleHandler moduleHandler = new ModuleHandler(plugin);
		moduleHandler.unloadModules();
		moduleHandler.loadModules();
	}
}