package nl.thedutchmc.uhcplus.modules.moduleAntiCheat.listeners;

import java.util.Date;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import nl.thedutchmc.uhcplus.modules.moduleAntiCheat.ModuleAntiCheat;
import nl.thedutchmc.uhcplus.presets.PresetHandler;

public class BlockBreakEventListener implements Listener {
	
	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {
		
		//Check if the mined ore is a diamond ore
		if(event.getBlock().getType().equals(Material.DIAMOND_ORE)) {
			
			UUID uuid = event.getPlayer().getUniqueId();
			
			//Check if the list already contains the player
			if(ModuleAntiCheat.timeSinceLastDiamond.containsKey(uuid)) {
				
				//Calculate the time difference between now and the last time the player mined a diamond
				Date dateNow = new Date();
				long seconds = (dateNow.getTime() - ModuleAntiCheat.timeSinceLastDiamond.get(uuid).getTime()) / 1000;
				
				//Check if its more than 5 and less than the configured (default 60 seconds) time
				if(seconds > 10 && seconds <= PresetHandler.moduleAntiCheatTime) {
					
					//Get all players, and then check for the OP Players
					for(Player p : Bukkit.getOnlinePlayers()) {
						if(p.isOp()) {
							
							//Send the OP players a message indicating the checked player might be using xray
							p.sendMessage(ChatColor.LIGHT_PURPLE + "[UhcPlus AntiCheat] " + ChatColor.AQUA + event.getPlayer().getName() + ChatColor.GOLD + " Might be cheating, they have found diamonds within " + ChatColor.RED + PresetHandler.moduleAntiCheatTime + ChatColor.GOLD + " seconds");
						}
					}
				}
				
				//Finally, update the hashmap with a new time
				ModuleAntiCheat.timeSinceLastDiamond.replace(uuid, new Date());
			} else {
				
				//The player wasn't in the hashmap yet, so add them to it.
				ModuleAntiCheat.timeSinceLastDiamond.put(uuid, new Date());	
			}
		} 
	}
}
