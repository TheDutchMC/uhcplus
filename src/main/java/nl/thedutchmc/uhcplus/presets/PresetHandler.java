package nl.thedutchmc.uhcplus.presets;

import java.io.File;
import java.util.List;

import nl.thedutchmc.uhcplus.ConfigurationHandler;
import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.modules.ModuleHandler;

public class PresetHandler {

	public static String loadedPreset;

	public static boolean moduleOreAutoSmelt, moduleTreeFullRemove, moduleLeaveDecay, moduleEnchantedTools,
			moduleInfiniteEnchanting, moduleSheepDropString, moduleGravelDropArrow,
			moduleDissalowGrindingEnchantedTools, moduleLightGoldenApple, moduleLightAnvil, moduleDioriteDamage,
			moduleAntiCheat, moduleAxeOfDestruction, axeOfDestructionLevelling, moduleSwordOfDivinity,
			swordOfDivinityLevelling, moduleTeamInventory, moduleOneHeartStart, moduleSlimeBoost, moduleSticksFromLogs, moduleStatistics,
			moduleRevive;
	public static int maxTeamCount, maxPlayerCountPerTeam, moduleOreAutoSmeltIngotDrop, timeToPvp, worldBorderSize,
			worldBorderShrinkAfter, worldBorderShrinkTo, gameTime, moduleAntiCheatTime,
			moduleAxeOfDestructionLevelOneTime, moduleAxeOfDestructionLevelTwoTime, moduleSwordOfDivinityLevelOneTime,
			moduleSwordOfDivinityLevelTwoTime;

	public void loadPresets() {
		List<String> availablePresets = ConfigurationHandler.availablePresets;

		DefaultPreset defaultPreset = new DefaultPreset();
		defaultPreset = new DefaultPreset();

		for (String presetName : availablePresets) {
			defaultPreset.loadPreset(presetName, false);
		}

		loadPreset(ConfigurationHandler.defaultPreset);
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