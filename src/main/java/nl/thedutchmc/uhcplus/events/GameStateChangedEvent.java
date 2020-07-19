package nl.thedutchmc.uhcplus.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import nl.thedutchmc.uhcplus.uhc.GameState;

public class GameStateChangedEvent extends Event {

	private static HandlerList handlers = new HandlerList();
	private static GameState newState, oldState;
	
	public GameStateChangedEvent(GameState oldState, GameState newState) {
		GameStateChangedEvent.newState = newState;
		GameStateChangedEvent.oldState = oldState;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public GameState getNewGameState() {
		return newState;
	}
	
	public GameState getOldGameState() {
		return oldState;
	}
	
}
