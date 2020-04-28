package nl.thedutchmc.uhcplus.world;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;

import nl.thedutchmc.uhcplus.UhcPlus;

public class WorldHandler {

	private UhcPlus plugin;
	
	public WorldHandler(UhcPlus plugin) {
		this.plugin = plugin;
	}
	
	public void setupWorld() {
		
		//First we want to remove the old world
		removeOldWorld();
	}
	
	void removeOldWorld() {
		
		
		
		String worldName = null;
		for(World world : Bukkit.getServer().getWorlds()) {
			if(world.getEnvironment().equals(Environment.NORMAL)) {
				worldName = world.getName();
			}
		}
		
		File worldFolder = new File("." + File.separator + worldName);
		
		worldFolder.delete();
		
		
		
	}
	
}
