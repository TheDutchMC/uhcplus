package nl.thedutchmc.uhcplus;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import nl.thedutchmc.uhcplus.commands.BroadcastCommandHandler;
import nl.thedutchmc.uhcplus.commands.ChatCommandHandler;
import nl.thedutchmc.uhcplus.commands.CoordsCommandHandler;
import nl.thedutchmc.uhcplus.commands.ReviveCommandHandler;
import nl.thedutchmc.uhcplus.commands.TeamInventoryCommandHandler;
import nl.thedutchmc.uhcplus.commands.UhcpCommandHandler;
import nl.thedutchmc.uhcplus.commands.UhcpTabCompleter;
import nl.thedutchmc.uhcplus.gui.GuiHandler;
import nl.thedutchmc.uhcplus.modules.modules.kits.KitHandler;
import nl.thedutchmc.uhcplus.presets.PresetHandler;
import nl.thedutchmc.uhcplus.teams.TeamHandler;
import nl.thedutchmc.uhcplus.uhc.GameState;
import nl.thedutchmc.uhcplus.uhc.UhcHandler;
import nl.thedutchmc.uhcplus.uhc.listener.EntityDamageByEntityEventListener;
import nl.thedutchmc.uhcplus.uhc.listener.PlayerLoginJoinEventListener;
import nl.thedutchmc.uhcplus.world.WorldHandler;

public class UhcPlus extends JavaPlugin {

	public static Scoreboard scoreboard;
	public static UhcPlus INSTANCE;

	@Override
	public void onEnable() {
		INSTANCE = this;
		
		logInfo("Welcome to UHCPlus - Version " + this.getDescription().getVersion());

		// Register the LoginPlayerListener
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerLoginJoinEventListener(), this);

		ScoreboardManager sbManager = Bukkit.getScoreboardManager();
		scoreboard = sbManager.getNewScoreboard();
		Objective healthObjective = scoreboard.registerNewObjective("health", "health", "health");
		healthObjective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
		healthObjective.setRenderType(RenderType.HEARTS);

		// Register the EntityDamageByEntityEventListener
		Bukkit.getServer().getPluginManager().registerEvents(new EntityDamageByEntityEventListener(), this);

		//Register commands
		getCommand("uhcp").setExecutor(new UhcpCommandHandler());
		getCommand("uhcp").setTabCompleter(new UhcpTabCompleter());
		getCommand("teaminventory").setExecutor(new TeamInventoryCommandHandler());
		getCommand("chat").setExecutor(new ChatCommandHandler());
		getCommand("broadcast").setExecutor(new BroadcastCommandHandler());
		getCommand("coords").setExecutor(new CoordsCommandHandler());
		getCommand("revive").setExecutor(new ReviveCommandHandler());

		// Load the configuration file
		ConfigurationHandler configurationHandler = new ConfigurationHandler();
		configurationHandler.loadConfig();

		// Load all the presets
		PresetHandler presetHandler = new PresetHandler();
		presetHandler.loadPresets();
		
		//Load the Kits from file
		KitHandler.setup();
		
		// Check if the files in the presets/ directory match what's in config.
		configurationHandler.readAvailablePresets();

		// Create a folder for the deathmatch schematics
		File file = new File(this.getDataFolder() + File.separator + "deathmatch");
		file.mkdir();

		// Create teams
		TeamHandler teamHandler = new TeamHandler(null, false);
		teamHandler.createTeams();

		// Set up the GUI system
		GuiHandler.setupGuiSystem();

		new BukkitRunnable() {

			@Override
			public void run() {

				WorldHandler worldHandler = new WorldHandler();
				worldHandler.setupWorld();
			}
		}.runTaskLater(this, 120);
		
		UhcHandler.setGameState(GameState.LOADING);;
	}

	@Override
	public void onDisable() {

	}
	
	public static void logInfo(String log) {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				Bukkit.getLogger().info("[UhcPlus] " + log);
			}
		}.runTask(INSTANCE);
	}
	
	public static void logWarn(String log) {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				Bukkit.getLogger().warning("[UhcPlus] " + log);
			}
		}.runTask(INSTANCE);
	}
}