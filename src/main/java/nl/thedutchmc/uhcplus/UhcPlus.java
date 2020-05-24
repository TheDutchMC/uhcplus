package nl.thedutchmc.uhcplus;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;
import nl.thedutchmc.uhcplus.commands.ChatCommandHandler;
import nl.thedutchmc.uhcplus.commands.UhcpCommandHandler;
import nl.thedutchmc.uhcplus.commands.UhcpTabCompleter;
import nl.thedutchmc.uhcplus.discord.DiscordConfigurationHandler;
import nl.thedutchmc.uhcplus.discord.JdaSetup;
import nl.thedutchmc.uhcplus.discord.ModuleProximityVoice;
import nl.thedutchmc.uhcplus.presets.PresetHandler;
import nl.thedutchmc.uhcplus.uhc.UhcHandler;
import nl.thedutchmc.uhcplus.uhc.listener.EntityDamageByEntityEventListener;
import nl.thedutchmc.uhcplus.uhc.listener.PlayerLoginJoinEventListener;
import nl.thedutchmc.uhcplus.world.WorldHandler;

public class UhcPlus extends JavaPlugin {

	public static String VERSION = "1.1-BETA";
		
	public static boolean PLAYER_CAN_JOIN = true;	
	
	public static boolean UHC_STARTED = false;
	
	public static Scoreboard scoreboard;
	
	public static void debugLog(String log) {
		System.out.println("[UhcPlus][Debug]" + log);
	}
	
	
	@Override
	public void onEnable() {
		System.out.println("Welcome to UHCPlus - Version " + VERSION);
		
		//Register the LoginPlayerListener
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerLoginJoinEventListener(), this);
		
		ScoreboardManager sbManager = Bukkit.getScoreboardManager();
		scoreboard = sbManager.getNewScoreboard();
		Objective healthObjective = scoreboard.registerNewObjective("health", "health", "health");
		healthObjective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
		healthObjective.setRenderType(RenderType.HEARTS);
		
		//Register the EntityDamageByEntityEventListener
		Bukkit.getServer().getPluginManager().registerEvents(new EntityDamageByEntityEventListener(), this);
		
		//Set the executor and tab completer for the /uhcp command
		getCommand("uhcp").setExecutor(new UhcpCommandHandler(this));
		getCommand("uhcp").setTabCompleter(new UhcpTabCompleter());
		
		//Set the executor for the /chat command
		getCommand("chat").setExecutor(new ChatCommandHandler());
		
		//Load the configuration file
		ConfigurationHandler configurationHandler = new ConfigurationHandler(this);
		configurationHandler.loadConfig();
		
		//Load and read the Discord configuration file
		DiscordConfigurationHandler discordConfigurationHandler = new DiscordConfigurationHandler(this);
		discordConfigurationHandler.loadDiscordConfig();
		
		//Load all the presets
		PresetHandler presetHandler = new PresetHandler(this);
		presetHandler.loadPresets();
		
		//Check if the files in the presets/ directory match what's in config.
		configurationHandler.readAvailablePresets();
		
		//Create a folder for the deathmatch schematics
		File file = new File(this.getDataFolder() + File.separator + "deathmatch");
		file.mkdir();
		
		//setup discord
		if(PresetHandler.ModuleProximityVoice) {
			ModuleProximityVoice.setupModule();
		}
		
		UhcPlus plugin = this;
		
		new BukkitRunnable() {

			@Override
			public void run() {
				WorldHandler worldHandler = new WorldHandler(plugin);
				worldHandler.setupWorld();				
			}
			
		
		
		}.runTaskLater(plugin, 120);

		
		
	}
	
	@Override
	public void onDisable() {
		
		Guild guild = JdaSetup.getJda().getGuildById(DiscordConfigurationHandler.guildId);
		
		for(UUID uuid : UhcHandler.createdChannels) {
			
			List<VoiceChannel> channels = guild.getVoiceChannelsByName(uuid.toString(), true);
			channels.get(0).delete().reason("Plugin Shutdown").queue();
		}
		
	}
}
