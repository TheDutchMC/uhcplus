package nl.thedutchmc.uhcplus.uhc;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.World.Environment;

import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.teams.Team;
import nl.thedutchmc.uhcplus.teams.TeamHandler;
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
		
		//Disable PVP
		overworld.setPVP(false);
		
		PvpScheduler pvpScheduler = new PvpScheduler(overworld, plugin);
		pvpScheduler.schedulePvp();
		
		WorldborderScheduler worldborderScheduler = new WorldborderScheduler(plugin);
		worldborderScheduler.scheduleWorldborder();
		
	}
}
