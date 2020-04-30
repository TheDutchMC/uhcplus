package nl.thedutchmc.uhcplus.events;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UhcStartedEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
	private List<Player> playersPlaying;
	
	public UhcStartedEvent(List<Player> playersPlaying) {
		this.playersPlaying = playersPlaying;
	}
	
	public List<Player> getPlayingPlayers() {
		return playersPlaying;
	}
	
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
