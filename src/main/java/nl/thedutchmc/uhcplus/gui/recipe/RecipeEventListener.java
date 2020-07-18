package nl.thedutchmc.uhcplus.gui.recipe;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import nl.thedutchmc.uhcplus.gui.recipe.subgui.RecipeAxeOfDestruction;
import nl.thedutchmc.uhcplus.gui.recipe.subgui.RecipeLightAnvil;
import nl.thedutchmc.uhcplus.gui.recipe.subgui.RecipeLightGoldenApple;
import nl.thedutchmc.uhcplus.gui.recipe.subgui.RecipeSwordOfDivinity;

public class RecipeEventListener implements Listener {

	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {

		if (!event.getInventory().equals(RecipeGui.getGui()))
			return;

		event.setCancelled(true);

		final ItemStack clickedItem = event.getCurrentItem();

		if (clickedItem == null || clickedItem.getType().equals(Material.AIR))
			return;

		HumanEntity ent = event.getWhoClicked();

		if (clickedItem.getType().equals(Material.ANVIL))
			RecipeLightAnvil.openGui(ent);
		if (clickedItem.getType().equals(Material.GOLDEN_APPLE))
			RecipeLightGoldenApple.openGui(ent);
		if (clickedItem.getType().equals(Material.IRON_AXE))
			RecipeAxeOfDestruction.openGui(ent);
		if (clickedItem.getType().equals(Material.IRON_SWORD))
			RecipeSwordOfDivinity.openGui(ent);

		if (clickedItem.getType().equals(Material.BARRIER))
			ent.closeInventory();
	}

	@EventHandler
	public void onInventoryDragEvent(InventoryDragEvent event) {
		if (event.getInventory().equals(RecipeGui.getGui()))
			event.setCancelled(true);
	}
}
