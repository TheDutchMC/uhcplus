package nl.thedutchmc.uhcplus.modules.modules;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.modules.BlockChecker;

public class ModuleLeaveDecay implements Listener {

	private final static int decayRange = 7;

	private final static BlockFace[] NeighboringBlocks = new BlockFace[] { BlockFace.UP, BlockFace.DOWN,
			BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {

		Block blockBroken = event.getBlock();

		// Check if it is not a log, since we dont want leaves to decay then
		if (!BlockChecker.isLog(blockBroken.getType())) {
			return;
		}

		new BukkitRunnable() {

			@Override
			public void run() {
				onBlockBreak(blockBroken);
			}
		}.runTask(UhcPlus.INSTANCE);

	}

	@EventHandler
	public void onLeaveDecay(LeavesDecayEvent event) {
		onBlockBreak(event.getBlock());
	}

	private void onBlockBreak(Block block) {
		for (BlockFace blockFace : NeighboringBlocks) {

			final Block relativeBlock = block.getRelative(blockFace);

			// The block isn't a leave, so we dont want it to decay
			if (!BlockChecker.isLeave(block.getType())) {
				return;
			}

			// Log is nearby, don't decay too fast.
			if (findNeighboringLog(relativeBlock, decayRange)) {
				continue;
			}

			new BukkitRunnable() {

				@Override
				public void run() {

					if (!BlockChecker.isLeave(relativeBlock.getType())) {
						return;
					}

					LeavesDecayEvent leaveDecayEvent = new LeavesDecayEvent(relativeBlock);

					Bukkit.getPluginManager().callEvent(leaveDecayEvent);

					if (!leaveDecayEvent.isCancelled()) {
						relativeBlock.breakNaturally();

						// Since not all leaves drop apples in vanilla, we add a chance that they do,
						// chance is 1.5%
						// 0.015 = 1.5%
						if (Math.random() < 0.015) {

							// Get the location of the leave that decayed
							Location location = new Location(relativeBlock.getWorld(), relativeBlock.getX(),
									relativeBlock.getY(), relativeBlock.getZ());

							// Drop the apple
							relativeBlock.getWorld().dropItem(location, new ItemStack(Material.APPLE, 1));
						}
					}

				}

			}.runTaskLater(UhcPlus.INSTANCE, 5);
		}
	}

	private boolean findNeighboringLog(Block block, int i) {
		if (BlockChecker.isLog(block.getType())) {
			return true;
		} else if (BlockChecker.isLeave(block.getType())) {
			i--;
		} else {
			return false;
		}

		if (i > 0) {
			boolean result = false;

			for (BlockFace face : BlockFace.values()) {
				if (face.equals(BlockFace.DOWN) || face.equals(BlockFace.UP) || face.equals(BlockFace.NORTH)
						|| face.equals(BlockFace.EAST) || face.equals(BlockFace.SOUTH) || face.equals(BlockFace.WEST)) {

					boolean bool = findNeighboringLog(block.getRelative(face), i);
					if (bool)
						result = bool;
				}
			}
			return result;
		}
		return false;
	}
}
