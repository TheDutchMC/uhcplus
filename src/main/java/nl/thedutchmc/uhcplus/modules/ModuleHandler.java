package nl.thedutchmc.uhcplus.modules;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.HandlerList;

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
import nl.thedutchmc.uhcplus.UhcPlus;

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

		if ((boolean) PresetHandler.getPrefabOption("moduleOreAutoSmelt"))
			plugin.getServer().getPluginManager().registerEvents(moduleOreAutoSmelt, plugin);

		if ((boolean) PresetHandler.getPrefabOption("moduleTreeFullRemove"))
			plugin.getServer().getPluginManager().registerEvents(moduleTreeFullRemove, plugin);

		if ((boolean) PresetHandler.getPrefabOption("moduleLeaveDecay"))
			plugin.getServer().getPluginManager().registerEvents(moduleLeaveDecay, plugin);

		if ((boolean) PresetHandler.getPrefabOption("moduleEnchantedTools"))
			plugin.getServer().getPluginManager().registerEvents(moduleEnchantedTools, plugin);

		if ((boolean) PresetHandler.getPrefabOption("moduleInfiniteEnchanting"))
			plugin.getServer().getPluginManager().registerEvents(moduleInfiniteEnchanting, plugin);

		if ((boolean) PresetHandler.getPrefabOption("moduleSheepDropString"))
			plugin.getServer().getPluginManager().registerEvents(moduleSheepDropString, plugin);

		if ((boolean) PresetHandler.getPrefabOption("moduleGravelDropArrow"))
			plugin.getServer().getPluginManager().registerEvents(moduleGravelDropArrow, plugin);

		if ((boolean) PresetHandler.getPrefabOption("moduleDissalowGrindingEnchantedTools"))
			plugin.getServer().getPluginManager().registerEvents(moduleDissalowGrindingEnchantedTools, plugin);

		if ((boolean) PresetHandler.getPrefabOption("moduleDioriteDamage")) {
			plugin.getServer().getPluginManager().registerEvents(moduleDioriteDamage, plugin);
			moduleDioriteDamage.dioriteDamage();
		}

		if ((boolean) PresetHandler.getPrefabOption("moduleLightGoldenApple") && !RecipeLightGoldenApple.appleRegistered)
			plugin.getServer().addRecipe(recipeLightGoldenApple.getLightGoldenAppleRecipe());

		if ((boolean) PresetHandler.getPrefabOption("moduleLightAnvil") && !RecipeLightAnvil.anvilRegistered)
			plugin.getServer().addRecipe(recipeLightAnvil.getLightAnvilRecipe());

		if ((boolean) PresetHandler.getPrefabOption("moduleAxeOfDestruction") && !RecipeAxeOfDestruction.axeRegistered)
			plugin.getServer().addRecipe(recipeAxeOfDestruction.getAxeOfDestructionRecipe());

		if ((boolean) PresetHandler.getPrefabOption("moduleSwordOfDivinity") && !RecipeSwordOfDivinity.swordRegistered)
			plugin.getServer().addRecipe(recipeSwordOfDivinity.getSwordOfDivinity());

		if ((boolean) PresetHandler.getPrefabOption("moduleAntiCheat"))
			moduleAntiCheat.enableModule();

		if ((boolean) PresetHandler.getPrefabOption("axeOfDestructionLevelling"))
			Bukkit.getServer().getPluginManager().registerEvents(moduleAxeOfDestruction, plugin);

		if ((boolean) PresetHandler.getPrefabOption("swordOfDivinityLevelling"))
			Bukkit.getServer().getPluginManager().registerEvents(moduleSwordOfDivinity, plugin);

		if ((boolean) PresetHandler.getPrefabOption("moduleOneHeartStart"))
			Bukkit.getServer().getPluginManager().registerEvents(moduleOneHeartStart, plugin);
		
		if((boolean) PresetHandler.getPrefabOption("moduleSlimeBoost")) {
			Bukkit.getServer().getPluginManager().registerEvents(moduleSlimeBoost, plugin);
			moduleSlimeBoost.slimeBoost();
		}
		
		if((boolean) PresetHandler.getPrefabOption("moduleSticksFromLogs"))
			recipeSticksFromLogs.setupRecipe();
		
		if((boolean) PresetHandler.getPrefabOption("moduleStatistics")) 
			moduleStatistics.loadModule();
		
		if((boolean) PresetHandler.getPrefabOption("moduleRevive")) {
			if(Bukkit.getRecipe((RecipeReviveToken.reviveTokenKey != null) ? RecipeReviveToken.reviveTokenKey : new NamespacedKey(UhcPlus.INSTANCE, "revive_token")) == null) {
				Bukkit.addRecipe(recipeReviveToken.getReviveTokenRecipe());
			}
		}
	}
	
	public void unloadModules() {
		if (!(boolean) PresetHandler.getPrefabOption("moduleOreAutoSmelt"))
			HandlerList.unregisterAll(moduleOreAutoSmelt);

		if (!(boolean) PresetHandler.getPrefabOption("moduleTreeFullRemove"))
			HandlerList.unregisterAll(moduleTreeFullRemove);

		if (!(boolean) PresetHandler.getPrefabOption("moduleLeaveDecay"))
			HandlerList.unregisterAll(moduleLeaveDecay);

		if (!(boolean) PresetHandler.getPrefabOption("moduleEnchantedTools"))
			HandlerList.unregisterAll(moduleEnchantedTools);

		if (!(boolean) PresetHandler.getPrefabOption("moduleInfiniteEnchanting"))
			HandlerList.unregisterAll(moduleInfiniteEnchanting);

		if (!(boolean) PresetHandler.getPrefabOption("moduleSheepDropString"))
			HandlerList.unregisterAll(moduleSheepDropString);

		if (!(boolean) PresetHandler.getPrefabOption("moduleGravelDropArrow"))
			HandlerList.unregisterAll(moduleGravelDropArrow);

		if (!(boolean) PresetHandler.getPrefabOption("moduleDissalowGrindingEnchantedTools"))
			HandlerList.unregisterAll(moduleDissalowGrindingEnchantedTools);

		if(!(boolean) PresetHandler.getPrefabOption("moduleDioriteDamage")) {
			HandlerList.unregisterAll(moduleDioriteDamage);
			if(moduleDioriteDamage != null) moduleDioriteDamage.cancelDamageTask();
		}
		
		if (!(boolean) PresetHandler.getPrefabOption("moduleAntiCheat"))
			moduleAntiCheat.disableModule();

		if (!(boolean) PresetHandler.getPrefabOption("axeOfDestructionLevelling"))
			HandlerList.unregisterAll(moduleAxeOfDestruction);

		if (!(boolean) PresetHandler.getPrefabOption("swordOfDivinityLevelling"))
			HandlerList.unregisterAll(moduleSwordOfDivinity);

		if (!(boolean) PresetHandler.getPrefabOption("moduleOneHeartStart"))
			HandlerList.unregisterAll(moduleOneHeartStart);

		if(!(boolean) PresetHandler.getPrefabOption("moduleSlimeBoost")) {
			HandlerList.unregisterAll(moduleSlimeBoost);
			if(moduleSlimeBoost != null) moduleSlimeBoost.cancelBoostTask();
		}
		
		if(!(boolean) PresetHandler.getPrefabOption("moduleStatistics")) {
			
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
