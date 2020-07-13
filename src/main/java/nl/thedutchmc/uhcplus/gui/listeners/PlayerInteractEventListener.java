package nl.thedutchmc.uhcplus.gui.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.gui.team.TeamGui;

public class PlayerInteractEventListener implements Listener {
	
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event) {
		
		if(UhcPlus.UHC_STARTED || event.getItem() == null) return;
		
		Action a = event.getAction();
		if(!(a.equals(Action.RIGHT_CLICK_AIR) || a.equals(Action.RIGHT_CLICK_BLOCK))) return;
		
		final ItemStack item = event.getItem();
		
		//The teams gui
		if(item.getType().equals(Material.IRON_SWORD)) TeamGui.openGui(event.getPlayer());
	}

}
