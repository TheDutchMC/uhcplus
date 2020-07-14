package nl.thedutchmc.uhcplus.gui.recipe;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class RecipeEventListener implements Listener {
	
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {
		
		if(!event.getInventory().equals(RecipeGui.getGui()));
		
		event.setCancelled(true);
		
		final ItemStack clickedItem = event.getCurrentItem();
	}
	
}
