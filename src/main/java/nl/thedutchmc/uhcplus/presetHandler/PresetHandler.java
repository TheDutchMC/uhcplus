package nl.thedutchmc.uhcplus.presetHandler;

import nl.thedutchmc.uhcplus.ConfigurationHandler;
import nl.thedutchmc.uhcplus.UhcPlus;

public class PresetHandler {

	public static int maxTeamCount, maxPlayerCountPerTeam;
	
	private UhcPlus plugin;
	
	public PresetHandler(UhcPlus plugin) {
		this.plugin = plugin;
	}
	
	public void loadPresets() {
		
		DefaultPreset defaultPreset = new DefaultPreset(plugin);
		defaultPreset.loadPreset();
	}
	
	public void loadActivePresetValues() {
		String active = ConfigurationHandler.activePreset;
		
		if(active.equals("default.yml")) {
			maxTeamCount = DefaultPreset.maxTeamCount;
			maxPlayerCountPerTeam = DefaultPreset.maxPlayerCountPerTeam;
		}
	}
}
