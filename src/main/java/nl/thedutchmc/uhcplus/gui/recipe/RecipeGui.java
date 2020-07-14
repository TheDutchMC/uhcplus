package nl.thedutchmc.uhcplus.gui.recipe;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

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
			
		if(PresetHandler.moduleLightAnvil) {
			gui.setItem(totalRecipes, CreateItem.create(Material.ANVIL, "Light Anvil", "Click me to see recipe"));
			totalRecipes--;
		}
		
		if(PresetHandler.moduleLightGoldenApple) {
			gui.setItem(totalRecipes, CreateItem.create(Material.GOLDEN_APPLE, "Light Golden Apple", "Click me to see recipe"));
			totalRecipes--;
		}
		
		if(PresetHandler.moduleAxeOfDestruction) {
			gui.setItem(totalRecipes, CreateItem.create(Material.IRON_AXE, "Axe of Destruction", "Click me to see recipe"));
			totalRecipes--;
		}
		
		if(PresetHandler.moduleSwordOfDivinity) {
			gui.setItem(totalRecipes, CreateItem.create(Material.IRON_SWORD, "Sword of Divinity", "Click me to see recipe"));
			totalRecipes--;
		}
		
		gui.setItem(26, CreateItem.create(Material.BARRIER, "Close menu"));
	}
	
	public static void openGui(HumanEntity ent) {
		ent.openInventory(gui);
	}
	
	public static Inventory getGui() {
		return gui;
	}
}
