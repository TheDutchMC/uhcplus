package nl.thedutchmc.uhcplus.gui.listeners;

import java.util.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.gui.GuiHandler;

public class PlayerJoinEventListener implements Listener {

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		// We only want to give players these items if they're still in lobby, ie the
		// uhc hasnt started yet
		if (!UhcPlus.UHC_STARTED) {

			final Inventory inv = event.getPlayer().getInventory();

			// Give them all the items they need to open the main GUIs
			for (Map.Entry<ItemStack, Boolean> entry : GuiHandler.getItemsForPlayers().entrySet()) {

				if (entry.getValue()) {
					if (event.getPlayer().isOp())
						inv.addItem(entry.getKey());
				} else {
					inv.addItem(entry.getKey());
				}
			}
		}
	}
}
