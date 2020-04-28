package nl.thedutchmc.uhcplus.deathmatch;

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

	private UhcPlus plugin;
	
	World overworld = null;
	
	public DeathmatchHandler(UhcPlus plugin) {
		this.plugin = plugin;
	}
	
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
		
		TeamHandler teamHandler = new TeamHandler(plugin, null, false);
		
		List<Location> teleportLocation = new ArrayList<>();
		List<Team> aliveTeams = teamHandler.getAliveTeams();
		
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
		
		File file = new File(plugin.getDataFolder() + File.separator + "deathmatch");
		
		//A filter, since we only want the files ending in .yml
		FilenameFilter filter = new FilenameFilter() {
			
			@Override
			public boolean accept(File file, String name) {
				return name.endsWith(".schem");
			}
		};
		
		filenames = file.list(filter);
		
		//Iterate over all the files in folder
		for(String filename : filenames) {
						
			//Split the name because we dont want the .yml extension
			String[] filenameParts = filename.split(".schem");
						
			//Check if it's in config, if not, add it.
			if(!availableSchematics.contains(filenameParts[0])) {
				availableSchematics.add(filenameParts[0]);
			}
		}
		
		
		//Pick a random deathmatch schematic (thus arena)
		Random random = new Random();
		int i = random.nextInt((availableSchematics.size() - 0) + 1) + 0;
		
		availableSchematics.size();
		
		return availableSchematics.get(i-1);
	}
	
	void loadSchematic(String deathmatchArena) {
		
		//load the schematic into the clipboard
		File schematic = new File(plugin.getDataFolder() + File.separator + "deathmatch", deathmatchArena + ".schem");
		
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
					.to(BlockVector3.at(0, 100, 0)).build();
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
