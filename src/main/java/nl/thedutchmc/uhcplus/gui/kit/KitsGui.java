package nl.thedutchmc.uhcplus.gui.kit;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.modules.modules.kits.Kit;
import nl.thedutchmc.uhcplus.modules.modules.kits.KitHandler;

public class KitsGui {

	private static Inventory gui;
	
	public static void setupGui() {
		
		gui = Bukkit.createInventory(null, 27, "Kits");
		setupItems();
		
	}
	
	static void setupItems() {
		
		for(Kit k : KitHandler.kits) {
			
			gui.addItem(createItem(Material.PAPER, ChatColor.RESET + k.getKitName(), k.getKitEnabled(), k.getKitEnabled() ? "This kit is enabled" : ""));
		}	
	}
	
	public static Inventory getGui() {
		return gui;
	}
	
	public static void openGui(HumanEntity ent) {
		setupItems();
		ent.openInventory(gui);
	}
	
	static ItemStack createItem(Material m, String name, boolean enchanted, String... lore) {
	
		final ItemStack item = new ItemStack(m, 1);
		final ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		item.addUnsafeEnchantment(Enchantment.MENDING, 1);
		
		return item;
	}
	
}
