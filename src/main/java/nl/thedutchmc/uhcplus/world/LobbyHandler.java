package nl.thedutchmc.uhcplus.world;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
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

public class LobbyHandler {

	World overworld = null;

	public void loadLobby() {

		overworld = Bukkit.getServer().getWorld("uhcworld");

		loadSchematic(chooseLobby());

		// Adjust the worldborder
		WorldBorder worldborder = overworld.getWorldBorder();
		worldborder.setCenter(0, 0);
		worldborder.setSize(140);

		// Set difficulty to peaceful since we dont want mobs now
		overworld.setDifficulty(Difficulty.PEACEFUL);

		// Turn PVP off
		overworld.setPVP(false);

		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			player.setGameMode(GameMode.ADVENTURE);
		}

	}

	String chooseLobby() {
		List<String> availableSchematics = new ArrayList<>();

		// Iterate over all schematic files in the schematics folder
		String[] filenames;

		File file = new File(UhcPlus.INSTANCE.getDataFolder() + File.separator + "lobby");

		// Check if the directory exists, if not, make it.
		if (!file.exists()) {
			file.mkdirs();
		}

		// A filter, since we only want the files ending in .schem
		FilenameFilter filter = new FilenameFilter() {

			@Override
			public boolean accept(File file, String name) {
				return name.endsWith(".schem");
			}
		};

		filenames = file.list(filter);

		// Check if any lobby's exist.
		if (filenames.length < 1) {
			System.out.println("[UhcPlus] No lobby arena schematics found! Disabling UhcPlus");
			UhcPlus.INSTANCE.getServer().getPluginManager().disablePlugin(UhcPlus.INSTANCE);
		}

		// Iterate over all the files in folder
		for (String filename : filenames) {

			// Split the name because we dont want the .yml extension
			String[] filenameParts = filename.split(".schem");

			// Check if it's in config, if not, add it.
			if (!availableSchematics.contains(filenameParts[0])) {
				availableSchematics.add(filenameParts[0]);
			}
		}

		// Pick a random lobby
		Random random = new Random();
		int i = random.nextInt((availableSchematics.size()));

		return availableSchematics.get(i);
	}

	void loadSchematic(String lobbySchematic) {

		// load the schematic into the clipboard
		File schematic = new File(UhcPlus.INSTANCE.getDataFolder() + File.separator + "lobby",
				lobbySchematic + ".schem");

		Clipboard clipboard = null;
		ClipboardFormat format = ClipboardFormats.findByFile(schematic);

		try {
			ClipboardReader reader = format.getReader(new FileInputStream(schematic));
			clipboard = reader.read();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Paste the clipboard at 0,200,0

		com.sk89q.worldedit.world.World weWorld = BukkitAdapter.adapt(overworld);

		try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(weWorld, -1)) {
			Operation operation = new ClipboardHolder(clipboard).createPaste(editSession).to(BlockVector3.at(0, 200, 0))
					.build();
			Operations.complete(operation);
		} catch (WorldEditException e) {
			e.printStackTrace();
		}
	}
}
