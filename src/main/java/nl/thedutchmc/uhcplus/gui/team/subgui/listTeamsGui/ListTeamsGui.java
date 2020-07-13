package nl.thedutchmc.uhcplus.gui.team.subgui.listTeamsGui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.gui.CreateItem;
import nl.thedutchmc.uhcplus.teams.Team;
import nl.thedutchmc.uhcplus.teams.TeamHandler;

public class ListTeamsGui {
	private static Inventory gui;
	public static HashMap<Integer, GuiPage> guiPages = new HashMap<>();
	
	private static int guiSize;
	
	public static void initGui() {
		
		//Base size is what size the inventory should be at least
		int baseInvSize = TeamHandler.teams.size() + 5;

		//Gui size must be a multiple of 9, so calculate this
		guiSize = baseInvSize % 9 == 0 ? baseInvSize : baseInvSize + (9 - (baseInvSize % 9));
		
		//Inventory cant be larger than 54 slots (6 rows of 9), so create a next page if this is the case
		if(guiSize > 54) {
			guiSize = 54;
			guiPages.put(1, new GuiPage(1));
		}
		
		//Create the gui
		gui = Bukkit.createInventory(null, guiSize, "Teams");
		
		//add the items to the gui
		initItems();
	}
	
	public static Inventory getGui() {
		return gui;
	}
	
	static void initItems() {
				
		//loop over all the teams, and add them as items
		for(int i = 0; i < TeamHandler.teams.size() && i < 51; i++) {
			Team team = TeamHandler.teams.get(i);
			
			List<String> lore = new ArrayList<>();
			lore.add("Click to join team");
			
			//Loop over all the players in the team, and add their username as lore
			for(UUID uuid : team.getTeamMembers()) {
				lore.add(ChatColor.GRAY + "- " + Bukkit.getPlayer(uuid).getName());
			}
			
			//add the item to the gui
			gui.setItem(i, createItems(Material.PAPER, team.getTeamColor() + "Team " + i, lore));
			
		}
		
		//Add the next page button if need be
		if(guiPages.size() > 0) gui.setItem(guiSize - 2, CreateItem.create(Material.BLACK_STAINED_GLASS_PANE, "Next page")); 
		
		//Button to go back to the main menu
		gui.setItem(guiSize -1, CreateItem.create(Material.BARRIER, "Main menu"));
	}
	
	public static void openGui(final HumanEntity ent) {
		initItems();
		ent.openInventory(gui);
	}
	
	//Special method to create an item given a List of lores, instead of a String...
	public static ItemStack createItems(Material m, String name, List<String> lore) {
		final ItemStack item = new ItemStack(m, 1);
		final ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(name);
		
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		
		return item;
	}
}
