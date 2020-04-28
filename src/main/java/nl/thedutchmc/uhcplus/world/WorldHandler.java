package nl.thedutchmc.uhcplus.world;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import nl.thedutchmc.uhcplus.UhcPlus;

public class WorldHandler {

	private UhcPlus plugin;
	
	public WorldHandler(UhcPlus plugin) {
		this.plugin = plugin;
	}
	
	public void setupWorld() {
		
		//First we want to remove the old world
		removeOldWorld();
		
		makeNewWorld();
				
		ChunkGenerator chunkGenerator = new ChunkGenerator(plugin);
		chunkGenerator.generateChunks();
		
		
		
	}
	
	void removeOldWorld() {
		
		System.out.println("[UhcPlus] Removing old world...");
		
		World uhcworld = Bukkit.getServer().getWorld("uhcworld");
		
		if(uhcworld != null) {
			
			System.out.println("Deleting overworld");
			
			Bukkit.getServer().unloadWorld(uhcworld, true);
		
			File overworldFolder = uhcworld.getWorldFolder();
			
			deleteWorld(overworldFolder);
			
		} else {
			System.out.println("Cant find world!");
		}
	}
	
	void makeNewWorld() {
		plugin.getServer().createWorld(new WorldCreator("uhcworld"));
	}
	
	boolean deleteWorld(File path) {
	      if(path.exists()) {
	          File files[] = path.listFiles();
	          for(int i=0; i<files.length; i++) {
	              if(files[i].isDirectory()) {
	                  deleteWorld(files[i]);
	              } else {
	                  files[i].delete();
	              }
	          }
	      }
	      return(path.delete());
	}
}
