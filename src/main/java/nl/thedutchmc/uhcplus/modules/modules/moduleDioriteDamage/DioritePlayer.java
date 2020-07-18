package nl.thedutchmc.uhcplus.modules.modules.moduleDioriteDamage;

import java.util.UUID;

public class DioritePlayer {

	private UUID uuid;
	private int secondsHeld;
	private boolean warned;
	
	public DioritePlayer(UUID uuid) {
		this.uuid = uuid;
		secondsHeld = 0;
		warned = false;
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	
	public int getSecondsHeld() {
		return secondsHeld;
	}
	
	public void setSecondsHeld(int secondsHeld) {
		this.secondsHeld = secondsHeld;
	}
	
	public boolean getWarned() {
		return warned;
	}
	
	public void setWarned(boolean warned) {
		this.warned = warned;
	}
	
}
