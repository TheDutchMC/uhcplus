package nl.thedutchmc.uhcplus.uhc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.events.UhcStartedEvent;
import nl.thedutchmc.uhcplus.gui.GuiHandler;
import nl.thedutchmc.uhcplus.presets.PresetHandler;
import nl.thedutchmc.uhcplus.teams.Team;
import nl.thedutchmc.uhcplus.teams.TeamHandler;
import nl.thedutchmc.uhcplus.uhc.listener.ChatEventListener;
import nl.thedutchmc.uhcplus.uhc.listener.EntityDeathEventListener;
import nl.thedutchmc.uhcplus.uhc.listener.PlayerCommandPreprocessEventListener;
import nl.thedutchmc.uhcplus.uhc.listener.PlayerDeathEventListener;
import nl.thedutchmc.uhcplus.uhc.listener.PlayerRespawnEventListener;
import nl.thedutchmc.uhcplus.uhc.listener.UhcEndedEventListener;
import nl.thedutchmc.uhcplus.uhc.listener.UhcStartedEventListener;
import nl.thedutchmc.uhcplus.uhc.scheduler.GameEndScheduler;
import nl.thedutchmc.uhcplus.uhc.scheduler.PvpScheduler;
import nl.thedutchmc.uhcplus.uhc.scheduler.WorldborderScheduler;

public class UhcHandler {

	TeamHandler teamHandler = new TeamHandler(null, false);

	public void startUhc(boolean resortTeams) {
		UhcPlus plugin = UhcPlus.INSTANCE;

		// Start the countdown timer for the scoreboard
		UhcTimeRemainingCalculator uhcTRC = new UhcTimeRemainingCalculator();
		uhcTRC.startCountdown();

		// Set the global flag that the uhc has started to true
		UhcPlus.UHC_STARTED = true;

		// If we need to resort the teams, do it.
		if (resortTeams) {
			teamHandler.playerRandomTeamJoiner();
		}

		if (TeamHandler.teamManuallySelect) {
			teamHandler.sortPlayersNotInTeam();
		} else {
			teamHandler.playerRandomTeamJoiner();
		}

		List<Team> teams = TeamHandler.teams;

		World uhcworld = Bukkit.getServer().getWorld("uhcworld");

		// Add event listener for UhcStartedEventListener (this listener will remove the
		// lobby)
		plugin.getServer().getPluginManager().registerEvents(new UhcStartedEventListener(), plugin);

		// Set the correct tab list color and display name color for every player
		for (Team team : teams) {
			for (UUID uuid : team.getTeamMembers()) {
				Player p = Bukkit.getPlayer(uuid);
				p.setPlayerListName(team.getTeamColor() + p.getName());
				p.setDisplayName(team.getTeamColor() + p.getName() + ChatColor.WHITE);
			}
		}

		// Set the required gamerules.
		uhcworld.setGameRule(GameRule.NATURAL_REGENERATION, false);
		uhcworld.setGameRule(GameRule.DO_FIRE_TICK, false);
		uhcworld.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
		uhcworld.setGameRule(GameRule.DO_WEATHER_CYCLE, false);

		// Set weather to clear (no rain)
		uhcworld.setStorm(false);

		// Time to day
		uhcworld.setTime(6000);

		// Set full health
		// Clear player's inventory, set their health to 20, their gamemode to survival,
		// inform them that they're in teamchat and clear their inventory.
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			player.setHealth(20);

			player.setGameMode(GameMode.SURVIVAL);
			player.sendMessage(ChatColor.GOLD + "You are currently typing in " + ChatColor.RED + "team "
					+ ChatColor.GOLD + "chat! Use " + ChatColor.RED + "/chat " + ChatColor.GOLD
					+ " to switch between global and team chat!");
			player.getInventory().clear();
		}

		// Register the UHCEndedEventListener, this fires after the UHC ends
		plugin.getServer().getPluginManager().registerEvents(new UhcEndedEventListener(), plugin);

		// Register the chat listener, for team chat
		plugin.getServer().getPluginManager().registerEvents(new ChatEventListener(), plugin);

		// Register the PlayerDeathEvent listener
		plugin.getServer().getPluginManager().registerEvents(new PlayerDeathEventListener(), plugin);

		// Disable PVP
		uhcworld.setPVP(false);

		// Register the EntityDeathEvent listener
		plugin.getServer().getPluginManager().registerEvents(new EntityDeathEventListener(), plugin);

		// Register the RespawnEvent listener
		plugin.getServer().getPluginManager().registerEvents(new PlayerRespawnEventListener(), plugin);

		// Register the CommandPreprocessEvent listener
		plugin.getServer().getPluginManager().registerEvents(new PlayerCommandPreprocessEventListener(), plugin);

		// Difficuly to hard
		uhcworld.setDifficulty(Difficulty.HARD);

		// Spawn protection to 0
		Bukkit.getServer().setSpawnRadius(0);

		// Schedule the pvp timer
		PvpScheduler pvpScheduler = new PvpScheduler(uhcworld);
		pvpScheduler.schedulePvp();

		// Schedule the worldborder
		WorldborderScheduler worldborderScheduler = new WorldborderScheduler();
		worldborderScheduler.scheduleWorldborder();

		// Set the information scoreboard for all the players
		ScoreboardHandler scoreboardHandler = new ScoreboardHandler();

		Objective infObjective = scoreboardHandler.getInformationObjective();

		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			player.setScoreboard(UhcPlus.scoreboard);
		}

		// Schedule the information scoreboard to update every seconds (20 ticks)
		new BukkitRunnable() {

			@Override
			public void run() {

				scoreboardHandler.updateInformationScoreboard(infObjective);
			}
		}.runTaskTimerAsynchronously(plugin, 0, 20);

		// Schedule game end
		GameEndScheduler gameEndScheduler = new GameEndScheduler();
		gameEndScheduler.scheduleGameEnd();

		// Get a list of all Players playing
		List<Player> playersPlaying = new ArrayList<>();

		for (Team team : teams) {
			for (UUID uuid : team.getTeamMembers()) {
				playersPlaying.add(Bukkit.getServer().getPlayer(uuid));
			}
		}

		// Spread the teams

		// Calculate distance that the "spawncircle" should be from 0,0.
		int worldborderSize = PresetHandler.worldBorderSize;
		int spawnCircleRadius = (worldborderSize / 2) / 4 * 3;

		List<Location> teleportLocations = new ArrayList<>();

		// Calculate the actual locations
		int teamCount = teamHandler.teamsWithPlayers();
		int increment = 360 / teamCount;
		int startAngle = 0;

		for (int i = 0; i < teamCount; i++) {

			double angle = startAngle + increment * i;
			double rads = angle * Math.PI / 180;

			double x = 0 + spawnCircleRadius * Math.cos(rads);
			double z = 0 + spawnCircleRadius * Math.sin(rads);

			Location location = new Location(uhcworld, (int) x,
					uhcworld.getHighestBlockAt((int) x, (int) z).getY() + 20, (int) z);
			// Location location = new Location(uhcworld, (int) x,
			// uhcworld.getHighestBlockAt((int) x, (int) z,
			// HeightMap.MOTION_BLOCKING_NO_LEAVES).getY() + 20, (int) z);

			teleportLocations.add(location);

			location.getBlock().setType(Material.AIR);
			new Location(uhcworld, location.getX(), location.getY() + 1, location.getZ()).getBlock()
					.setType(Material.AIR);

		}

		// TP the players of each team to the previously calculated locations
		for (int i = 0; i < teamCount; i++) {
			Team team = teams.get(i);

			Location location = teleportLocations.get(i);

			// Check if the block they're being teleported to is air or not, if not Y+1
			while (location.getBlock().getType() != Material.AIR) {

				location = new Location(uhcworld, location.getX(), location.getY() + 1, location.getZ());
			}

			for (UUID uuid : team.getTeamMembers()) {
				Player player = Bukkit.getServer().getPlayer(uuid);
				player.teleport(location);
				player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 400, 200));
				player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 400, 200));
			}
		}

		// Remove the gui system, since we no longer want it
		GuiHandler.unregisterGuiSystem();

		// lastly, Call the UhcStartedEvent
		Bukkit.getServer().getPluginManager().callEvent(new UhcStartedEvent());
	}
}
