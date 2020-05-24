package nl.thedutchmc.uhcplus.modules;

import org.bukkit.event.HandlerList;

import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.discord.ModuleProximityVoice;
import nl.thedutchmc.uhcplus.modules.moduleListeners.*;
import nl.thedutchmc.uhcplus.presets.PresetHandler;
import nl.thedutchmc.uhcplus.uhc.Recipes;

public class ModuleHandler {

	private UhcPlus plugin;
	
	static ModuleOreAutoSmelt moduleOreAutoSmelt;
	static ModuleTreeFullRemove moduleTreeFullRemove;
	static ModuleLeaveDecay moduleLeaveDecay;
	static ModuleEnchantedTools moduleEnchantedTools;
	static ModuleInfiniteEnchanting moduleInfiniteEnchanting;
	static ModuleSheepDropString moduleSheepDropString;
	static ModuleGravelDropArrow moduleGravelDropArrow;
	static ModuleDissalowGrindingEnchantedTools moduleDissalowGrindingEnchantedTools;
	
	static Recipes recipes;
	
	static ModuleProximityVoice moduleProximityVoice;
	
	
	
	public ModuleHandler(UhcPlus plugin) {
		this.plugin = plugin;
				
	}
	
	public void loadModules() {
		
		moduleOreAutoSmelt = new ModuleOreAutoSmelt(plugin);
		moduleTreeFullRemove = new ModuleTreeFullRemove();
		moduleLeaveDecay = new ModuleLeaveDecay(plugin);
		moduleEnchantedTools = new ModuleEnchantedTools();
		moduleInfiniteEnchanting = new ModuleInfiniteEnchanting();
		moduleSheepDropString = new ModuleSheepDropString();
		moduleGravelDropArrow = new ModuleGravelDropArrow();	
		moduleDissalowGrindingEnchantedTools = new ModuleDissalowGrindingEnchantedTools(plugin);
		
		recipes = new Recipes(plugin);
		moduleProximityVoice = new ModuleProximityVoice(plugin);
	
		if(PresetHandler.moduleOreAutoSmelt) plugin.getServer().getPluginManager().registerEvents(moduleOreAutoSmelt, plugin);

		if(PresetHandler.moduleTreeFullRemove) plugin.getServer().getPluginManager().registerEvents(moduleTreeFullRemove, plugin);

		if(PresetHandler.moduleLeaveDecay) plugin.getServer().getPluginManager().registerEvents(moduleLeaveDecay, plugin);
		
		if(PresetHandler.moduleEnchantedTools) plugin.getServer().getPluginManager().registerEvents(moduleEnchantedTools, plugin);
				
		if(PresetHandler.moduleInfiniteEnchanting) plugin.getServer().getPluginManager().registerEvents(moduleInfiniteEnchanting, plugin);
		
		if(PresetHandler.moduleSheepDropString) plugin.getServer().getPluginManager().registerEvents(moduleSheepDropString, plugin);
		
		if(PresetHandler.moduleGravelDropArrow) plugin.getServer().getPluginManager().registerEvents(moduleGravelDropArrow, plugin);
		
		if(PresetHandler.moduleDissalowGrindingEnchantedTools) plugin.getServer().getPluginManager().registerEvents(moduleDissalowGrindingEnchantedTools, plugin);
		
		if(PresetHandler.moduleLightGoldenApple && !Recipes.lightGoldenAppleRegistered) plugin.getServer().addRecipe(recipes.getLightGoldenAppleRecipe());
		
		if(PresetHandler.moduleLightAnvil && !Recipes.lightAnvilRegistered) plugin.getServer().addRecipe(recipes.getLightAnvilRecipe());
		
		System.out.println("Module status: " + PresetHandler.ModuleProximityVoice);
		if(PresetHandler.ModuleProximityVoice) moduleProximityVoice.enableModule();
	}
	
	public void unloadModules() {
		if(!PresetHandler.moduleOreAutoSmelt) HandlerList.unregisterAll(moduleOreAutoSmelt);
		
		if(!PresetHandler.moduleTreeFullRemove) HandlerList.unregisterAll(moduleTreeFullRemove);
		
		if(!PresetHandler.moduleLeaveDecay) HandlerList.unregisterAll(moduleLeaveDecay);
		
		if(!PresetHandler.moduleEnchantedTools) HandlerList.unregisterAll(moduleEnchantedTools);
		
		if(!PresetHandler.moduleInfiniteEnchanting) HandlerList.unregisterAll(moduleInfiniteEnchanting);
		
		if(!PresetHandler.moduleSheepDropString) HandlerList.unregisterAll(moduleSheepDropString);
		
		if(!PresetHandler.moduleGravelDropArrow) HandlerList.unregisterAll(moduleGravelDropArrow);
		
		if(!PresetHandler.moduleDissalowGrindingEnchantedTools) HandlerList.unregisterAll(moduleDissalowGrindingEnchantedTools);
		
		if(!PresetHandler.moduleLightGoldenApple && Recipes.lightGoldenAppleRegistered) {
			if(Recipes.lightGoldenAppleKey != null) plugin.getServer().removeRecipe(Recipes.lightGoldenAppleKey);
		}
		
		if(!PresetHandler.moduleLightAnvil && Recipes.lightAnvilRegistered) {
			if(Recipes.lightAnvilKey != null) plugin.getServer().removeRecipe(Recipes.lightAnvilKey);
		}
		
		if(!PresetHandler.ModuleProximityVoice && ModuleProximityVoice.isEnabled) moduleProximityVoice.disableModule();
	}
}
