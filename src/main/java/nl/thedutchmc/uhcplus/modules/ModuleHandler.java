package nl.thedutchmc.uhcplus.modules;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.modules.modules.ModuleAxeOfDestruction;
import nl.thedutchmc.uhcplus.modules.modules.ModuleDissalowGrindingEnchantedTools;
import nl.thedutchmc.uhcplus.modules.modules.ModuleEnchantedTools;
import nl.thedutchmc.uhcplus.modules.modules.ModuleGravelDropArrow;
import nl.thedutchmc.uhcplus.modules.modules.ModuleInfiniteEnchanting;
import nl.thedutchmc.uhcplus.modules.modules.ModuleLeaveDecay;
import nl.thedutchmc.uhcplus.modules.modules.ModuleOneHeartStart;
import nl.thedutchmc.uhcplus.modules.modules.ModuleOreAutoSmelt;
import nl.thedutchmc.uhcplus.modules.modules.ModuleSheepDropString;
import nl.thedutchmc.uhcplus.modules.modules.ModuleSlimeBoost;
import nl.thedutchmc.uhcplus.modules.modules.ModuleSwordOfDivinity;
import nl.thedutchmc.uhcplus.modules.modules.ModuleTreeFullRemove;
import nl.thedutchmc.uhcplus.modules.modules.moduleAntiCheat.ModuleAntiCheat;
import nl.thedutchmc.uhcplus.modules.modules.moduleDioriteDamage.ModuleDioriteDamage;
import nl.thedutchmc.uhcplus.modules.modules.moduleStatistics.ModuleStatistics;
import nl.thedutchmc.uhcplus.modules.modules.recipes.RecipeAxeOfDestruction;
import nl.thedutchmc.uhcplus.modules.modules.recipes.RecipeLightGoldenApple;
import nl.thedutchmc.uhcplus.modules.modules.recipes.RecipeReviveToken;
import nl.thedutchmc.uhcplus.modules.modules.recipes.RecipeSticksFromLogs;
import nl.thedutchmc.uhcplus.modules.modules.recipes.RecipeSwordOfDivinity;
import nl.thedutchmc.uhcplus.modules.modules.recipes.RecipeLightAnvil;
import nl.thedutchmc.uhcplus.presets.PresetHandler;

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
	static ModuleSlimeBoost moduleSlimeBoost;

	static ModuleAntiCheat moduleAntiCheat;
	static ModuleStatistics moduleStatistics;

	//Recipes
	static RecipeAxeOfDestruction recipeAxeOfDestruction;
	static RecipeLightAnvil recipeLightAnvil;
	static RecipeLightGoldenApple recipeLightGoldenApple;
	static RecipeSwordOfDivinity recipeSwordOfDivinity;
	static RecipeSticksFromLogs recipeSticksFromLogs;
	static RecipeReviveToken recipeReviveToken;
	
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
		moduleSlimeBoost = new ModuleSlimeBoost();
		moduleStatistics = new ModuleStatistics();
		
		//Recipes
		recipeAxeOfDestruction = new RecipeAxeOfDestruction();
		recipeLightAnvil = new RecipeLightAnvil();
		recipeLightGoldenApple = new RecipeLightGoldenApple();
		recipeSwordOfDivinity = new RecipeSwordOfDivinity();
		recipeSticksFromLogs = new RecipeSticksFromLogs();
		recipeReviveToken = new RecipeReviveToken();

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

		if (PresetHandler.moduleLightGoldenApple && !RecipeLightGoldenApple.appleRegistered)
			plugin.getServer().addRecipe(recipeLightGoldenApple.getLightGoldenAppleRecipe());

		if (PresetHandler.moduleLightAnvil && !RecipeLightAnvil.anvilRegistered)
			plugin.getServer().addRecipe(recipeLightAnvil.getLightAnvilRecipe());

		if (PresetHandler.moduleAxeOfDestruction && !RecipeAxeOfDestruction.axeRegistered)
			plugin.getServer().addRecipe(recipeAxeOfDestruction.getAxeOfDestructionRecipe());

		if (PresetHandler.moduleSwordOfDivinity && !RecipeSwordOfDivinity.swordRegistered)
			plugin.getServer().addRecipe(recipeSwordOfDivinity.getSwordOfDivinity());

		if (PresetHandler.moduleAntiCheat)
			moduleAntiCheat.enableModule();

		if (PresetHandler.axeOfDestructionLevelling)
			Bukkit.getServer().getPluginManager().registerEvents(moduleAxeOfDestruction, plugin);

		if (PresetHandler.swordOfDivinityLevelling)
			Bukkit.getServer().getPluginManager().registerEvents(moduleSwordOfDivinity, plugin);

		if (PresetHandler.moduleOneHeartStart)
			Bukkit.getServer().getPluginManager().registerEvents(moduleOneHeartStart, plugin);
		
		if(PresetHandler.moduleSlimeBoost) {
			Bukkit.getServer().getPluginManager().registerEvents(moduleSlimeBoost, plugin);
			moduleSlimeBoost.slimeBoost();
		}
		
		if(PresetHandler.moduleSticksFromLogs)
			recipeSticksFromLogs.setupRecipe();
		
		if(PresetHandler.moduleStatistics) 
			moduleStatistics.loadModule();
		
		if(PresetHandler.moduleRevive) {
			plugin.getServer().addRecipe(recipeReviveToken.getReviveTokenRecipe());
		}
	
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

		if(!PresetHandler.moduleSlimeBoost) {
			HandlerList.unregisterAll(moduleSlimeBoost);
			if(moduleSlimeBoost != null) moduleSlimeBoost.cancelBoostTask();
		}
		
		if(!PresetHandler.moduleStatistics) {
			
		}
		
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
	}
}
