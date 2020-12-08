package nl.thedutchmc.uhcplus.presets;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import nl.thedutchmc.uhcplus.ConfigurationHandler;
import nl.thedutchmc.uhcplus.modules.ModuleHandler;
import nl.thedutchmc.uhcplus.UhcPlus;

public class PresetHandler {

	public static String loadedPreset;

	private static HashMap<String, Object> presetParams = new HashMap<>();

	public void loadPresets() {
		List<String> availablePresets = ConfigurationHandler.availablePresets;

		DefaultPreset defaultPreset = new DefaultPreset();
		defaultPreset = new DefaultPreset();

		for (String presetName : availablePresets) {
			defaultPreset.loadPreset(presetName, false);
		}

		loadPreset(ConfigurationHandler.defaultPreset);
	}
	
	public static void setPrefabOption(String option, Object value) {
		presetParams.put(option, value);
	}
	
	public static Object getPrefabOption(String name) {
		return presetParams.get(name);
	}
	
	public static HashMap<String, Object> getPrefabOptions() {
		return presetParams;
	}

	public void loadPreset(String presetName) {
		DefaultPreset defaultPreset = new DefaultPreset();
		defaultPreset.loadPreset(presetName, true);

		ModuleHandler moduleHandler = new ModuleHandler();
		moduleHandler.unloadModules();
		moduleHandler.loadModules();

		loadedPreset = presetName;
	}

	public void createPreset(String presetName) {

		DefaultPreset defaultPreset = new DefaultPreset();
		defaultPreset.loadPreset(presetName, false);

		ConfigurationHandler configurationHandler = new ConfigurationHandler();
		configurationHandler.addNewPreset(presetName);
	}

	public void removePreset(String presetName) {

		ConfigurationHandler configurationHandler = new ConfigurationHandler();
		configurationHandler.removePreset(presetName);

		File file = new File(UhcPlus.INSTANCE.getDataFolder() + File.separator + "presets", presetName + ".yml");

		file.delete();
	}

	public static void changedPresetOption() {

		DefaultPreset defaultPreset = new DefaultPreset();
		defaultPreset.writePreset(loadedPreset);

		ModuleHandler moduleHandler = new ModuleHandler();
		moduleHandler.unloadModules();
		moduleHandler.loadModules();
	}
}