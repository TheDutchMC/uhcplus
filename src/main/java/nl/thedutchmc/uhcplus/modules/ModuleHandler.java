package nl.thedutchmc.uhcplus.modules;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.modules.moduleAntiCheat.ModuleAntiCheat;
import nl.thedutchmc.uhcplus.modules.moduleClasses.ModuleAxeOfDestruction;
import nl.thedutchmc.uhcplus.modules.moduleClasses.ModuleDioriteDamage;
import nl.thedutchmc.uhcplus.modules.moduleClasses.ModuleDissalowGrindingEnchantedTools;
import nl.thedutchmc.uhcplus.modules.moduleClasses.ModuleEnchantedTools;
import nl.thedutchmc.uhcplus.modules.moduleClasses.ModuleGravelDropArrow;
import nl.thedutchmc.uhcplus.modules.moduleClasses.ModuleInfiniteEnchanting;
import nl.thedutchmc.uhcplus.modules.moduleClasses.ModuleLeaveDecay;
import nl.thedutchmc.uhcplus.modules.moduleClasses.ModuleOneHeartStart;
import nl.thedutchmc.uhcplus.modules.moduleClasses.ModuleOreAutoSmelt;
import nl.thedutchmc.uhcplus.modules.moduleClasses.ModuleSheepDropString;
import nl.thedutchmc.uhcplus.modules.moduleClasses.ModuleSwordOfDivinity;
import nl.thedutchmc.uhcplus.modules.moduleClasses.ModuleTreeFullRemove;
import nl.thedutchmc.uhcplus.presets.PresetHandler;
import nl.thedutchmc.uhcplus.uhc.Recipes;

public class ModuleHandler {

	static ModuleOreAutoSmelt moduleOreAutoSmelt;
	static ModuleTreeFullRemove moduleTreeFullRemove;
	static ModuleLeaveDecay moduleLeaveDecay;
	static ModuleEnchantedTools moduleEnchantedTools;
	static ModuleInfiniteEnchanting moduleInfiniteEnchanting;
	static ModuleSheepDropString moduleSheepDropString;
	static ModuleGravelDropArrow moduleGravelDropArrow;
	static ModuleDissalowGrindingEnchantedTools moduleDissalowGrindingEnchantedTools;
	static ModuleDioriteDamage moduleDioriteDamage;
	static ModuleAxeOfDestruction moduleAxeOfDestruction;
	static ModuleSwordOfDivinity moduleSwordOfDivinity;
	static ModuleOneHeartStart moduleOneHeartStart;

	static Recipes recipes;
	static ModuleAntiCheat moduleAntiCheat;

	public void loadModules() {

		moduleOreAutoSmelt = new ModuleOreAutoSmelt();
		moduleTreeFullRemove = new ModuleTreeFullRemove();
		moduleLeaveDecay = new ModuleLeaveDecay();
		moduleEnchantedTools = new ModuleEnchantedTools();
		moduleInfiniteEnchanting = new ModuleInfiniteEnchanting();
		moduleSheepDropString = new ModuleSheepDropString();
		moduleGravelDropArrow = new ModuleGravelDropArrow();
		moduleDissalowGrindingEnchantedTools = new ModuleDissalowGrindingEnchantedTools();
		moduleDioriteDamage = new ModuleDioriteDamage();
		moduleAntiCheat = new ModuleAntiCheat();
		moduleAxeOfDestruction = new ModuleAxeOfDestruction();
		moduleSwordOfDivinity = new ModuleSwordOfDivinity();
		moduleOneHeartStart = new ModuleOneHeartStart();

		recipes = new Recipes();

		UhcPlus plugin = UhcPlus.INSTANCE;

		if (PresetHandler.moduleOreAutoSmelt)
			plugin.getServer().getPluginManager().registerEvents(moduleOreAutoSmelt, plugin);

		if (PresetHandler.moduleTreeFullRemove)
			plugin.getServer().getPluginManager().registerEvents(moduleTreeFullRemove, plugin);

		if (PresetHandler.moduleLeaveDecay)
			plugin.getServer().getPluginManager().registerEvents(moduleLeaveDecay, plugin);

		if (PresetHandler.moduleEnchantedTools)
			plugin.getServer().getPluginManager().registerEvents(moduleEnchantedTools, plugin);

		if (PresetHandler.moduleInfiniteEnchanting)
			plugin.getServer().getPluginManager().registerEvents(moduleInfiniteEnchanting, plugin);

		if (PresetHandler.moduleSheepDropString)
			plugin.getServer().getPluginManager().registerEvents(moduleSheepDropString, plugin);

		if (PresetHandler.moduleGravelDropArrow)
			plugin.getServer().getPluginManager().registerEvents(moduleGravelDropArrow, plugin);

		if (PresetHandler.moduleDissalowGrindingEnchantedTools)
			plugin.getServer().getPluginManager().registerEvents(moduleDissalowGrindingEnchantedTools, plugin);

		if (PresetHandler.moduleDioriteDamage) {
			plugin.getServer().getPluginManager().registerEvents(moduleDioriteDamage, plugin);
			moduleDioriteDamage.dioriteDamage();
		}

		if (PresetHandler.moduleLightGoldenApple && !Recipes.lightGoldenAppleRegistered)
			plugin.getServer().addRecipe(recipes.getLightGoldenAppleRecipe());

		if (PresetHandler.moduleLightAnvil && !Recipes.lightAnvilRegistered)
			plugin.getServer().addRecipe(recipes.getLightAnvilRecipe());

		if (PresetHandler.moduleAxeOfDestruction && !Recipes.axeOfDestructionRegistered)
			plugin.getServer().addRecipe(recipes.getAxeOfDestructionRecipe());

		if (PresetHandler.moduleSwordOfDivinity && !Recipes.swordOfDivinityRegistered)
			plugin.getServer().addRecipe(recipes.getSwordOfDivinity());

		if (PresetHandler.moduleAntiCheat)
			moduleAntiCheat.enableModule();

		if (PresetHandler.axeOfDestructionLevelling)
			Bukkit.getServer().getPluginManager().registerEvents(moduleAxeOfDestruction, plugin);

		if (PresetHandler.swordOfDivinityLevelling)
			Bukkit.getServer().getPluginManager().registerEvents(moduleSwordOfDivinity, plugin);

		if (PresetHandler.moduleOneHeartStart)
			Bukkit.getServer().getPluginManager().registerEvents(moduleOneHeartStart, plugin);
	}

	public void unloadModules() {
		if (!PresetHandler.moduleOreAutoSmelt)
			HandlerList.unregisterAll(moduleOreAutoSmelt);

		if (!PresetHandler.moduleTreeFullRemove)
			HandlerList.unregisterAll(moduleTreeFullRemove);

		if (!PresetHandler.moduleLeaveDecay)
			HandlerList.unregisterAll(moduleLeaveDecay);

		if (!PresetHandler.moduleEnchantedTools)
			HandlerList.unregisterAll(moduleEnchantedTools);

		if (!PresetHandler.moduleInfiniteEnchanting)
			HandlerList.unregisterAll(moduleInfiniteEnchanting);

		if (!PresetHandler.moduleSheepDropString)
			HandlerList.unregisterAll(moduleSheepDropString);

		if (!PresetHandler.moduleGravelDropArrow)
			HandlerList.unregisterAll(moduleGravelDropArrow);

		if (!PresetHandler.moduleDissalowGrindingEnchantedTools)
			HandlerList.unregisterAll(moduleDissalowGrindingEnchantedTools);

		if(!PresetHandler.moduleDioriteDamage) {
			HandlerList.unregisterAll(moduleDioriteDamage);
			if(moduleDioriteDamage != null) moduleDioriteDamage.cancelDamageTask();
		}
		
		if (!PresetHandler.moduleAntiCheat)
			moduleAntiCheat.disableModule();

		if (!PresetHandler.axeOfDestructionLevelling)
			HandlerList.unregisterAll(moduleAxeOfDestruction);

		if (!PresetHandler.swordOfDivinityLevelling)
			HandlerList.unregisterAll(moduleSwordOfDivinity);

		if (!PresetHandler.moduleOneHeartStart)
			HandlerList.unregisterAll(moduleOneHeartStart);

		/*
		 * Iterator<Recipe> it = plugin.getServer().recipeIterator();
		 * while(it.hasNext()) { Recipe itRecipe = it.next(); if(itRecipe instanceof
		 * ShapedRecipe) { ShapedRecipe itShaped = (ShapedRecipe) itRecipe;
		 * if(itShaped.equals(recipes.getLightAnvilRecipe())) it.remove();
		 * 
		 * if(itShaped.equals(recipes.getLightGoldenAppleRecipe())) it.remove(); } }
		 */

		/*
		 * if(!PresetHandler.moduleLightGoldenApple &&
		 * Recipes.lightGoldenAppleRegistered) { if(Recipes.lightGoldenAppleKey != null)
		 * plugin.getServer().removeRecipe(Recipes.lightGoldenAppleKey); }
		 * 
		 * if(!PresetHandler.moduleLightAnvil && Recipes.lightAnvilRegistered) {
		 * if(Recipes.lightAnvilKey != null)
		 * plugin.getServer().removeRecipe(Recipes.lightAnvilKey); }
		 */

		if (!PresetHandler.moduleDioriteDamage)
			HandlerList.unregisterAll(moduleDioriteDamage);

	}
}
