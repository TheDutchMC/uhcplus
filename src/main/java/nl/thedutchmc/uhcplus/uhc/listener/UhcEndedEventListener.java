package nl.thedutchmc.uhcplus.uhc.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import nl.thedutchmc.uhcplus.ConfigurationHandler;
import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.events.UhcEndedEvent;

public class UhcEndedEventListener implements Listener {

	@EventHandler
	public void onUhcEndedEvent(UhcEndedEvent event) {

		Bukkit.broadcastMessage(ChatColor.GRAY + "[UhcPlus] " + ChatColor.AQUA + "UHC ended!");

		// Iterate over the winning team and form a Strign with their names
		String winningPlayers = "";
		for (int i = 0; i < event.getWinningPlayers().size(); i++) {
			Player p = Bukkit.getPlayer(event.getWinningPlayers().get(i));

			if (i == 0) {
				winningPlayers = ChatColor.RED + p.getName();
			} else {
				winningPlayers += ChatColor.GOLD + ", " + ChatColor.RED + p.getName();
			}
		}

		// Broadcast which players have won
		Bukkit.broadcastMessage(
				ChatColor.GRAY + "[UhcPlus] " + ChatColor.AQUA + "This UHC has been won by " + winningPlayers);

		// Check if the server should auto-restart
		if (!ConfigurationHandler.restartAfterUhc)
			return;

		// Broadcast in how many seconds the server will restart
		Bukkit.broadcastMessage(ChatColor.GRAY + "[UhcPlus] " + ChatColor.AQUA + "Server shutting down in "
				+ ChatColor.RED + ConfigurationHandler.timeUntilRestart + ChatColor.AQUA + " seconds!");

		// Schedule the restart
		new BukkitRunnable() {

			@Override
			public void run() {

				new BukkitRunnable() {

					@Override
					public void run() {
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "restart");
					}
				}.runTask(UhcPlus.INSTANCE);
			}
		}.runTaskLaterAsynchronously(UhcPlus.INSTANCE, ConfigurationHandler.timeUntilRestart * 20);

		// Check if the time until restart is bigger than 60 seconds
		if (ConfigurationHandler.timeUntilRestart > 60) {
			// Schedule 60 seconds warning
			new BukkitRunnable() {

				@Override
				public void run() {

					new BukkitRunnable() {

						@Override
						public void run() {
							Bukkit.broadcastMessage(ChatColor.GRAY + "[UhcPlus]" + ChatColor.AQUA + " Restarting in "
									+ ChatColor.RED + "60" + ChatColor.AQUA + " seconds!");
						}
					}.runTask(UhcPlus.INSTANCE);

				}
			}.runTaskLaterAsynchronously(UhcPlus.INSTANCE, (ConfigurationHandler.timeUntilRestart - 60) * 20);
		}

		// Check if the time until restart is bigger than 10 seconds
		if (ConfigurationHandler.timeUntilRestart > 10) {
			// Schedule 10 seconds warning
			new BukkitRunnable() {

				@Override
				public void run() {
					new BukkitRunnable() {

						@Override
						public void run() {
							Bukkit.broadcastMessage(ChatColor.GRAY + "[UhcPlus] " + ChatColor.AQUA + "Restarting in "
									+ ChatColor.RED + "10" + ChatColor.AQUA + " seconds!");
						}
					}.runTask(UhcPlus.INSTANCE);
				}
			}.runTaskLaterAsynchronously(UhcPlus.INSTANCE, (ConfigurationHandler.timeUntilRestart - 10) * 20);
		}
	}
}
