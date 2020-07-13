package nl.thedutchmc.uhcplus.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.gui.listeners.PlayerInteractEventListener;
import nl.thedutchmc.uhcplus.gui.listeners.PlayerJoinEventListener;
import nl.thedutchmc.uhcplus.gui.team.TeamEventListener;
import nl.thedutchmc.uhcplus.gui.team.TeamGui;
import nl.thedutchmc.uhcplus.gui.team.subgui.listTeamsGui.ListTeamsEventListener;
import nl.thedutchmc.uhcplus.gui.team.subgui.listTeamsGui.ListTeamsGui;

public class GuiHandler {

	private UhcPlus plugin;
	
	private static List<ItemStack> itemsForPlayers = new ArrayList<>();
	
	public GuiHandler(UhcPlus plugin) {
		this.plugin = plugin;
	}
	
	public void setupGuiSystem() {
		
		//Setup listeners required for the system
		Bukkit.getPluginManager().registerEvents(new PlayerInteractEventListener(), plugin);
		Bukkit.getPluginManager().registerEvents(new PlayerJoinEventListener(), plugin);
		
		
		//START OF TEAM GUI
		
		//Setup the main GUI
		TeamGui.initGui();
		itemsForPlayers.add(CreateItem.create(Material.IRON_SWORD, ChatColor.GOLD + "Teams", "Right click me"));
		Bukkit.getPluginManager().registerEvents(new TeamEventListener(), plugin);
		
		
		//Setup the ListTeams GUI
		ListTeamsGui.initGui();
		Bukkit.getPluginManager().registerEvents(new ListTeamsEventListener(plugin), plugin);
		
		//END OF TEAM GUI
	}
	
	public static List<ItemStack> getItemsForPlayers() {
		return itemsForPlayers;
	}
}
