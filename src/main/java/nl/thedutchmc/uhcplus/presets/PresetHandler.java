package nl.thedutchmc.uhcplus.presets;

import java.io.File;
import java.util.List;

import nl.thedutchmc.uhcplus.ConfigurationHandler;
import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.modules.ModuleHandler;

public class PresetHandler {

	public static String loadedPreset;
	
	public static String maxTeamCount, maxPlayerCountPerTeam;
	public static boolean moduleOreAutoSmelt, moduleTreeFullRemove, moduleLeaveDecay, moduleEnchantedTools, moduleInfiniteEnchanting;
	public static int moduleOreAutoSmeltIngotDrop, timeToPvp, worldBorderSize, worldBorderShrinkAfter, worldBorderShrinkTo, gameTime;
	
	private UhcPlus plugin;
	
	public PresetHandler(UhcPlus plugin) {
		this.plugin = plugin;
	}
	
	
	public void loadPresets() {
		List<String> availablePresets = ConfigurationHandler.availablePresets;
		
		DefaultPreset defaultPreset = new DefaultPreset(plugin);
		defaultPreset = new DefaultPreset(plugin);

		for(String presetName : availablePresets) {
			defaultPreset.loadPreset(presetName, false);
		}
		
		loadPreset(ConfigurationHandler.defaultPreset);
	}
	
	
	public void loadPreset(String presetName) {
		DefaultPreset defaultPreset = new DefaultPreset(plugin);
		defaultPreset.loadPreset(presetName, true);
		
		ModuleHandler moduleHandler = new ModuleHandler(plugin);
		moduleHandler.unloadModules();
		moduleHandler.loadModules();
		
		loadedPreset = presetName;
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
		
		DefaultPreset defaultPreset = new DefaultPreset(plugin);
		defaultPreset.writePreset(loadedPreset);
		
		ModuleHandler moduleHandler = new ModuleHandler(plugin);
		moduleHandler.unloadModules();
		moduleHandler.loadModules();
	}
}