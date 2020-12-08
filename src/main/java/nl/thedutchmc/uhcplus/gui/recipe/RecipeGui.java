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

		gui.clear();

		int totalRecipes = 0;
		if ((boolean) PresetHandler.getPrefabOption("moduleLightAnvil"))
			totalRecipes++;
		if ((boolean) PresetHandler.getPrefabOption("moduleLightGoldenApple"))
			totalRecipes++;
		if ((boolean) PresetHandler.getPrefabOption("moduleAxeOfDestruction"))
			totalRecipes++;
		if ((boolean) PresetHandler.getPrefabOption("moduleSwordOfDivinity"))
			totalRecipes++;

		totalRecipes--;

		if ((boolean) PresetHandler.getPrefabOption("moduleLightAnvil")) {
			gui.setItem(totalRecipes, CreateItem.create(Material.ANVIL,
					ChatColor.RESET + "" + ChatColor.AQUA + "Light Anvil", "Click me to see recipe"));
			totalRecipes--;
		}

		if ((boolean) PresetHandler.getPrefabOption("moduleLightGoldenApple")) {
			gui.setItem(totalRecipes, CreateItem.create(Material.GOLDEN_APPLE,
					ChatColor.RESET + "" + ChatColor.AQUA + "Light Golden Apple", "Click me to see recipe"));
			totalRecipes--;
		}

		if ((boolean) PresetHandler.getPrefabOption("moduleAxeOfDestruction")) {
			gui.setItem(totalRecipes, CreateItem.create(Material.IRON_AXE,
					ChatColor.RESET + "" + ChatColor.RED + "Axe of Destruction", "Click me to see recipe"));
			totalRecipes--;
		}

		if ((boolean) PresetHandler.getPrefabOption("moduleSwordOfDivinity")) {
			gui.setItem(totalRecipes, CreateItem.create(Material.IRON_SWORD,
					ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + "Sword of Divinity", "Click me to see recipe"));
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
