package nl.thedutchmc.uhcplus.world;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;

import nl.thedutchmc.uhcplus.ConfigurationHandler;
import nl.thedutchmc.uhcplus.UhcPlus;

public class WorldHandler {

	public void setupWorld() {

		// First we want to remove the old world
		removeOldWorld();

		makeNewWorld();

		// Place the lobby
		LobbyHandler lobbyHandler = new LobbyHandler();
		lobbyHandler.loadLobby();

		if (ConfigurationHandler.pregenWorld) {
			ChunkGenerator chunkGenerator = new ChunkGenerator();
			chunkGenerator.generateChunks();
		} else {
			System.out.println("[UhcPlus] Done. Players may now join.");
			UhcPlus.PLAYER_CAN_JOIN = true;
		}
	}

	void removeOldWorld() {

		System.out.println("[UhcPlus] Removing old world...");

		File worldFile = new File(Bukkit.getServer().getWorldContainer() + File.separator + "uhcworld");

		if (worldFile.exists()) {

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

		if (Boolean.valueOf(ConfigurationHandler.onlyAllowedSeeds)) {

			List<String> allowedSeeds = ConfigurationHandler.allowedSeeds;

			// Pick a random seed from the list of allowed
			Random random = new Random();
			int i = random.nextInt((allowedSeeds.size()));

			Long seed = Long.valueOf(allowedSeeds.get(i));

			// Now generate world with that seed
			UhcPlus.INSTANCE.getServer()
					.createWorld(new WorldCreator("uhcworld").environment(Environment.NORMAL).seed(seed));
		} else {

			// Allow any random seed
			UhcPlus.INSTANCE.getServer().createWorld(new WorldCreator("uhcworld").environment(Environment.NORMAL));

		}

	}

	boolean deleteWorld(File path) {
		if (path.exists()) {
			File files[] = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteWorld(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}
}
