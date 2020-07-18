package nl.thedutchmc.uhcplus.uhc.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;

import nl.thedutchmc.uhcplus.events.UhcStartedEvent;

public class UhcStartedEventListener implements Listener {

	// This listener will remove the lobby at the start of the UHC
	@EventHandler
	public void onUhcStartedEvent(UhcStartedEvent event) {

		World uhcWorld = Bukkit.getServer().getWorld("uhcworld");
		com.sk89q.worldedit.world.World uhcWorldWE = BukkitAdapter.adapt(uhcWorld);

		BlockVector3 posA = BlockVector3.at(-50, 199, -50);
		BlockVector3 posB = BlockVector3.at(50, 250, 50);

		CuboidRegion lobbySelection = new CuboidRegion(uhcWorldWE, posA, posB);

		for (BlockVector3 point : lobbySelection) {

			Location pointLocation = new Location(uhcWorld, point.getX(), point.getY(), point.getZ());
			Block pointBlock = pointLocation.getBlock();
			pointBlock.setType(Material.AIR);

		}

	}
}
