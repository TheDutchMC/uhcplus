package nl.thedutchmc.uhcplus.discord;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.md_5.bungee.api.ChatColor;

public class PlayerJoinEventListener implements Listener {

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();
		
		if(!ModuleProximityVoice.discordNicknames.containsKey(uuid)) {
			player.sendMessage(ChatColor.GOLD + "Please add your discord nickname to your player profile. You can do this by running /discord <nickname>");
		}
	}
}
