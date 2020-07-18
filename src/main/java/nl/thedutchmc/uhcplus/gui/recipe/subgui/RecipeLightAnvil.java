package nl.thedutchmc.uhcplus.gui.recipe.subgui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.gui.CreateItem;

public class RecipeLightAnvil {

	private static Inventory gui;

	public static void setupGui() {
		gui = Bukkit.createInventory(null, 27, "Light Anvil");

		setupItems();
	}

	static void setupItems() {

		ItemStack ironIngot = CreateItem.create(Material.IRON_INGOT, ChatColor.RESET + "Iron Ingot");

		gui.setItem(0, ironIngot);
		gui.setItem(1, ironIngot);
		gui.setItem(2, ironIngot);

		gui.setItem(10, CreateItem.create(Material.IRON_BLOCK, ChatColor.RESET + "Iron Block"));

		gui.setItem(18, ironIngot);
		gui.setItem(19, ironIngot);
		gui.setItem(20, ironIngot);

		gui.setItem(13, CreateItem.create(Material.ANVIL, ChatColor.RESET + "" + ChatColor.AQUA + "Light Anvil"));

		gui.setItem(26, CreateItem.create(Material.BARRIER, ChatColor.RESET + "Main menu"));
	}

	public static void openGui(HumanEntity ent) {
		setupItems();
		ent.openInventory(gui);
	}

	public static Inventory getGui() {
		return gui;
	}

}
