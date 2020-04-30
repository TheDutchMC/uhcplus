package nl.thedutchmc.uhcplus.modules.moduleListeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import nl.thedutchmc.uhcplus.events.UhcStartedEvent;

public class ModuleInfiniteEnchanting implements Listener {

	@EventHandler
	public void onUhcStartedEvent(UhcStartedEvent event) {
		
		ItemStack anvils = new ItemStack(Material.ANVIL, 64);
		ItemStack lapisBlocks = new ItemStack(Material.LAPIS_BLOCK, 64);
		ItemStack enchantingTables = new ItemStack(Material.ENCHANTING_TABLE, 64);
		
		
		for(Player player : event.getPlayingPlayers()) {
			
			player.getInventory().addItem(anvils, lapisBlocks, enchantingTables);
			player.setLevel(Integer.MAX_VALUE);
		}
	}
}
