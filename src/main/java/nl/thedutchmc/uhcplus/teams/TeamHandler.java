package nl.thedutchmc.uhcplus.teams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.modules.modules.ModuleTeamInventory;
import nl.thedutchmc.uhcplus.players.PlayerHandler;
import nl.thedutchmc.uhcplus.players.PlayerObject;
import nl.thedutchmc.uhcplus.presets.PresetHandler;

public class TeamHandler {

	private int maxTeamCount = Integer.valueOf(PresetHandler.maxTeamCount);
	private int maxPlayerCountPerTeam = Integer.valueOf(PresetHandler.maxPlayerCountPerTeam);

	private boolean shouldReturn = false;

	public static boolean teamManuallySelect = false;

	private List<String> colorsUsed = new ArrayList<>();

	private CommandSender sender;
	public static List<Team> teams = new ArrayList<>();

	public TeamHandler(CommandSender sender, boolean shouldReturn) {
		this.sender = sender;
		this.shouldReturn = shouldReturn;
	}

	public void createTeams() {

		for (int i = 0; i < maxTeamCount; i++) {
			Team team = new Team(i);

			String c = getRandomTeamColor();
			if (colorsUsed.contains(c))
				c = getRandomTeamColor();

			team.setTeamColor(ChatColor.of("#" + c));
			colorsUsed.add(c);

			if (PresetHandler.moduleTeamInventory) {
				ModuleTeamInventory teamInventory = new ModuleTeamInventory();
				teamInventory.setupGui();
				team.setTeamInventory(teamInventory);
			}

			teams.add(team);
		}
	}

	String getRandomTeamColor() {
		String zeros = "000000";

		Random r = new Random();
		String c = Integer.toString(r.nextInt(0x1000000), 16);
		c = zeros.substring(c.length()) + c;

		return c;
	}

	public void playerJoinTeam(int teamId, UUID uuid) {

		teamManuallySelect = true;

		// Check if teams have been made yet, if not do so
		if (teams.isEmpty()) {
			createTeams();
		}

		// Loop over all teams
		for (Team team : teams) {

			// Check if the current team is the team the player wants to join
			if (team.getTeamId() == teamId) {

				// Check if the player is already in the team, if yes inform them
				if (team.getTeamMembers().contains(uuid)) {
					Bukkit.getPlayer(uuid).sendMessage(ChatColor.GOLD + "You are already in this team!");
				} else {

					// Check if the selected team isnt full yet
					if (team.getTeamSize() < maxPlayerCountPerTeam) {

						// Add the player to the team
						team.playerJoinTeam(uuid);

						// Set the player object values
						PlayerObject playerObject;
						if (!PlayerHandler.playerObjects.containsKey(uuid)) {
							playerObject = PlayerHandler.addPlayerToListAndReturn(uuid);

						} else {
							playerObject = PlayerHandler.playerObjects.get(uuid);
						}

						// If the player's in a team, their teamId will be >= 0, else -1
						if (playerObject.getTeamId() >= 0) {
							// Remove the player from their old Team
							Team oldTeam = getTeamById(playerObject.getTeamId());
							oldTeam.playerLeaveTeam(uuid);
						}

						// Set the new team ID and teamchat enabled
						playerObject.setTeamId(team.getTeamId());
						playerObject.setTeamChatEnabled(true);

						Bukkit.getPlayer(uuid)
								.sendMessage(ChatColor.GOLD + "You joined team " + ChatColor.RED + team.getTeamId());
					} else {

						Bukkit.getPlayer(uuid).sendMessage(ChatColor.GOLD + "Sorry, this team is full.");
					}
				}
			}
		}
	}

	public void sortPlayersNotInTeam() {

		List<UUID> playersInTeams = new ArrayList<>();
		List<UUID> playersToBeSorted = new ArrayList<>();

		for (Team team : teams) {

			for (UUID player : team.getTeamMembers()) {

				playersInTeams.add(player);
			}
		}

		for (Player player : Bukkit.getServer().getOnlinePlayers()) {

			if (!playersInTeams.contains(player.getUniqueId())) {

				playersToBeSorted.add(player.getUniqueId());
			}
		}

		List<UUID> playersLeftUnsorted = new ArrayList<>();

		for (UUID uuid : playersToBeSorted) {

			boolean playerInTeam = false;

			for (Team team : teams) {

				if (team.getTeamSize() != maxPlayerCountPerTeam && !playerInTeam) {
					team.playerJoinTeam(uuid);

					PlayerObject playerObject = PlayerHandler.addPlayerToListAndReturn(uuid);

					playerObject.setTeamId(team.getTeamId());
					playerObject.setTeamChatEnabled(true);

					playerInTeam = true;

					break;
				}
			}

			if (!playerInTeam) {
				playersLeftUnsorted.add(uuid);
			}
		}

		if (playersLeftUnsorted.size() != 0) {

			for (UUID uuid : playersLeftUnsorted) {
				Bukkit.getPlayer(uuid).sendMessage(ChatColor.GOLD
						+ "You were not put into a team since all teams were full. You will be a spectator");
				Bukkit.getPlayer(uuid).setGameMode(GameMode.SPECTATOR);
			}

		}
	}

	public void playerRandomTeamJoiner() {

		teams.clear();
		createTeams();

		Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
		List<UUID> playersNotInTeam = new ArrayList<>();
		Bukkit.getServer().getOnlinePlayers().toArray(players);

		if ((Bukkit.getServer().getOnlinePlayers().size() / maxTeamCount) > maxPlayerCountPerTeam && shouldReturn) {
			sender.sendMessage(ChatColor.RED
					+ "There are more players than that can be fit into all the teams! There might be spectators!");
		}

		for (int i = 0; i < players.length; i++) {

			UUID playerUuid = players[i].getUniqueId();

			boolean isPlayerInTeam = false;

			for (Team team : teams) {

				if (team.getTeamSize() != maxPlayerCountPerTeam && !team.isPlayerInTeam(playerUuid)) {
					team.playerJoinTeam(playerUuid);

					PlayerObject playerObject = PlayerHandler.addPlayerToListAndReturn(playerUuid);
					playerObject.setTeamId(team.getTeamId());
					playerObject.setTeamChatEnabled(true);

					isPlayerInTeam = true;
					Bukkit.getServer().getPlayer(playerUuid)
							.sendMessage(ChatColor.GOLD + "You are now in team " + ChatColor.RED + team.getTeamId());
					break;
				}
			}

			if (!isPlayerInTeam) {
				playersNotInTeam.add(playerUuid);
			}

		}

		String playersNotInTeamNames = "";

		for (UUID uuid : playersNotInTeam) {
			Player player = Bukkit.getServer().getPlayer(uuid);
			player.setGameMode(GameMode.SPECTATOR);
			player.sendMessage(ChatColor.GOLD + "You are not put into a team, and are now a spectator.");

			playersNotInTeamNames += new String(player.getName() + ", ");
		}

		if (playersNotInTeamNames.length() > 1 && shouldReturn) {
			sender.sendMessage(ChatColor.GOLD
					+ "The following players were not added to a team, and have been put in spectator mode: "
					+ playersNotInTeamNames);
		}
	}

	public static Team getTeamById(int teamId) {
		for (Team team : teams) {
			if (team.getTeamId() == teamId)
				return team;
		}

		return null;
	}

	boolean isTeamFull(int teamId) {

		for (Team team : teams) {

			if (team.getTeamId() == teamId) {
				return !(team.getTeamSize() <= maxPlayerCountPerTeam);
			}
		}
		return false;
	}

	public HashMap<Integer, Integer> getTeamSizes() {

		HashMap<Integer, Integer> returnTeamSizes = new HashMap<>();

		for (Team team : teams) {
			returnTeamSizes.put(team.getTeamId(), team.getTeamSize());
		}

		return returnTeamSizes;

	}

	public int teamsWithPlayers() {

		int teamsWithPlayers = 0;

		for (Team team : teams) {
			if (team.getTeamMembers().size() > 0) {
				teamsWithPlayers++;
			}
		}

		return teamsWithPlayers;
	}

	public int teamsAlive() {

		int teamsAlive = 0;

		for (Team team : teams) {
			if (team.getAliveTeamMembers().size() > 0) {
				teamsAlive++;
			}
		}

		return teamsAlive;
	}

	public static List<Team> getAliveTeams() {

		List<Team> aliveTeams = new ArrayList<>();

		for (Team team : teams) {
			if (team.getAliveTeamMembers().size() > 0) {
				aliveTeams.add(team);
			}
		}

		return aliveTeams;
	}

	public void playerDied(UUID uuid) {

		for (Team team : teams) {

			if (team.getAliveTeamMembers().contains(uuid)) {
				List<UUID> newAliveTeamMembers = new ArrayList<>();
				newAliveTeamMembers = team.getAliveTeamMembers();
				newAliveTeamMembers.remove(uuid);

				team.setAliveTeamMembers(newAliveTeamMembers);
			}
		}
	}
	
	public static void revivePlayer(UUID uuid) {
		for(Team team : teams) {
			if(team.getTeamMembers().contains(uuid)) {
				
				//Check if the player isnt actually still alive, if not, add them to the Team, if they are, return
				if(!team.getAliveTeamMembers().contains(uuid)) {
					team.getAliveTeamMembers().add(uuid);
				} else return;
				
				//Get the two players involved in teleportation
				Player teleportTo = Bukkit.getPlayer(team.getAliveTeamMembers().get(0));
				Player toRevive = Bukkit.getPlayer(uuid);
				
				//Teleport the player to revive to another team member
				toRevive.teleport(teleportTo);
				
				//Set the gamemode of the revived player to Survival.
				toRevive.setGameMode(GameMode.SURVIVAL);
				
				//If the infinite enchanting module is enabled, we want to set the XP of the revived player to Integer.MAX_VALUE
				if(PresetHandler.moduleInfiniteEnchanting) {
					toRevive.setTotalExperience(Integer.MAX_VALUE);
				}
				
				//If the module for a one heart start is enabled, set the revived player's health to one heart. If not, set it to the maximum health value (usually 10 hearts, or 20 in code)
				if(PresetHandler.moduleOneHeartStart) {
					toRevive.setHealth(2);
				} else {
					toRevive.setHealth(toRevive.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
				}				
			}
		}
	}
}
