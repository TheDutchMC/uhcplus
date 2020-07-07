package nl.thedutchmc.uhcplus.events;

import java.util.UUID;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SwordOfDivinityLevelUpEvent extends Event {

	private static HandlerList handlers = new HandlerList();
	private static int swordLevel;
	private static UUID swordUuid;
	
	public SwordOfDivinityLevelUpEvent(int swordLevel, UUID swordUuid) {
		SwordOfDivinityLevelUpEvent.swordLevel = swordLevel;
		SwordOfDivinityLevelUpEvent.swordUuid = swordUuid;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	public int getSwordLevel() {
		return swordLevel;
	}
	
	public UUID getSwordUuid() {
		return swordUuid;
	}
	
	
}
