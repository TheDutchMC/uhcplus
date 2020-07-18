package nl.thedutchmc.uhcplus.players;

import java.util.UUID;

public class PlayerObject {

	private UUID playerUuid;
	private boolean teamChatEnabled;
	private int teamId;

	public PlayerObject(UUID playerUuid) {
		this.playerUuid = playerUuid;

		teamId = -1;
	}

	public UUID getPlayerUuid() {
		return playerUuid;
	}

	public void setTeamChatEnabled(boolean teamChatEnabled) {
		this.teamChatEnabled = teamChatEnabled;
	}

	public boolean getTeamChatEnabled() {
		return teamChatEnabled;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public int getTeamId() {
		return teamId;
	}
}
