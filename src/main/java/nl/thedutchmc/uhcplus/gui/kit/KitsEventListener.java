package nl.thedutchmc.uhcplus.gui.kit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import nl.thedutchmc.uhcplus.gui.modules.ModulesGui;
import nl.thedutchmc.uhcplus.modules.modules.kits.Kit;
import nl.thedutchmc.uhcplus.modules.modules.kits.KitHandler;

public class KitsEventListener implements Listener {
	
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {
		if(!event.getInventory().equals(KitsGui.getGui())) return;
		
		event.setCancelled(true);
		
		final ItemStack clickedItem = event.getCurrentItem();
		
		if (clickedItem == null || clickedItem.getType().equals(Material.AIR)) return;
		
		String itemName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
		
		for(int i = 0; i < KitHandler.kits.size(); i++) {
			Kit k = KitHandler.kits.get(i);
			
			System.out.println(itemName);
			
			if(k.getKitName().equals(itemName)) {
				
				KitHandler.setKitEnabled(itemName, !clickedItem.getItemMeta().hasEnchants());
				event.getWhoClicked().sendMessage(ChatColor.GOLD + "The kit " + ChatColor.RED + itemName + ChatColor.GOLD + " has been " + ChatColor.RED + (!clickedItem.getItemMeta().hasEnchants() ? "enabled" : "disabled"));
			}
		}
		
		KitsGui.openGui(event.getWhoClicked());
	}
	
	@EventHandler
	public void onInventoryDragEvent(InventoryDragEvent event) {
		if (event.getInventory().equals(ModulesGui.getGui()))
			event.setCancelled(true);
	}
}