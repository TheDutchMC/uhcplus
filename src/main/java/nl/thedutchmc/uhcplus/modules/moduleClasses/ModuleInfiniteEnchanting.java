package nl.thedutchmc.uhcplus.modules.moduleClasses;

import org.bukkit.Bukkit;
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
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {

			player.getInventory().setItem(0, new ItemStack(Material.ENCHANTING_TABLE, 64));
			player.getInventory().setItem(1, new ItemStack(Material.ANVIL, 64));
			player.setLevel(Integer.MAX_VALUE);
		}
	}

	@EventHandler
	public void onInventoryOpenEvent(InventoryOpenEvent event) {

		if (event.getInventory().getType().equals(InventoryType.ENCHANTING)) {

			event.getInventory().setItem(1, new ItemStack(Material.LAPIS_LAZULI, 64));

		}
	}

	@EventHandler
	public void onInventoryCloseEvent(InventoryCloseEvent event) {

		if (event.getInventory().getType().equals(InventoryType.ENCHANTING)) {

			event.getInventory().setItem(1, new ItemStack(Material.AIR));
		}

	}
}
