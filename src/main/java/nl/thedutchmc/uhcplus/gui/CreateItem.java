package nl.thedutchmc.uhcplus.gui;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CreateItem {
	
	//Used in almost all Gui classes to easily create new items.
	public static ItemStack create(Material m, String name, String... lore) {
		final ItemStack item = new ItemStack(m, 1);
		final ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(name);
		
		meta.setLore(Arrays.asList(lore));
		
		item.setItemMeta(meta);
		
		return item;
	}
}
