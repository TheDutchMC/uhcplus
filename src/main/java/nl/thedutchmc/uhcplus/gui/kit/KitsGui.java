package nl.thedutchmc.uhcplus.gui.kit;

import java.util.ArrayList;
import java.util.List;

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
		
		if(KitHandler.kits == null) return;
		
		gui.clear();
		
		for(Kit k : KitHandler.kits) {
						
			List<String> itemsInKit = new ArrayList<>();
			for(ItemStack stack : k.getKitItems()) {
				if(stack == null) continue;
								
				String firstName = stack.getType().name().toLowerCase().replace("_", " ");
				String finalName = "";
				
				boolean shouldNextBeCapitalized = false;
				for(int i = 0; i < firstName.toCharArray().length; i++) {
					char c = firstName.toCharArray()[i];
					
					if(shouldNextBeCapitalized || i == 0) {
						
						String str = String.valueOf(c);
						str = str.toUpperCase();
						c = str.toCharArray()[0];
						shouldNextBeCapitalized = false;
					}
					
					if(c == ' ') {
						shouldNextBeCapitalized = true;
					}
					
								
					finalName += String.valueOf(c);
				}
				
				
				itemsInKit.add(finalName);
			}
			
			gui.addItem(createItem(Material.PAPER, ChatColor.RESET + k.getKitName(), k.getKitEnabled(), itemsInKit));
		}	
	}
	
	public static Inventory getGui() {
		return gui;
	}
	
	public static void openGui(HumanEntity ent) {
		setupItems();
		ent.openInventory(gui);
	}
	
	static ItemStack createItem(Material m, String name, boolean enchanted, List<String> lore) {
	
		final ItemStack item = new ItemStack(m, 1);
		final ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(name);
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		if(enchanted) item.addUnsafeEnchantment(Enchantment.MENDING, 1);
		
		return item;
	}
	
}
