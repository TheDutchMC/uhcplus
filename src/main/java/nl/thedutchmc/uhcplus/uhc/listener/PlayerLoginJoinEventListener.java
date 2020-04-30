package nl.thedutchmc.uhcplus.uhc.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import nl.thedutchmc.uhcplus.UhcPlus;

public class PlayerLoginJoinEventListener implements Listener {

	@EventHandler
	public void onPlayerLoginEvent(PlayerLoginEvent event) {

		if(!UhcPlus.PLAYER_CAN_JOIN) { 
			event.disallow(Result.KICK_OTHER, ChatColor.RED + "You shall not pass!\n\n" + ChatColor.GOLD + "(you are not allowed to join just yet)");
		}
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		
		if(UhcPlus.PLAYER_CAN_JOIN) {
			
			Player player = event.getPlayer();
			
			World uhcWorld = Bukkit.getServer().getWorld("uhcworld");
						
			player.setGameMode(GameMode.ADVENTURE);
			player.teleport(new Location(uhcWorld, 0, 201, 0));
		}
	}
}
