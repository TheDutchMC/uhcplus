package nl.thedutchmc.uhcplus.gui.team.subgui.listTeamsGui;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.gui.team.TeamGui;
import nl.thedutchmc.uhcplus.teams.TeamHandler;

public class ListTeamsEventListener implements Listener {

	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {

		// Check if the inventory that was opened is an a team list page
		boolean matchedAnInventory = false;
		for (Map.Entry<Integer, GuiPage> entry : ListTeamsGui.guiPages.entrySet()) {
			if (entry.getValue().getGui().equals(event.getInventory()))
				matchedAnInventory = true;
		}

		// Check if its the first page of the team list
		if (event.getInventory().equals(ListTeamsGui.getGui()))
			matchedAnInventory = true;

		// if its neither of the above, or the clicked item is null, we do nothing
		if (!matchedAnInventory || event.getCurrentItem() == null)
			return;

		// Cancel the event, so the player cant take the item
		event.setCancelled(true);

		// If the clicked item is a barrier, we want to go back to the main team menu
		if (event.getCurrentItem().getType().equals(Material.BARRIER)) {
			TeamGui.openGui((event.getWhoClicked()));

			// if the clicked item is a black glass pane, we want to go to the next page
		} else if (event.getCurrentItem().getType().equals(Material.BLACK_STAINED_GLASS_PANE)) {

			// Get the id of the next page from the Inventory name
			int pageId = (event.getView().getTitle().split(" ").length > 2)
					? Integer.valueOf(event.getView().getTitle().split(" ")[2])
					: 1;

			// Open the next page
			ListTeamsGui.guiPages.get(pageId).openGui(event.getWhoClicked());

			// If the clicked item is a white glass pane, we want to go to the previous page
		} else if (event.getCurrentItem().getType().equals(Material.WHITE_STAINED_GLASS_PANE)) {

			// get the page id from the inventory name
			int pageId = Integer.valueOf(ChatColor.stripColor((event.getView().getTitle().split(" ")[2]))) - 1;

			// if the page id is 1, that means we want to open the "first" team list page
			if (pageId == 1) {
				ListTeamsGui.openGui(event.getWhoClicked());

				// if its not one, its a seperate 'page'
			} else {
				ListTeamsGui.guiPages.get(pageId - 1).openGui(event.getWhoClicked());
			}

			// if its a none of the above, its a team
		} else {

			// get the team id from the name of the item
			final int teamId = Integer
					.valueOf(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName().split(" ")[1]));

			// get the player
			final Player player = (Player) event.getWhoClicked();

			// join the player to the team
			TeamHandler teamHandler = new TeamHandler(null, false);
			teamHandler.playerJoinTeam(teamId, player.getUniqueId());

			// get the current page id from the inventory name
			int pageId = (event.getView().getTitle().split(" ").length > 2)
					? Integer.valueOf(event.getView().getTitle().split(" ")[2])
					: 1;

			// if the page id is 1, we're on the 'first' page
			if (pageId == 1) {
				ListTeamsGui.openGui(event.getWhoClicked());

				// if its not 1, we're on a later page
			} else {
				ListTeamsGui.guiPages.get(pageId - 1).openGui(event.getWhoClicked());
			}
		}
	}

	// we dont want players to drag the items, so we cancel the event if they try
	@EventHandler
	public void onInventoryDragEvent(InventoryDragEvent event) {
		if (event.getInventory() == TeamGui.getGui())
			event.setCancelled(true);
	}
}
