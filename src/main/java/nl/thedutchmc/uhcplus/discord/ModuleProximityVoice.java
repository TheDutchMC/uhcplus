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

import net.dv8tion.jda.api.JDA;
import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.uhc.listener.PlayerLoginJoinEventListener;

public class ModuleProximityVoice {

	private UhcPlus plugin;
	
	private JDA jda;
	public static HashMap<UUID, String> discordNicknames;
	
	private static PlayerLoginJoinEventListener playerLoginJoinEventListener;
	
	static int proxmityDistance = 20;
	
	public ModuleProximityVoice(UhcPlus plugin) {
		this.plugin = plugin;
	}
	
	public void enableModule() {
		discordNicknames = new HashMap<>();
		
		JdaSetup jdaSetup = new JdaSetup();
		
		if(!JdaSetup.jdaConnected) {
			jdaSetup.setupJda();	
		}
		
		jda = jdaSetup.getJda();
		
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
				
				for(Map.Entry<UUID, Location> entryA : playerLocations.entrySet()) {
					UUID uuidA = entryA.getKey();
					Location locationA = entryA.getValue();
					
					List<UUID> playersInProximity = new ArrayList<>();
					playersInProximity.add(uuidA);
					
					for(Map.Entry<UUID, Location> entryB : playerLocations.entrySet()) {
						UUID uuidB = entryB.getKey();
						Location locationB = entryB.getValue();
						
						//Calculate horizontal distance
						double distanceCylindrical = Math.pow((locationA.getX() - locationB.getX()), 2) + Math.pow((locationA.getZ() - locationB.getZ()), 2);
						
						//Check if the players are within horizontal distance of each other
						boolean isInHorizontalProximity = (distanceCylindrical <= proxmityDistance) ? true : false;
						
						//if the player is in horizontal proxmity we calculate the vertical proxmity, if not we do nothing
						if(isInHorizontalProximity)  {
							//Calculate the vertical distance, not sure if the calculation is correct
							double distanceY = Math.pow((locationA.getY() - locationB.getY()), 2);
							
							//Check if the players are within vertical distance of each other
							boolean isInVerticalProximity = (distanceY <= proxmityDistance) ? true : false;
							
							//Combine the two booleans, to form one final answer
							boolean isInProximity = (isInHorizontalProximity && isInVerticalProximity) ? true : false;
							
							//If isInProximity is true, add the player to the list.
							if(isInProximity) playersInProximity.add(uuidB);
						}

					}
				}
				
			}
		}.runTaskTimer(plugin, 0, 60);
	}
	
}
