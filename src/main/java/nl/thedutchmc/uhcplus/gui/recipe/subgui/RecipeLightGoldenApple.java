package nl.thedutchmc.uhcplus.gui.recipe.subgui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.gui.CreateItem;

public class RecipeLightGoldenApple {
	
	private static Inventory gui;
	
	public static void setupGui() {
		gui = Bukkit.createInventory(null, 27, "Light Golden Apple");
		
		setupItems();
	}
	
	static void setupItems() {
		ItemStack goldIngot = CreateItem.create(Material.GOLD_INGOT, ChatColor.RESET + "Gold Ingot");
		gui.setItem(1, goldIngot);
		
		gui.setItem(9, goldIngot);
		gui.setItem(10, CreateItem.create(Material.APPLE, ChatColor.RESET + "Apple"));
		gui.setItem(11, goldIngot);
		
		gui.setItem(19, goldIngot);
		
		gui.setItem(13, CreateItem.create(Material.GOLDEN_APPLE, ChatColor.RESET + "" + ChatColor.AQUA + "Light Golden Apple"));
		
		gui.setItem(26, CreateItem.create(Material.BARRIER, ChatColor.RESET + "Main menu"));
	}
	
	public static Inventory getGui() {
		return gui;
	}
	
	public static void openGui(HumanEntity ent) {
		ent.openInventory(gui);
	}

}
