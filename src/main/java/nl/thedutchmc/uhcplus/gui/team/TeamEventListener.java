package nl.thedutchmc.uhcplus.gui.team;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import nl.thedutchmc.uhcplus.gui.team.subgui.listTeamsGui.ListTeamsGui;

public class TeamEventListener implements Listener {

	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {

		// Check if the inventory is the main Team gui
		if (!event.getInventory().equals(TeamGui.getGui()))
			return;

		// cancel the event so players cant take the items
		event.setCancelled(true);

		// get the clicked item
		final ItemStack clickedItem = event.getCurrentItem();

		// check if the clicked item is null or air, if so, dont continue
		if (clickedItem == null || clickedItem.getType().equals(Material.AIR))
			return;

		// get the player who clicked
		HumanEntity ent = event.getWhoClicked();

		// if the clicked item is a barrier, close the gui
		if (clickedItem.getType().equals(Material.BARRIER))
			ent.closeInventory();

		// if the clicked item is a book, open the Team list gui
		if (clickedItem.getType().equals(Material.BOOK))
			ListTeamsGui.openGui(ent);

	}

	// We dont want players to drag items, so we cancel the event if they try.
	@EventHandler
	public void onInventoryDragEvent(InventoryDragEvent event) {
		if (event.getInventory() == TeamGui.getGui())
			event.setCancelled(true);
	}

}
