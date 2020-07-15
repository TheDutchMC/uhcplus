package nl.thedutchmc.uhcplus.teams;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.modules.moduleClasses.ModuleTeamInventory;

public class Team {

	private List<UUID> teamMembers = new ArrayList<>();
	private ChatColor teamColor;
	private List<UUID> teamMembersAlive = new ArrayList<>();
	private int teamId;
	private Location startingLocation;
	private ModuleTeamInventory teamInventory;
	
	
	public Team(int teamId) {
		this.teamId = teamId;
	}
	
	public List<UUID> getTeamMembers() {
		return teamMembers;
	}
	
	public void setTeamMembers(List<UUID> teamMembers) {
		this.teamMembers = teamMembers;
	}
	
	public ChatColor getTeamColor() {
		return teamColor;
	}
	
	public void setTeamColor(ChatColor teamColor) {
		this.teamColor = teamColor;
	}
	
	public List<UUID> getAliveTeamMembers() {
		return teamMembersAlive;
	}
	
	public void setAliveTeamMembers(List<UUID> teamMembersAlive) {
		this.teamMembersAlive = teamMembersAlive;
	}
	
	public int getTeamId() {
		return teamId;
	}
	
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	
	public int getTeamSize() {
		return teamMembers.size();
	}
	
	public void playerJoinTeam(UUID playerUuid) {
		teamMembers.add(playerUuid);
		teamMembersAlive.add(playerUuid);
	}
	
	public void playerLeaveTeam(UUID playerUuid) {
		teamMembers.remove(playerUuid);
		teamMembers.remove(playerUuid);
	}
	
	public boolean isPlayerInTeam(UUID playerUuid) {
		return teamMembers.contains(playerUuid);
	}
	
	public Location getStartingLocation() {
		return startingLocation;
	}
	
	public void setStartingLocation(Location startingLocation) {
		this.startingLocation = startingLocation;
	}
	
	public ModuleTeamInventory getTeamInventory() {
		return teamInventory;
	}
	
	public void setTeamInventory(ModuleTeamInventory teamInventory) {
		this.teamInventory = teamInventory;
	}
	
	
}
