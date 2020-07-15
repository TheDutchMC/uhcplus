package nl.thedutchmc.uhcplus.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.gui.listeners.PlayerInteractEventListener;
import nl.thedutchmc.uhcplus.gui.listeners.PlayerJoinEventListener;
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
	private static List<ItemStack> itemsForPlayers = new ArrayList<>();
	private static PlayerInteractEventListener playerInteractEventListener;
	private static PlayerJoinEventListener playerJoinEventListener;
	private static TeamEventListener teamEventListener;
	private static ListTeamsEventListener listTeamsEventListener;
	private static RecipeEventListener recipeEventListener;
	private static SubguiEventListener subguiEventListener;
	
	public static void setupGuiSystem() {
		
		plugin = UhcPlus.INSTANCE;
		
		//Create listeners
		playerInteractEventListener = new PlayerInteractEventListener();
		playerJoinEventListener = new PlayerJoinEventListener();
		teamEventListener = new TeamEventListener();
		listTeamsEventListener = new ListTeamsEventListener();
		recipeEventListener = new RecipeEventListener();
		subguiEventListener = new SubguiEventListener();
		
		//Setup listeners required for the system
		Bukkit.getPluginManager().registerEvents(playerInteractEventListener, plugin);
		Bukkit.getPluginManager().registerEvents(playerJoinEventListener, plugin);
		
		//START OF TEAM GUI
		
		//Setup the main GUI
		TeamGui.initGui();
		itemsForPlayers.add(CreateItem.create(Material.IRON_SWORD, ChatColor.GOLD + "Teams", "Right click me"));
		Bukkit.getPluginManager().registerEvents(teamEventListener, plugin);
		
		//Setup the ListTeams GUI
		ListTeamsGui.initGui();
		Bukkit.getPluginManager().registerEvents(listTeamsEventListener, plugin);
		
		//END OF TEAM GUI
		
		//START OF RECIPE GUI
		RecipeGui.setupGui();
		itemsForPlayers.add(CreateItem.create(Material.CRAFTING_TABLE, ChatColor.GOLD + "Recipes", "Right click me"));
		 
		Bukkit.getPluginManager().registerEvents(recipeEventListener, plugin);
		
		//EventListener for all subguis
		Bukkit.getPluginManager().registerEvents(subguiEventListener, plugin);
		
		//Subguis
		RecipeLightAnvil.setupGui();
		RecipeLightGoldenApple.setupGui();
		RecipeAxeOfDestruction.setupGui();
		RecipeSwordOfDivinity.setupGui();
		
	}
	
	public static void unregisterGuiSystem() {
		HandlerList.unregisterAll(playerInteractEventListener);
		HandlerList.unregisterAll(playerJoinEventListener);
		HandlerList.unregisterAll(teamEventListener);
		HandlerList.unregisterAll(listTeamsEventListener);
		HandlerList.unregisterAll(recipeEventListener);
		HandlerList.unregisterAll(subguiEventListener);
	}
	
	public static List<ItemStack> getItemsForPlayers() {
		return itemsForPlayers;
	}
}
