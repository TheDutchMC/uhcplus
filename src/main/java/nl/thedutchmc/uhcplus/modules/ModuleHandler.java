package nl.thedutchmc.uhcplus.modules;

import org.bukkit.event.HandlerList;

import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.presets.PresetHandler;

public class ModuleHandler {

	private UhcPlus plugin;
	
	static ModuleOreAutoSmelt moduleOreAutoSmelt;
	static ModuleTreeFullRemove moduleTreeFullRemove;
	
	public ModuleHandler(UhcPlus plugin) {
		this.plugin = plugin;
				
	}
	
	public void loadModules() {
		
		moduleOreAutoSmelt = new ModuleOreAutoSmelt(plugin);
		moduleTreeFullRemove = new ModuleTreeFullRemove();
		
		if(PresetHandler.moduleOreAutoSmelt) {
			plugin.getServer().getPluginManager().registerEvents(moduleOreAutoSmelt, plugin);
			
		} 

		if(PresetHandler.moduleTreeFullRemove) {
			System.out.println("we should enable the module!");
			plugin.getServer().getPluginManager().registerEvents(moduleTreeFullRemove, plugin);
			
		}
	}
	
	public void unloadModules() {
		if(!PresetHandler.moduleOreAutoSmelt) {
			HandlerList.unregisterAll(moduleOreAutoSmelt);
			
		}
		
		if(!PresetHandler.moduleTreeFullRemove) {
			HandlerList.unregisterAll(moduleTreeFullRemove);
			
		}
	}
}
