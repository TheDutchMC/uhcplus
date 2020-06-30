package nl.thedutchmc.uhcplus.uhc.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import nl.thedutchmc.uhcplus.events.UhcEndedEvent;

public class UhcEndedEventListener implements Listener {

	@EventHandler
	public void onUhcEndedEvent(UhcEndedEvent event) {
		
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "restart");
		
	}
}
