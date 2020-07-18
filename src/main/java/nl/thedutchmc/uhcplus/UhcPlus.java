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
import nl.thedutchmc.uhcplus.commands.TeamInventoryCommandHandler;
import nl.thedutchmc.uhcplus.commands.UhcpCommandHandler;
import nl.thedutchmc.uhcplus.commands.UhcpTabCompleter;
import nl.thedutchmc.uhcplus.gui.GuiHandler;
import nl.thedutchmc.uhcplus.presets.PresetHandler;
import nl.thedutchmc.uhcplus.teams.TeamHandler;
import nl.thedutchmc.uhcplus.uhc.listener.EntityDamageByEntityEventListener;
import nl.thedutchmc.uhcplus.uhc.listener.PlayerLoginJoinEventListener;
import nl.thedutchmc.uhcplus.world.WorldHandler;

public class UhcPlus extends JavaPlugin {

	public static String VERSION = "0.4-BETA";
	public static boolean PLAYER_CAN_JOIN = false;
	public static boolean UHC_STARTED = false;
	public static Scoreboard scoreboard;
	public static UhcPlus INSTANCE;

	public static void log(String log) {
		System.out.println("[UhcPlus]" + log);
	}

	@Override
	public void onEnable() {
		INSTANCE = this;

		System.out.println("Welcome to UHCPlus - Version " + VERSION);

		// Register the LoginPlayerListener
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerLoginJoinEventListener(), this);

		ScoreboardManager sbManager = Bukkit.getScoreboardManager();
		scoreboard = sbManager.getNewScoreboard();
		Objective healthObjective = scoreboard.registerNewObjective("health", "health", "health");
		healthObjective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
		healthObjective.setRenderType(RenderType.HEARTS);

		// Register the EntityDamageByEntityEventListener
		Bukkit.getServer().getPluginManager().registerEvents(new EntityDamageByEntityEventListener(), this);

		// Set the executor and tab completer for the /uhcp command
		getCommand("uhcp").setExecutor(new UhcpCommandHandler());
		getCommand("uhcp").setTabCompleter(new UhcpTabCompleter());

		// set the executor for /teaminventory (/ti)
		getCommand("teaminventory").setExecutor(new TeamInventoryCommandHandler());

		// Set the executor for the /chat command
		getCommand("chat").setExecutor(new ChatCommandHandler());

		// set the executor for the /broadcast command
		getCommand("broadcast").setExecutor(new BroadcastCommandHandler());

		// set the executor for the /coords command (/c)
		getCommand("coords").setExecutor(new CoordsCommandHandler());

		// Load the configuration file
		ConfigurationHandler configurationHandler = new ConfigurationHandler();
		configurationHandler.loadConfig();

		// Load all the presets
		PresetHandler presetHandler = new PresetHandler();
		presetHandler.loadPresets();

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

		UhcPlus plugin = this;

		new BukkitRunnable() {

			@Override
			public void run() {

				WorldHandler worldHandler = new WorldHandler();
				worldHandler.setupWorld();
			}
		}.runTaskLater(plugin, 120);
	}

	@Override
	public void onDisable() {

	}
}