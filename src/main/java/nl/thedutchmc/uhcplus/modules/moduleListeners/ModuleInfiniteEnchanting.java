package nl.thedutchmc.uhcplus.modules.moduleListeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import nl.thedutchmc.uhcplus.events.UhcStartedEvent;

public class ModuleInfiniteEnchanting implements Listener {

	@EventHandler
	public void onUhcStartedEvent(UhcStartedEvent event) {
		
		ItemStack anvils = new ItemStack(Material.ANVIL, 64);
		ItemStack enchantingTables = new ItemStack(Material.ENCHANTING_TABLE, 64);
		
		
		for(Player player : Bukkit.getServer().getOnlinePlayers()) {
			
			player.getInventory().addItem(anvils, enchantingTables);
			player.setLevel(Integer.MAX_VALUE);
		}
	}
	
	@EventHandler
	public void onInventoryOpenEvent(InventoryOpenEvent event) {
		
		if(event.getInventory().getType().equals(InventoryType.ENCHANTING)) {
			
			event.getInventory().setItem(1, new ItemStack(Material.LAPIS_LAZULI, 64));
			
		}
		
	}
	
	@EventHandler
	public void onInventoryCloseEvent(InventoryCloseEvent event) {
		
		if(event.getInventory().getType().equals(InventoryType.ENCHANTING)) {
			
			event.getInventory().setItem(1, new ItemStack(Material.AIR));
		}
		
	}
}
