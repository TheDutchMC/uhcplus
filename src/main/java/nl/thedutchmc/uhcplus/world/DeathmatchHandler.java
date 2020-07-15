package nl.thedutchmc.uhcplus.world;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;

import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.teams.Team;
import nl.thedutchmc.uhcplus.teams.TeamHandler;

public class DeathmatchHandler {
	
	World overworld = null;
	
	public void startDeathmatch() {
		
		overworld = Bukkit.getServer().getWorld("uhcworld");
		
		//Load the arena
		loadSchematic(chooseDeathmatch());
		
		//set difficulty to peaceful
		overworld.setDifficulty(Difficulty.PEACEFUL);
		
		
		//TP all players to the arena
		Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().toArray().length];
		Bukkit.getServer().getOnlinePlayers().toArray(players);
		
		Location locationSpectators = new Location(overworld, 0, 120, 0);

		//Calculate spawn positions
		int arenaRadius = 50;
		int spawnCircleRadius = arenaRadius / 5 * 4;
		
		TeamHandler teamHandler = new TeamHandler(null, false);
		
		List<Location> teleportLocation = new ArrayList<>();
		List<Team> aliveTeams = TeamHandler.getAliveTeams();
		
		int teamAliveCount = teamHandler.teamsAlive();
		int increment = 360 / teamAliveCount;
		int startAngle = 0;
		
		for(int i = 0; i < teamAliveCount; i++) {
			
			double angle = startAngle + increment * i;
			double rads = angle * Math.PI / 180;
			
			double x = 0 + spawnCircleRadius * Math.cos(rads);
			double z = 0 + spawnCircleRadius * Math.sin(rads);
			
			Location location = new Location(overworld, x, overworld.getHighestBlockYAt((int) x, (int) z) + 1, z);
			
			teleportLocation.add(location);
		}
		
		for(int i = 0; i < teamAliveCount; i++) {
			
			Team team = aliveTeams.get(i);
			
			Location location = teleportLocation.get(i);
			
			for(UUID uuid: team.getAliveTeamMembers()) {
				Player player = Bukkit.getPlayer(uuid);
				
				System.out.println("[UhcPlus][Debug][world.DeathmatchHandler: 99] Player: " + player + " Location: " + location);
				
				player.teleport(location);
			}
			
		}
		
		for(Player player : players) {
			if(player.getGameMode().equals(GameMode.SPECTATOR)) {
				player.teleport(locationSpectators);

			}
		}
	}
	
	String chooseDeathmatch() {
		
		List<String> availableSchematics = new ArrayList<>();
		
		//Iterate over all schematic files in the schematics folder
		String[] filenames;
		
		File file = new File(UhcPlus.INSTANCE.getDataFolder() + File.separator + "deathmatch");
		
		//Check if the directory exists, if not, make it.
		if(!file.exists()) {
			file.mkdirs();
		}
		
		
		//A filter, since we only want the files ending in .yml
		FilenameFilter filter = new FilenameFilter() {
			
			@Override
			public boolean accept(File file, String name) {
				return name.endsWith(".schem");
			}
		};
		
		filenames = file.list(filter);
		
		//Check if any deathmatch arena's exist.
		if(filenames.length < 1) {
			System.out.println("[UhcPlus] No deathmatch arena schematics found! Disabling UhcPlus");
			UhcPlus.INSTANCE.getServer().getPluginManager().disablePlugin(UhcPlus.INSTANCE);	
		}
		
		//Iterate over all the files in folder
		for(String filename : filenames) {
						
			//Split the name because we dont want the .schem extension
			String[] filenameParts = filename.split(".schem");
						
			//Check if it's in config, if not, add it.
			if(!availableSchematics.contains(filenameParts[0])) {
				availableSchematics.add(filenameParts[0]);
			}
		}
		
		
		//Pick a random deathmatch schematic
		Random random = new Random();
		int i = random.nextInt((availableSchematics.size() - 0) + 1);
				
		i -= 1;
		
		System.out.println("[UhcPlus][Debug][world.DeathmatchHandler: 163] i: " + i);
		
		return availableSchematics.get(i);
	}
	
	void loadSchematic(String deathmatchArena) {
		
		//load the schematic into the clipboard
		File schematic = new File(UhcPlus.INSTANCE.getDataFolder() + File.separator + "deathmatch", deathmatchArena + ".schem");
		
		Clipboard clipboard = null;
		ClipboardFormat format = ClipboardFormats.findByFile(schematic);
		
		try {
			ClipboardReader reader = format.getReader(new FileInputStream(schematic));
			clipboard = reader.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Paste the clipboard at 0,0
		com.sk89q.worldedit.world.World weWorld = BukkitAdapter.adapt(overworld);
		
		try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(weWorld, -1)) {
			Operation operation = new ClipboardHolder(clipboard)
					.createPaste(editSession)
					.to(BlockVector3.at(0, 200, 0)).build();
			Operations.complete(operation);
		} catch (WorldEditException e) {
			e.printStackTrace();
		}
		
		//Adjust the worldborder
		WorldBorder worldborder = overworld.getWorldBorder();
		worldborder.setCenter(0, 0);
		worldborder.setSize(140);
	}
}
