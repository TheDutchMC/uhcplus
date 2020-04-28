package nl.thedutchmc.uhcplus.modules;

import org.bukkit.event.HandlerList;

import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.modules.moduleListeners.ModuleLeaveDecay;
import nl.thedutchmc.uhcplus.modules.moduleListeners.ModuleOreAutoSmelt;
import nl.thedutchmc.uhcplus.modules.moduleListeners.ModuleTreeFullRemove;
import nl.thedutchmc.uhcplus.presets.PresetHandler;

public class ModuleHandler {

	private UhcPlus plugin;
	
	static ModuleOreAutoSmelt moduleOreAutoSmelt;
	static ModuleTreeFullRemove moduleTreeFullRemove;
	static ModuleLeaveDecay moduleLeaveDecay;
	
	public ModuleHandler(UhcPlus plugin) {
		this.plugin = plugin;
				
	}
	
	public void loadModules() {
		
		moduleOreAutoSmelt = new ModuleOreAutoSmelt(plugin);
		moduleTreeFullRemove = new ModuleTreeFullRemove();
		moduleLeaveDecay = new ModuleLeaveDecay(plugin);
		
		if(PresetHandler.moduleOreAutoSmelt) {
			plugin.getServer().getPluginManager().registerEvents(moduleOreAutoSmelt, plugin);
			
		} 

		if(PresetHandler.moduleTreeFullRemove) {
			System.out.println("we should enable the module!");
			plugin.getServer().getPluginManager().registerEvents(moduleTreeFullRemove, plugin);
			
		}
		
		//Leave decay is on by default
		plugin.getServer().getPluginManager().registerEvents(moduleLeaveDecay, plugin);
		
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
