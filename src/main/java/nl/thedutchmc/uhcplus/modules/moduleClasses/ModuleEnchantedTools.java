package nl.thedutchmc.uhcplus.modules.moduleClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ModuleEnchantedTools implements Listener {
	
	@EventHandler
	public void onCraftItemEvent(CraftItemEvent event) {
		
		ItemStack item = event.getCurrentItem();
		
		if(item.getItemMeta().getDisplayName().equals(ChatColor.RED + "Axe of Destruction")) return;
		
		
		try {	
			
			item.addEnchantment(Enchantment.DIG_SPEED, 4);
			item.addEnchantment(Enchantment.DURABILITY, 2);
			
			ItemMeta meta = item.getItemMeta();
			ArrayList<String> lore = new ArrayList<String>();
			meta.getLore();
			lore.add("An enchanted tool");
			meta.setLore(lore);
			
			String displayName = item.getType().toString().toLowerCase();
			List<String> displayNameParts = new ArrayList<>();
			String[] displayNamePartsStrArr = displayName.split("");
			displayNameParts = Arrays.asList(displayNamePartsStrArr);
			
			int iUnderscore = 10000;
			for(int i = 0; i < displayNameParts.size(); i++) {
				
				//The first character of the name, we want this to be a capital letter.
				if(i == 0) displayNameParts.set(i, displayNameParts.get(i).toUpperCase());
				
				//First letter after the space should be a capital letter
				if(i == iUnderscore + 1) displayNameParts.set(i, displayNameParts.get(i).toUpperCase());
				
				//Check if the current character is an underscore, and replace this for a space, and set iUnderscore equal to i.
				if(displayNameParts.get(i).equals("_")) {
					iUnderscore = i;
					displayNameParts.set(i, " ");
				}
			}
			
			displayName = String.join("", displayNameParts);
			
 			meta.setDisplayName(ChatColor.GOLD + displayName);
			
			item.setItemMeta(meta);
			
		} catch (IllegalArgumentException e) {
			//dont need to do anything here
		}
		
	}
	
}
