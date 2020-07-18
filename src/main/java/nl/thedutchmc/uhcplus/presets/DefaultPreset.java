package nl.thedutchmc.uhcplus.presets;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import nl.thedutchmc.uhcplus.UhcPlus;

public class DefaultPreset {

	private File presetConfigFile;
	private FileConfiguration presetConfig;

	public FileConfiguration getPresetConfig() {
		return this.presetConfig;
	}

	public void loadPreset(String presetName, boolean shouldReturn) {

		presetConfigFile = new File(UhcPlus.INSTANCE.getDataFolder() + File.separator + "presets", presetName + ".yml");

		presetConfig = YamlConfiguration.loadConfiguration(presetConfigFile);

		// Check if the config file exists, if it doesnt, create it.
		if (!presetConfigFile.exists()) {
			presetConfigFile.getParentFile().mkdirs();

			try {
				FileUtils.copyToFile(UhcPlus.INSTANCE.getResource("default.yml"), presetConfigFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Load the preset configuration file.
		try {
			presetConfig.load(presetConfigFile);

			if (shouldReturn) {
				readPreset();
			}
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
			// TODO better error handling
		}
	}

	public void readPreset() {

		// Boolean
		PresetHandler.moduleOreAutoSmelt = Boolean.valueOf(this.getPresetConfig().getString("moduleOreAutoSmelt"));
		PresetHandler.moduleTreeFullRemove = Boolean.valueOf(this.getPresetConfig().getString("moduleTreeFullRemove"));
		PresetHandler.moduleLeaveDecay = Boolean.valueOf(this.getPresetConfig().getString("moduleLeaveDecay"));
		PresetHandler.moduleEnchantedTools = Boolean.valueOf(this.getPresetConfig().getString("moduleEnchantedTools"));
		PresetHandler.moduleInfiniteEnchanting = Boolean.valueOf(this.getPresetConfig().getString("moduleInfiniteEnchanting"));
		PresetHandler.moduleSheepDropString = Boolean.valueOf(this.getPresetConfig().getString("moduleSheepDropString"));
		PresetHandler.moduleGravelDropArrow = Boolean.valueOf(this.getPresetConfig().getString("moduleGravelDropArrow"));
		PresetHandler.moduleDissalowGrindingEnchantedTools = Boolean.valueOf(this.getPresetConfig().getString("moduleDissalowGrindingEnchantedTools"));
		PresetHandler.moduleLightGoldenApple = Boolean.valueOf(this.getPresetConfig().getString("moduleLightGoldenApple"));
		PresetHandler.moduleLightAnvil = Boolean.valueOf(this.getPresetConfig().getString("moduleLightAnvil"));
		PresetHandler.moduleAntiCheat = Boolean.valueOf(this.getPresetConfig().getString("moduleAntiCheat"));
		PresetHandler.moduleAxeOfDestruction = Boolean.valueOf(this.getPresetConfig().getString("moduleAxeOfDestruction"));
		PresetHandler.axeOfDestructionLevelling = Boolean.valueOf(this.getPresetConfig().getString("axeOfDestructionLevelling"));
		PresetHandler.moduleSwordOfDivinity = Boolean.valueOf(this.getPresetConfig().getString("moduleSwordOfDivinity"));
		PresetHandler.swordOfDivinityLevelling = Boolean.valueOf(this.getPresetConfig().getString("swordOfDivinityLevelling"));
		PresetHandler.moduleTeamInventory = Boolean.valueOf(this.getPresetConfig().getString("moduleTeamInventory"));
		PresetHandler.moduleOneHeartStart = Boolean.valueOf(this.getPresetConfig().getString("moduleOneHeartStart"));
		PresetHandler.moduleDioriteDamage = Boolean.valueOf(this.getPresetConfig().getString("moduleDioriteDamage"));
		PresetHandler.moduleSlimeBoost = Boolean.valueOf(this.getPresetConfig().getString("moduleSlimeBoost"));
		PresetHandler.moduleSticksFromLogs = Boolean.valueOf(this.getPresetConfig().getString("moduleSticksFromLogs"));
		
		// Integer
		PresetHandler.maxTeamCount = Integer.valueOf(this.getPresetConfig().getString("maxTeamCount"));
		PresetHandler.maxPlayerCountPerTeam = Integer
				.valueOf(this.getPresetConfig().getString("maxPlayerCountPerTeam"));
		PresetHandler.moduleOreAutoSmeltIngotDrop = Integer
				.valueOf(this.getPresetConfig().getString("moduleOreAutoSmeltIngotDrop"));
		PresetHandler.timeToPvp = Integer.valueOf(this.getPresetConfig().getString("timeToPvp"));
		PresetHandler.worldBorderSize = Integer.valueOf(this.getPresetConfig().getString("worldBorderSize"));
		PresetHandler.worldBorderShrinkAfter = Integer
				.valueOf(this.getPresetConfig().getString("worldBorderShrinkAfter"));
		PresetHandler.worldBorderShrinkTo = Integer.valueOf(this.getPresetConfig().getString("worldBorderShrinkTo"));
		PresetHandler.gameTime = Integer.valueOf(this.getPresetConfig().getString("gameTime"));
		PresetHandler.moduleAntiCheatTime = Integer.valueOf(this.getPresetConfig().getString("moduleAntiCheatTime"));
		PresetHandler.moduleAxeOfDestructionLevelOneTime = Integer
				.valueOf(this.getPresetConfig().getString("moduleAxeOfDestructionLevelOneTime"));
		PresetHandler.moduleAxeOfDestructionLevelTwoTime = Integer
				.valueOf(this.getPresetConfig().getString("moduleAxeOfDestructionLevelTwoTime"));
		PresetHandler.moduleSwordOfDivinityLevelOneTime = Integer
				.valueOf(this.getPresetConfig().getString("moduleSwordOfDivinityLevelOneTime"));
		PresetHandler.moduleSwordOfDivinityLevelTwoTime = Integer
				.valueOf(this.getPresetConfig().getString("moduleSwordOfDivinityLevelTwoTime"));

	}

	public void writePreset(String presetName) {
		loadPreset(presetName, false);

		this.getPresetConfig().set("maxTeamCount", PresetHandler.maxTeamCount);
		this.getPresetConfig().set("maxPlayerCountPerTeam", PresetHandler.maxPlayerCountPerTeam);
		this.getPresetConfig().set("moduleOreAutoSmelt", PresetHandler.moduleOreAutoSmelt);
		this.getPresetConfig().set("moduleOreAutoSmeltIngotDrop", PresetHandler.moduleOreAutoSmeltIngotDrop);
		this.getPresetConfig().set("timeToPvp", PresetHandler.timeToPvp);
		this.getPresetConfig().set("worldBorderSize", PresetHandler.worldBorderSize);
		this.getPresetConfig().set("worldBorderShrinkAfter", PresetHandler.worldBorderShrinkAfter);
		this.getPresetConfig().set("worldBorderShrinkTo", PresetHandler.worldBorderShrinkTo);
		this.getPresetConfig().set("gameTime", PresetHandler.gameTime);
		this.getPresetConfig().set("moduleTreeFullRemove", PresetHandler.moduleTreeFullRemove);
		this.getPresetConfig().set("moduleLeaveDecay", PresetHandler.moduleLeaveDecay);
		this.getPresetConfig().set("moduleEnchantedTools", PresetHandler.moduleEnchantedTools);
		this.getPresetConfig().set("moduleInfiniteEnchanting", PresetHandler.moduleInfiniteEnchanting);
		this.getPresetConfig().set("moduleSheepDropString", PresetHandler.moduleSheepDropString);
		this.getPresetConfig().set("moduleGravelDropArrow", PresetHandler.moduleGravelDropArrow);
		this.getPresetConfig().set("moduleDissalowGrindingEnchantedTools",
				PresetHandler.moduleDissalowGrindingEnchantedTools);
		this.getPresetConfig().set("moduleLightGoldenApple", PresetHandler.moduleLightGoldenApple);
		this.getPresetConfig().set("moduleLightAnvil", PresetHandler.moduleLightAnvil);
		this.getPresetConfig().set("moduleAntiCheat", PresetHandler.moduleAntiCheat);
		this.getPresetConfig().set("moduleAntiCheatTime", PresetHandler.moduleAntiCheatTime);
		this.getPresetConfig().set("moduleAxeOfDestruction", PresetHandler.moduleAxeOfDestruction);
		this.getPresetConfig().set("axeOfDestructionLevelling", PresetHandler.axeOfDestructionLevelling);
		this.getPresetConfig().set("moduleAxeOfDestructionLevelOneTime",
				PresetHandler.moduleAxeOfDestructionLevelOneTime);
		this.getPresetConfig().set("moduleAxeOfDestructionLevelTwoTime",
				PresetHandler.moduleAxeOfDestructionLevelTwoTime);
		this.getPresetConfig().set("moduleSwordOfDivinity", PresetHandler.moduleSwordOfDivinity);
		this.getPresetConfig().set("swordOfDivinityLevelling", PresetHandler.swordOfDivinityLevelling);
		this.getPresetConfig().set("moduleSwordOfDivinityLevelOneTime",
				PresetHandler.moduleSwordOfDivinityLevelOneTime);
		this.getPresetConfig().set("moduleSwordOfDivinityLevelTwoTime",
				PresetHandler.moduleSwordOfDivinityLevelTwoTime);
		this.getPresetConfig().set("moduleTeamInventory", PresetHandler.moduleTeamInventory);
		this.getPresetConfig().set("moduleOneHeartStart", PresetHandler.moduleOneHeartStart);
		this.getPresetConfig().set("moduleDioriteDamage", PresetHandler.moduleDioriteDamage);
		this.getPresetConfig().set("moduleSlimeBoost", PresetHandler.moduleSlimeBoost);
		this.getPresetConfig().set("moduleSticksFromLogs", PresetHandler.moduleSticksFromLogs);
		
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