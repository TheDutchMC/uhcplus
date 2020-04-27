package nl.thedutchmc.uhcplus.uhc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;

import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.presets.PresetHandler;
import nl.thedutchmc.uhcplus.teams.Team;
import nl.thedutchmc.uhcplus.teams.TeamHandler;
import nl.thedutchmc.uhcplus.uhc.listener.PlayerDeathEventListener;
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
		
		World overworld = null;
		
		//Get the overworld
		List<World> worlds = Bukkit.getServer().getWorlds();
		for(World world : worlds) {
			Environment e = world.getEnvironment();
			if(e.equals(Environment.NORMAL)) {
				overworld = world;
			}
		}
		
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
		PlayerDeathEventListener playerDeathEventListener = new PlayerDeathEventListener(plugin);
		plugin.getServer().getPluginManager().registerEvents(playerDeathEventListener, plugin);
		
		//Disable PVP
		overworld.setPVP(false);
		
		PvpScheduler pvpScheduler = new PvpScheduler(overworld, plugin);
		pvpScheduler.schedulePvp();
		
		WorldborderScheduler worldborderScheduler = new WorldborderScheduler(plugin);
		worldborderScheduler.scheduleWorldborder();
		
		//Schedule game end
		GameEndScheduler gameEndScheduler = new GameEndScheduler(plugin);
		gameEndScheduler.scheduleGameEnd();
		
		//Spread the teams
		
		//Calculate distance that the "spawncircle" should be from 0,0.
		int worldborderSize = PresetHandler.worldBorderSize;
		int spawnCircleRadius = (worldborderSize / 2) / 4 * 3;
		
		List<Location> teleportLocations = new ArrayList<>();
		
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
		
		for(int i = 0; i < teamCount; i++) {
			Team team = teams.get(i);
			
			Location location = teleportLocations.get(i);
			
			for(UUID uuid : team.getTeamMembers()) {
				Player player = Bukkit.getServer().getPlayer(uuid);
				player.teleport(location);
			}	
		}	
	}
}
