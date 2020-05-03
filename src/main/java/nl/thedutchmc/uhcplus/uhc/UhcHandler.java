package nl.thedutchmc.uhcplus.uhc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.events.UhcStartedEvent;
import nl.thedutchmc.uhcplus.presets.PresetHandler;
import nl.thedutchmc.uhcplus.teams.Team;
import nl.thedutchmc.uhcplus.teams.TeamHandler;
import nl.thedutchmc.uhcplus.uhc.listener.EntityDeathEventListener;
import nl.thedutchmc.uhcplus.uhc.listener.PlayerDeathEventListener;
import nl.thedutchmc.uhcplus.uhc.listener.UhcStartedEventListener;
import nl.thedutchmc.uhcplus.uhc.scheduler.GameEndScheduler;
import nl.thedutchmc.uhcplus.uhc.scheduler.PvpScheduler;
import nl.thedutchmc.uhcplus.uhc.scheduler.WorldborderScheduler;

public class UhcHandler {

	private UhcPlus plugin;

	public UhcHandler(UhcPlus plugin) {
		this.plugin = plugin;
	}
	
	TeamHandler teamHandler = new TeamHandler(plugin, null, false);
	
	public void startUhc(boolean resortTeams) {
	
		//If we need to resort the teams, do it.
		if(resortTeams) {
			teamHandler.playerTeamJoiner();
		}
		
		List<Team> teams = TeamHandler.teams;
		
		World overworld = Bukkit.getServer().getWorld("uhcworld");
		
		//Add event listener for UhcStartedEventListener (this listener will remove the lobby)
		plugin.getServer().getPluginManager().registerEvents(new UhcStartedEventListener(), plugin);
		
		//Set the required gamerules.
		overworld.setGameRule(GameRule.NATURAL_REGENERATION, false);
		overworld.setGameRule(GameRule.DO_FIRE_TICK, false);
		overworld.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
		
		//Set full health
		Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().toArray().length];
		Bukkit.getServer().getOnlinePlayers().toArray(players);
		
		for(Player player : players) {
			player.setHealth(20);
		}
		
		//Register the PlayerDeathEvent listener
		plugin.getServer().getPluginManager().registerEvents(new PlayerDeathEventListener(plugin), plugin);
		
		//Disable PVP
		overworld.setPVP(false);
		
		//Register the EntityDeathEvent listener
		plugin.getServer().getPluginManager().registerEvents(new EntityDeathEventListener(), plugin);
		
		//Difficuly to hard
		overworld.setDifficulty(Difficulty.HARD);
		
		//Clear player inventory and 
		for(Player player : Bukkit.getServer().getOnlinePlayers()) {
			
			player.setGameMode(GameMode.SURVIVAL);
			player.getInventory().clear();
		}
		
		//Spawn protection to 0
		Bukkit.getServer().setSpawnRadius(0);
		
		//Schedule the pvp timer
		PvpScheduler pvpScheduler = new PvpScheduler(overworld, plugin);
		pvpScheduler.schedulePvp();
		
		//Schedule the worldborder
		WorldborderScheduler worldborderScheduler = new WorldborderScheduler(plugin);
		worldborderScheduler.scheduleWorldborder();
		
		//Set the information scoreboard for all the players
		ScoreboardHandler scoreboardHandler = new ScoreboardHandler();
		
		Scoreboard informationScoreboard = scoreboardHandler.getInformationScoreboard();
		
		for(Player player : Bukkit.getServer().getOnlinePlayers()) {
			player.setScoreboard(informationScoreboard);
		}
		
		//Schedule the information scoreboard to update every 5 seconds (100 ticks)
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				scoreboardHandler.updateInformationScoreboard(informationScoreboard.getObjective("infObj"));
			}
		}.runTaskTimer(plugin, 0, 100);
		
		
		//Schedule game end
		GameEndScheduler gameEndScheduler = new GameEndScheduler(plugin);
		gameEndScheduler.scheduleGameEnd();
		
		//Get a list of all Players playing
		List<Player> playersPlaying = new ArrayList<>();
		
		for(Team team : teams) {
			for(UUID uuid : team.getTeamMembers()) {
				playersPlaying.add(Bukkit.getServer().getPlayer(uuid));
			}
		}
		
		//Spread the teams
		
		//Calculate distance that the "spawncircle" should be from 0,0.
		int worldborderSize = PresetHandler.worldBorderSize;
		int spawnCircleRadius = (worldborderSize / 2) / 4 * 3;
		
		List<Location> teleportLocations = new ArrayList<>();
		
		//Calculate the actual locations
		int teamCount = teamHandler.teamsWithPlayers();
		int increment = 360 / teamCount;
		int startAngle = 0;
		
		for(int i = 0; i < teamCount; i++) {
			
			double angle = startAngle + increment * i;
			double rads = angle * Math.PI  / 180;
			
			double x = 0 + spawnCircleRadius * Math.cos(rads);
			double z = 0 + spawnCircleRadius * Math.sin(rads);
			
			Location location = new Location(overworld, x, overworld.getHighestBlockYAt((int) x, (int) z) + 1, z);
			
			teleportLocations.add(location);
			
		}
		
		//TP the players of each team to the previously calculated locations
		for(int i = 0; i < teamCount; i++) {
			Team team = teams.get(i);
			
			Location location = teleportLocations.get(i);
			
			for(UUID uuid : team.getTeamMembers()) {
				Player player = Bukkit.getServer().getPlayer(uuid);
				player.teleport(location);
			}	
		}
		
		//lastly, Call the UhcStartedEvent
		Bukkit.getServer().getPluginManager().callEvent(new UhcStartedEvent(playersPlaying));
	}
}
