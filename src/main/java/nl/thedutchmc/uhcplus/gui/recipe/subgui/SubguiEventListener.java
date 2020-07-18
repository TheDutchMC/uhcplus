package nl.thedutchmc.uhcplus.gui.recipe.subgui;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import nl.thedutchmc.uhcplus.gui.recipe.RecipeGui;

public class SubguiEventListener implements Listener {

	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {

		Inventory inv = event.getInventory();

		boolean matchesInv = false;
		if (inv.equals(RecipeLightAnvil.getGui()))
			matchesInv = true;
		if (inv.equals(RecipeLightGoldenApple.getGui()))
			matchesInv = true;
		if (inv.equals(RecipeAxeOfDestruction.getGui()))
			matchesInv = true;
		if (inv.equals(RecipeSwordOfDivinity.getGui()))
			matchesInv = true;

		if (!matchesInv || event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR))
			return;

		event.setCancelled(true);

		// If the go back to main menu button is clicked
		if (event.getCurrentItem().getType().equals(Material.BARRIER))
			RecipeGui.openGui(event.getWhoClicked());
	}

}
