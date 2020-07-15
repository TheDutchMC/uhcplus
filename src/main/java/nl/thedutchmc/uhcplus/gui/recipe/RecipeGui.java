package nl.thedutchmc.uhcplus.gui.recipe;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.gui.CreateItem;
import nl.thedutchmc.uhcplus.presets.PresetHandler;

public class RecipeGui {

	private static Inventory gui;
	
	public static void setupGui() {
		gui = Bukkit.createInventory(null, 27, "Recipes");
		
		setupItems();
	}
	
	static void setupItems() {

		int totalRecipes = 0;
		if(PresetHandler.moduleLightAnvil) totalRecipes++;
		if(PresetHandler.moduleLightGoldenApple) totalRecipes++;
		if(PresetHandler.moduleAxeOfDestruction) totalRecipes++;
		if(PresetHandler.moduleSwordOfDivinity) totalRecipes++;
			
		totalRecipes--;
		
		if(PresetHandler.moduleLightAnvil) {
			gui.setItem(totalRecipes, CreateItem.create(Material.ANVIL, ChatColor.RESET + "" + ChatColor.AQUA + "Light Anvil", "Click me to see recipe"));
			totalRecipes--;
		}
		
		if(PresetHandler.moduleLightGoldenApple) {
			gui.setItem(totalRecipes, CreateItem.create(Material.GOLDEN_APPLE, ChatColor.RESET + "" + ChatColor.AQUA + "Light Golden Apple", "Click me to see recipe"));
			totalRecipes--;
		}
		
		if(PresetHandler.moduleAxeOfDestruction) {
			gui.setItem(totalRecipes, CreateItem.create(Material.IRON_AXE, ChatColor.RESET + "" + ChatColor.RED + "Axe of Destruction", "Click me to see recipe"));
			totalRecipes--;
		}
		
		if(PresetHandler.moduleSwordOfDivinity) {
			gui.setItem(totalRecipes, CreateItem.create(Material.IRON_SWORD, ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + "Sword of Divinity", "Click me to see recipe"));
			totalRecipes--;
		}
		
		gui.setItem(26, CreateItem.create(Material.BARRIER, ChatColor.RESET + "Close menu"));
	}
	
	public static void openGui(HumanEntity ent) {
		setupItems();
		ent.openInventory(gui);
	}
	
	public static Inventory getGui() {
		return gui;
	}
}
