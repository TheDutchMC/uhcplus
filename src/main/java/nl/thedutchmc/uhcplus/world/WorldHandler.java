package nl.thedutchmc.uhcplus.world;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
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
				
		//Place the lobby
		LobbyHandler lobbyHandler = new LobbyHandler(plugin);
		lobbyHandler.loadLobby();
		
		ChunkGenerator chunkGenerator = new ChunkGenerator(plugin);
		chunkGenerator.generateChunks();
		

	}
	
	void removeOldWorld() {
		
		System.out.println("[UhcPlus] Removing old world...");
		
		File worldFile = new File(Bukkit.getServer().getWorldContainer() + File.separator + "uhcworld");
		
		if(worldFile.exists()) {
			System.out.println(worldFile.getAbsolutePath());
			
			try {
				FileUtils.deleteDirectory(worldFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("[UhcPlus] World doesn't exist. Skipping.");
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
