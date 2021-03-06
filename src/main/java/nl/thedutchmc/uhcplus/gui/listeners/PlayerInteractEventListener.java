package nl.thedutchmc.uhcplus.gui.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import nl.thedutchmc.uhcplus.gui.kit.KitsGui;
import nl.thedutchmc.uhcplus.gui.modules.ModulesGui;
import nl.thedutchmc.uhcplus.gui.recipe.RecipeGui;
import nl.thedutchmc.uhcplus.gui.team.TeamGui;
import nl.thedutchmc.uhcplus.uhc.UhcHandler;

public class PlayerInteractEventListener implements Listener {

	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event) {

		if (UhcHandler.isPlaying() || event.getItem() == null)
			return;

		Action a = event.getAction();
		if (!(a.equals(Action.RIGHT_CLICK_AIR) || a.equals(Action.RIGHT_CLICK_BLOCK)))
			return;

		final ItemStack item = event.getItem();

		if (item.getType().equals(Material.IRON_SWORD))
			TeamGui.openGui(event.getPlayer());
		if (item.getType().equals(Material.CRAFTING_TABLE))
			RecipeGui.openGui(event.getPlayer());
		if (item.getType().equals(Material.IRON_PICKAXE))
			ModulesGui.openGui(event.getPlayer());
		if(item.getType().equals(Material.PAPER)) 
			KitsGui.openGui(event.getPlayer());
	}

}
