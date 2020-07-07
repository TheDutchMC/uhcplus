package nl.thedutchmc.uhcplus.events;

import java.util.UUID;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AxeOfDestructionLevelUpEvent extends Event {

	private static HandlerList handlers = new HandlerList();
	private static int axeLevel;
	private static UUID axeUuid;
	
	public AxeOfDestructionLevelUpEvent(int axeLevel, UUID axeUuid) {
		AxeOfDestructionLevelUpEvent.axeLevel = axeLevel;
		AxeOfDestructionLevelUpEvent.axeUuid = axeUuid;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	public int getAxeLevel() {
		return axeLevel;
	}
	
	public UUID getAxeUuid() {
		return axeUuid;
	}
	
	
}
