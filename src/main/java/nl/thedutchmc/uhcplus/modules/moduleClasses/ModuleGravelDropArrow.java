package nl.thedutchmc.uhcplus.modules.moduleClasses;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class ModuleGravelDropArrow implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {

		if (event.getBlock().getType().equals(Material.GRAVEL)) {

			event.setDropItems(false);
			event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(),
					new ItemStack(Material.ARROW));

		}

	}

}
