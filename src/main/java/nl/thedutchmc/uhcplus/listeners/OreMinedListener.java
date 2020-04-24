package nl.thedutchmc.uhcplus.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import nl.thedutchmc.uhcplus.UhcPlus;

public class OreMinedListener implements Listener {

	private UhcPlus plugin;
	
	public OreMinedListener(UhcPlus plugin) {
		this.plugin = plugin;
		setDropCount(1);
	}
	
	
	int dropCount;
	
	void setDropCount(int dropCount) {
		//TODO integrate with preset settings
		
		dropCount = 1;
		//this.dropCount = dropCount;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		
		Material blockBroken = event.getBlock().getType();
		
		if(blockBroken.equals(Material.GOLD_ORE)) {
			event.setDropItems(true);
			event.setExpToDrop(5);
			
			event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), getItemStack(Material.GOLD_INGOT, dropCount));
			
		} else if(blockBroken.equals(Material.IRON_ORE)) {
			event.setDropItems(true);
			event.setExpToDrop(5);
			
			event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), getItemStack(Material.IRON_INGOT, dropCount));
			
		} else if(blockBroken.equals(Material.EMERALD_ORE)) {
			event.setDropItems(true);
			event.setExpToDrop(5);

			event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), getItemStack(Material.EMERALD, dropCount));
			
		} else if(blockBroken.equals(Material.COAL_ORE)) {
			event.setDropItems(true);
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
