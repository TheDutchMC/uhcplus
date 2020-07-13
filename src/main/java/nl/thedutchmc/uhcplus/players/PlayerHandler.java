package nl.thedutchmc.uhcplus.players;

import java.util.HashMap;
import java.util.UUID;

public class PlayerHandler {

	public static HashMap<UUID, PlayerObject> playerObjects = new HashMap<>();
	
	public static PlayerObject addPlayerToListAndReturn(UUID playerUuid) {
		
		PlayerObject newPlayerObject = new PlayerObject(playerUuid);
		playerObjects.put(playerUuid,newPlayerObject);
		
		return newPlayerObject;
		
	}
	
	public PlayerObject getPlayerObject(UUID playerUUID) {
		return playerObjects.get(playerUUID);
	}
}
