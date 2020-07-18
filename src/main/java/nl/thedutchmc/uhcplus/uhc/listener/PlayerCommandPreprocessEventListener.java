package nl.thedutchmc.uhcplus.uhc.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import nl.thedutchmc.uhcplus.ConfigurationHandler;

public class PlayerCommandPreprocessEventListener implements Listener {

	@EventHandler
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {

		String[] commandParts = event.getMessage().split(" ");

		// Check if spectators are not allowed to run commands
		if (!ConfigurationHandler.spectatorCanRunCommand) {

			// Check the gamemode, and if the sender is OP, because we always want to allow
			// OP players
			if (event.getPlayer().getGameMode().equals(GameMode.SPECTATOR) && !event.getPlayer().isOp()) {

				// Cancel the event and let the sender know
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.RED + "You are not allowed to execute commands!");
			}

			// Check if spectators are not allowed to /msg, /w or /tell
		} else if (!ConfigurationHandler.spectatorCanWhisperToPlayer) {

			// Check if the sender is not OP, because OP's always are allowed
			if (!event.getPlayer().isOp()) {

				// Check if the sent command matches any of the below
				if (commandParts[0].equalsIgnoreCase("/msg") || commandParts[0].equalsIgnoreCase("/w")
						|| commandParts[0].equalsIgnoreCase("/tell")) {

					if(commandParts.length >= 1) {
						if(!event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
							Player p = Bukkit.getPlayer(commandParts[1]);
							if(p == null) return;
							
							if(!p.getGameMode().equals(GameMode.SURVIVAL)) return;
							
							event.getPlayer().sendMessage(ChatColor.RED + "You may not whisper to players who are still playing!");
							return;
						}
					}
				}
			}

		} else if (commandParts[0].equalsIgnoreCase("/me")) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.RED + "You're not allowed to use /me!");
		}
	}
}
