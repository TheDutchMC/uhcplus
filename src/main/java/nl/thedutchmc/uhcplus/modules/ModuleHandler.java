package nl.thedutchmc.uhcplus.modules;

import org.bukkit.event.HandlerList;

import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.modules.moduleListeners.ModuleEnchantedTools;
import nl.thedutchmc.uhcplus.modules.moduleListeners.ModuleInfiniteEnchanting;
import nl.thedutchmc.uhcplus.modules.moduleListeners.ModuleLeaveDecay;
import nl.thedutchmc.uhcplus.modules.moduleListeners.ModuleOreAutoSmelt;
import nl.thedutchmc.uhcplus.modules.moduleListeners.ModuleTreeFullRemove;
import nl.thedutchmc.uhcplus.presets.PresetHandler;

public class ModuleHandler {

	private UhcPlus plugin;
	
	static ModuleOreAutoSmelt moduleOreAutoSmelt;
	static ModuleTreeFullRemove moduleTreeFullRemove;
	static ModuleLeaveDecay moduleLeaveDecay;
	static ModuleEnchantedTools moduleEnchantedTools;
	static ModuleInfiniteEnchanting moduleInfiniteEnchanting;
	
	public ModuleHandler(UhcPlus plugin) {
		this.plugin = plugin;
				
	}
	
	public void loadModules() {
		
		moduleOreAutoSmelt = new ModuleOreAutoSmelt(plugin);
		moduleTreeFullRemove = new ModuleTreeFullRemove();
		moduleLeaveDecay = new ModuleLeaveDecay(plugin);
		moduleEnchantedTools = new ModuleEnchantedTools();
		moduleInfiniteEnchanting = new ModuleInfiniteEnchanting();
				
		if(PresetHandler.moduleOreAutoSmelt) {
			plugin.getServer().getPluginManager().registerEvents(moduleOreAutoSmelt, plugin);
		} 

		if(PresetHandler.moduleTreeFullRemove) {
			plugin.getServer().getPluginManager().registerEvents(moduleTreeFullRemove, plugin);
		}
		
		if(PresetHandler.moduleLeaveDecay) {
			plugin.getServer().getPluginManager().registerEvents(moduleLeaveDecay, plugin);
		}
		
		if(PresetHandler.moduleEnchantedTools) {
			plugin.getServer().getPluginManager().registerEvents(moduleEnchantedTools, plugin);
		}
				
		if(PresetHandler.moduleInfiniteEnchanting) {
			plugin.getServer().getPluginManager().registerEvents(moduleInfiniteEnchanting, plugin);
		}
		
	}
	
	public void unloadModules() {
		if(!PresetHandler.moduleOreAutoSmelt) {
			HandlerList.unregisterAll(moduleOreAutoSmelt);
		}
		
		if(!PresetHandler.moduleTreeFullRemove) {
			HandlerList.unregisterAll(moduleTreeFullRemove);
		}
		
		if(!PresetHandler.moduleLeaveDecay) {
			HandlerList.unregisterAll(moduleLeaveDecay);
		}
		
		if(!PresetHandler.moduleEnchantedTools) {
			HandlerList.unregisterAll(moduleEnchantedTools);
		}
		
		if(!PresetHandler.moduleInfiniteEnchanting) {
			HandlerList.unregisterAll(moduleInfiniteEnchanting);
		}
		
	}
}
