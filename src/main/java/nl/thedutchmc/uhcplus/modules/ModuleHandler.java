package nl.thedutchmc.uhcplus.modules;

import org.bukkit.event.HandlerList;

import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.presetHandler.PresetHandler;

public class ModuleHandler {

	private UhcPlus plugin;
	
	static ModuleOreAutoSmelt moduleOreAutoSmelt;
	
	public ModuleHandler(UhcPlus plugin) {
		this.plugin = plugin;
				
	}
	
	public void loadModules() {
		
		moduleOreAutoSmelt = new ModuleOreAutoSmelt(plugin);
		
		if(PresetHandler.moduleOreAutoSmelt) {
			plugin.getServer().getPluginManager().registerEvents(moduleOreAutoSmelt, plugin);
		}
	}
	
	public void unloadModules() {
		if(!PresetHandler.moduleOreAutoSmelt) {
			HandlerList.unregisterAll(moduleOreAutoSmelt);
		}
	}
}
