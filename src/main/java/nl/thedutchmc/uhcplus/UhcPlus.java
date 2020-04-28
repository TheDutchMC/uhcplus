package nl.thedutchmc.uhcplus;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import nl.thedutchmc.uhcplus.commands.CommandHandler;
import nl.thedutchmc.uhcplus.commands.UhcpTabCompleter;
import nl.thedutchmc.uhcplus.presets.PresetHandler;
import nl.thedutchmc.uhcplus.world.ChunkGenerator;

public class UhcPlus extends JavaPlugin {

	public static String VERSION = "0.1-BETA";
		
	@Override
	public void onEnable() {
		System.out.println("Welcome to UHCPlus - Version " + VERSION);
	
		//Set the executor for the /uhcp command
		getCommand("uhcp").setExecutor(new CommandHandler(this));
		getCommand("uhcp").setTabCompleter(new UhcpTabCompleter());
		
		//Load the configuration file
		ConfigurationHandler configurationHandler = new ConfigurationHandler(this);
		configurationHandler.loadConfig();
		
		//Load all the presets
		PresetHandler presetHandler = new PresetHandler(this);
		presetHandler.loadPresets();
		
		//Check if the files in the presets/ directory match what's in config.
		configurationHandler.readAvailablePresets();
		
		//Create a folder for the deathmatch schematics
		File file = new File(this.getDataFolder() + File.separator + "deathmatch");
		file.mkdir();
		
		ChunkGenerator chunkGenerator = new ChunkGenerator(this);
		chunkGenerator.generateChunks();
	}
	
	@Override
	public void onDisable() {
		
	}
}
