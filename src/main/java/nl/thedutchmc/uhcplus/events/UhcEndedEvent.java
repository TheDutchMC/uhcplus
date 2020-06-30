package nl.thedutchmc.uhcplus.events;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UhcEndedEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private List<UUID> winningPlayers = new ArrayList<>();
	
	public UhcEndedEvent(List<UUID> winningPlayers) {
		this.winningPlayers = winningPlayers;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	public List<UUID> getWinningPlayers() {
		return winningPlayers;
	}

}
