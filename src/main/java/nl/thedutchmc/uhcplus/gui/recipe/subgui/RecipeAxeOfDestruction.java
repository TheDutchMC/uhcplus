package nl.thedutchmc.uhcplus.gui.recipe.subgui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.gui.CreateItem;

public class RecipeAxeOfDestruction {

	private static Inventory gui;
	
	public static void setupGui() {
		gui = Bukkit.createInventory(null, 27, "Axe of Destruction");
		
		setupItems();
	}
	
	static void setupItems() {
		ItemStack ironIngot = CreateItem.create(Material.IRON_INGOT, ChatColor.RESET + "Iron Ingot");
		ItemStack ironBlock = CreateItem.create(Material.IRON_BLOCK, ChatColor.RESET + "Iron Block");
		
		gui.setItem(1, ironIngot);
		gui.setItem(2, ironIngot);
		
		gui.setItem(10, ironBlock);
		gui.setItem(11, ironIngot);
		
		gui.setItem(19, ironBlock);
		
		gui.setItem(13, CreateItem.create(Material.IRON_AXE, ChatColor.RESET + "" + ChatColor.RED + "Axe of Destruction", "This item levels up"));
		
		gui.setItem(26, CreateItem.create(Material.BARRIER, ChatColor.RESET + "Main menu"));
	}
	
	public static Inventory getGui() {
		return gui;
	}
	
	public static void openGui(HumanEntity ent) {
		ent.openInventory(gui);
	}
	
}
