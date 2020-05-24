package nl.thedutchmc.uhcplus.discord;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitRunnable;

import net.dv8tion.jda.api.entities.*;
import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.uhc.listener.PlayerLoginJoinEventListener;

public class ModuleProximityVoice {

	private UhcPlus plugin;
	
	public static HashMap<UUID, String> discordNicknames;
	
	private static PlayerLoginJoinEventListener playerLoginJoinEventListener;
	
	static int proxmityDistance = 20;
	public static boolean isEnabled = false;
	
	public ModuleProximityVoice(UhcPlus plugin) {
		this.plugin = plugin;
	}
	
	public void enableModule() {
		isEnabled = true;
		
		discordNicknames = new HashMap<>();
		
		JdaSetup jdaSetup = new JdaSetup();
		
		if(!JdaSetup.jdaConnected) {
			jdaSetup.setupJda();	
		}
				
		plugin.getCommand("discord").setExecutor(new ProximityVoiceCommandHandler());
		
		playerLoginJoinEventListener = new PlayerLoginJoinEventListener();
		plugin.getServer().getPluginManager().registerEvents(playerLoginJoinEventListener, plugin);
	}
	
	public void disableModule() {
	
		//Unregister the handler for the /discord command
		try {
			final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			
			bukkitCommandMap.setAccessible(true);
			CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
			plugin.getCommand("discord").unregister(commandMap);

		} catch (Exception e) {
			e.printStackTrace();
		}

		//Unregister the login listener
		HandlerList.unregisterAll(playerLoginJoinEventListener);
	}
	
	public void startLocationCalculations() {
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
							
				HashMap <UUID, Location> playerLocations = new HashMap<>();				
				for(Player player : Bukkit.getOnlinePlayers()) {
					
					playerLocations.put(player.getUniqueId(), player.getLocation());
				}
				
				HashMap<UUID, UUID> playersCompared = new HashMap<>();
				
				for(Map.Entry<UUID, Location> entryA : playerLocations.entrySet()) {
					UUID uuidA = entryA.getKey();
					Location locationA = entryA.getValue();
					
					List<UUID> playersInProximity = new ArrayList<>();
					
					for(Map.Entry<UUID, Location> entryB : playerLocations.entrySet()) {
						UUID uuidB = entryB.getKey();
						Location locationB = entryB.getValue();
						
						if(!uuidA.equals(uuidB)) {								
							//Calculate the distance
							double distance = Math.sqrt(Math.pow(locationB.getX() - locationA.getX(), 2) + Math.pow(locationB.getY() - locationA.getY(), 2) + Math.pow(locationB.getZ() - locationA.getZ(), 2));
							System.out.println("Distance : " + distance);
							
							playersCompared.put(uuidA, uuidB);
							
							if(distance <= proxmityDistance) playersInProximity.add(uuidB);
						}
					}
					
					System.out.println("Players in prox: " + playersInProximity.size());
					
					for(UUID uuid : playersInProximity) {
						
						UhcPlus.debugLog("Players near " + Bukkit.getServer().getPlayer(uuidA).getName() + ": " + Bukkit.getServer().getPlayer(uuid).getName());
						
						Guild guild = JdaSetup.getJda().getGuildById(DiscordConfigurationHandler.guildId);
						
						List<VoiceChannel> channels = guild.getVoiceChannelsByName(uuidA.toString(), false);
						VoiceChannel channel = channels.get(0);
							
						guild.moveVoiceMember(guild.getMember(JdaSetup.getJda().getUserById(discordNicknames.get(uuid))), channel).queue();
					}
					
					playersInProximity = null;
				}
				
			}
		}.runTaskTimer(plugin, 0, 60);
	}
	
	public List<UUID> usersNotInVoice() {
		
		List<UUID> playersNotInVoice = new ArrayList<>();
		
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			
			//Get the user
			UUID uuid = p.getUniqueId();
			
			User user = JdaSetup.getJda().getUserById(discordNicknames.get(uuid));
			
			//Get the guild and voice channel
			Guild guild = JdaSetup.getJda().getGuildById(DiscordConfigurationHandler.guildId);
			
			VoiceChannel lobbyVoiceChannel = guild.getVoiceChannelById(DiscordConfigurationHandler.voiceChannelIds.get(0));
			
			//Check if the user is in the voice channel, if not add them to the list of players who aren't in a voice channel
			if(!lobbyVoiceChannel.getMembers().contains(guild.getMember(user))) playersNotInVoice.add(uuid);
		}
		
		return playersNotInVoice;
	}
	
	public static void setupModule() {
		Guild guild = JdaSetup.getJda().getGuildById(DiscordConfigurationHandler.guildId);
		
		boolean categoryExists = false;
		for(Category cat : guild.getCategories()) {
			if(cat.getName().equals("ProximityVoice")) categoryExists = true;
		}
		
		if(!categoryExists) {
			guild.createCategory("ProximityVoice");
		}
	}
	
	public static void joinUsersToChannels() {
		
		Guild guild = JdaSetup.getJda().getGuildById(DiscordConfigurationHandler.guildId);
		
		for(Map.Entry<UUID, String> entry : discordNicknames.entrySet()) {
			
			System.out.println(entry.getKey());
			
			List<VoiceChannel> channels = guild.getVoiceChannelsByName(entry.getKey().toString(), true);
			User user = JdaSetup.getJda().getUserById(entry.getValue());
			Member member = guild.getMember(user);

			System.out.println(channels.size());
			
			guild.moveVoiceMember(member, channels.get(0)).queue();
			
		}
		
	}
}
