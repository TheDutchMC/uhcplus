package nl.thedutchmc.uhcplus.gui.team;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.gui.CreateItem;

public class TeamGui {
	private static Inventory gui;
	
	public static void initGui() {

		//Create the gui
		gui = Bukkit.createInventory(null, 27, "Teams"); 
		
		//Add the items to the gui
		initItems();
	}
	
	public static Inventory getGui() {
		return gui;
	}
	
	static void initItems() {
		
		//Add item ("Button") which takes you to the list of teams gui
		gui.setItem(0, CreateItem.create(Material.BOOK, ChatColor.GOLD + "List of Teams", "See available Teams and join teams"));
		
		//Add button to close the gui completely
		gui.setItem(26, CreateItem.create(Material.BARRIER, ChatColor.RESET + "Close menu"));
	}
	
	public static void openGui(final HumanEntity ent) {
		ent.openInventory(gui);
	}
}
