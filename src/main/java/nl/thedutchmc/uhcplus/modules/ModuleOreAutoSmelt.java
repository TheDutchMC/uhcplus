package nl.thedutchmc.uhcplus.modules;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.presets.PresetHandler;

public class ModuleOreAutoSmelt implements Listener {

	@SuppressWarnings("unused")
	private UhcPlus plugin;
	
	public ModuleOreAutoSmelt(UhcPlus plugin) {
		this.plugin = plugin;
		dropCount = PresetHandler.moduleOreAutoSmeltIngotDrop;
	}

	int dropCount;
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		
		Material blockBroken = event.getBlock().getType();
		
		if(blockBroken.equals(Material.GOLD_ORE)) {
			event.setDropItems(false);
			event.setExpToDrop(5);
			
			event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), getItemStack(Material.GOLD_INGOT, dropCount));
			
		} else if(blockBroken.equals(Material.IRON_ORE)) {
			event.setDropItems(false);
			event.setExpToDrop(5);
			
			event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), getItemStack(Material.IRON_INGOT, dropCount));
			
		} else if(blockBroken.equals(Material.COAL_ORE)) {
			event.setDropItems(false);
			event.setExpToDrop(5);

			event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), getItemStack(Material.TORCH, 2));
		}
	}
	
	ItemStack getItemStack(Material material, int count) {
		ItemStack itemStack = new ItemStack(material);
		itemStack.setAmount(count);
		return itemStack;
	}
}
