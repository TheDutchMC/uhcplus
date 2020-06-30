package nl.thedutchmc.uhcplus.players;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerHandler {

	public static List<PlayerObject> playerObjects = new ArrayList<>();
	
	public PlayerObject addPlayerToListAndReturn(UUID playerUuid) {
		
		PlayerObject newPlayerObject = new PlayerObject(playerUuid);
		playerObjects.add(newPlayerObject);
		
		return newPlayerObject;
		
	}
}
