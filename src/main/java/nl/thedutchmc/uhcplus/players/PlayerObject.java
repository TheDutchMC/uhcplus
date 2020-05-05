package nl.thedutchmc.uhcplus.players;

import java.util.UUID;

import nl.thedutchmc.uhcplus.teams.Team;

public class PlayerObject {

	private UUID playerUuid;
	private boolean teamChatEnabled;
	private Team team;
	
	public PlayerObject(UUID playerUuid) {
		this.playerUuid = playerUuid;
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
	
	public void setTeam(Team team) {
		this.team = team;
	}
	
	public Team getTeam() {
		return team;
	}
}
