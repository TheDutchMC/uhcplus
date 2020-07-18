package nl.thedutchmc.uhcplus.gui;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.gui.listeners.PlayerInteractEventListener;
import nl.thedutchmc.uhcplus.gui.listeners.PlayerJoinEventListener;
import nl.thedutchmc.uhcplus.gui.modules.ModulesEventListener;
import nl.thedutchmc.uhcplus.gui.modules.ModulesGui;
import nl.thedutchmc.uhcplus.gui.recipe.RecipeEventListener;
import nl.thedutchmc.uhcplus.gui.recipe.RecipeGui;
import nl.thedutchmc.uhcplus.gui.recipe.subgui.RecipeAxeOfDestruction;
import nl.thedutchmc.uhcplus.gui.recipe.subgui.RecipeLightAnvil;
import nl.thedutchmc.uhcplus.gui.recipe.subgui.RecipeLightGoldenApple;
import nl.thedutchmc.uhcplus.gui.recipe.subgui.RecipeSwordOfDivinity;
import nl.thedutchmc.uhcplus.gui.recipe.subgui.SubguiEventListener;
import nl.thedutchmc.uhcplus.gui.team.TeamEventListener;
import nl.thedutchmc.uhcplus.gui.team.TeamGui;
import nl.thedutchmc.uhcplus.gui.team.subgui.listTeamsGui.ListTeamsEventListener;
import nl.thedutchmc.uhcplus.gui.team.subgui.listTeamsGui.ListTeamsGui;

public class GuiHandler {

	private static UhcPlus plugin;
	private static HashMap<ItemStack, Boolean> itemsForPlayers = new HashMap<>();
	private static PlayerInteractEventListener playerInteractEventListener;
	private static PlayerJoinEventListener playerJoinEventListener;
	private static TeamEventListener teamEventListener;
	private static ListTeamsEventListener listTeamsEventListener;
	private static RecipeEventListener recipeEventListener;
	private static SubguiEventListener subguiEventListener;
	private static ModulesEventListener modulesEventListener;

	public static void setupGuiSystem() {

		plugin = UhcPlus.INSTANCE;

		// Create listeners
		playerInteractEventListener = new PlayerInteractEventListener();
		playerJoinEventListener = new PlayerJoinEventListener();
		teamEventListener = new TeamEventListener();
		listTeamsEventListener = new ListTeamsEventListener();
		recipeEventListener = new RecipeEventListener();
		subguiEventListener = new SubguiEventListener();
		modulesEventListener = new ModulesEventListener();

		// Setup listeners required for the system
		Bukkit.getPluginManager().registerEvents(playerInteractEventListener, plugin);
		Bukkit.getPluginManager().registerEvents(playerJoinEventListener, plugin);

		// START OF TEAM GUI

		TeamGui.initGui();
		itemsForPlayers.put(CreateItem.create(Material.IRON_SWORD, ChatColor.GOLD + "Teams", "Right click me"), false);
		Bukkit.getPluginManager().registerEvents(teamEventListener, plugin);

		// Setup the ListTeams GUI
		ListTeamsGui.initGui();
		Bukkit.getPluginManager().registerEvents(listTeamsEventListener, plugin);

		// END OF TEAM GUI

		// START OF RECIPE GUI
		RecipeGui.setupGui();
		itemsForPlayers.put(CreateItem.create(Material.CRAFTING_TABLE, ChatColor.GOLD + "Recipes", "Right click me"),
				false);

		Bukkit.getPluginManager().registerEvents(recipeEventListener, plugin);

		// EventListener for all subguis
		Bukkit.getPluginManager().registerEvents(subguiEventListener, plugin);

		// Subguis
		RecipeLightAnvil.setupGui();
		RecipeLightGoldenApple.setupGui();
		RecipeAxeOfDestruction.setupGui();
		RecipeSwordOfDivinity.setupGui();

		// END OF RECIPE GUI

		// START OF MODULES GUI
		ModulesGui.setupGui();
		itemsForPlayers.put(CreateItem.create(Material.IRON_PICKAXE, ChatColor.GOLD + "Modules", "Right click me"),
				true);
		Bukkit.getPluginManager().registerEvents(modulesEventListener, plugin);

		// END OF MODULES GUI

	}

	public static void unregisterGuiSystem() {
		HandlerList.unregisterAll(playerInteractEventListener);
		HandlerList.unregisterAll(playerJoinEventListener);
		HandlerList.unregisterAll(teamEventListener);
		HandlerList.unregisterAll(listTeamsEventListener);
		HandlerList.unregisterAll(recipeEventListener);
		HandlerList.unregisterAll(subguiEventListener);
	}

	public static HashMap<ItemStack, Boolean> getItemsForPlayers() {
		return itemsForPlayers;
	}
}
