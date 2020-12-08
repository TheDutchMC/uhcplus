package nl.thedutchmc.uhcplus.uhc.scheduler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.scheduler.BukkitRunnable;

import nl.thedutchmc.uhcplus.presets.PresetHandler;
import nl.thedutchmc.uhcplus.uhc.GameState;
import nl.thedutchmc.uhcplus.uhc.UhcHandler;
import nl.thedutchmc.uhcplus.UhcPlus;

public class WorldborderScheduler {

	public void scheduleWorldborder() {

		double worldBorderStartingSize = Double.valueOf((int) PresetHandler.getPrefabOption("worldBorderSize"));
		double worldBorderShrinkTo = Double.valueOf((int) PresetHandler.getPrefabOption("worldBorderShrinkTo"));

		long worldBorderStartShrinkAfter = Long.valueOf((int) PresetHandler.getPrefabOption("worldBorderShrinkAfter"));
		long gameTimeLeft = Long.valueOf((int) PresetHandler.getPrefabOption("gameTime") - worldBorderStartShrinkAfter);

		long worldBorderStartShrinkAfterTick = worldBorderStartShrinkAfter * 60 * 20;
		long gameTimeLeftSecond = gameTimeLeft * 60;

		World overworld = Bukkit.getServer().getWorld("uhcworld");

		WorldBorder worldBorder = overworld.getWorldBorder();
		worldBorder.setSize(worldBorderStartingSize);
		worldBorder.setCenter(0, 0);

		String minuteSinglePlural = (worldBorderStartShrinkAfter != 1) ? " minutes" : " minute";
		Bukkit.broadcastMessage(ChatColor.GRAY + "The world border will start shrinking in " + ChatColor.RED
				+ worldBorderStartShrinkAfter + ChatColor.GRAY + minuteSinglePlural + "!");

		new BukkitRunnable() {

			@Override
			public void run() {
				
				if(UhcHandler.getGameState().equals(GameState.END)) {
					worldBorder.setSize(100000);
					this.cancel();
					return;
				}
				
				worldBorder.setSize(worldBorderShrinkTo, gameTimeLeftSecond);
			}
		}.runTaskLaterAsynchronously(UhcPlus.INSTANCE, worldBorderStartShrinkAfterTick);

	}
}
