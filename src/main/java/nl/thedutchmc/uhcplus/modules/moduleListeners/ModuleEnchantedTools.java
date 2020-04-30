package nl.thedutchmc.uhcplus.modules.moduleListeners;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class ModuleEnchantedTools implements Listener {
	
	@EventHandler
	public void onCraftItemEvent(CraftItemEvent event) {
		
		ItemStack item = event.getCurrentItem();
		
		try {
			item.addEnchantment(Enchantment.DIG_SPEED, 4);
			item.addEnchantment(Enchantment.DURABILITY, 2);
		} catch (IllegalArgumentException e) {
			//dont need to do anything here
		}
		
	}
	
}
