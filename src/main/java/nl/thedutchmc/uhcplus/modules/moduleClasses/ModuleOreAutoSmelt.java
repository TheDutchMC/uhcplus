package nl.thedutchmc.uhcplus.modules.moduleClasses;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import nl.thedutchmc.uhcplus.presets.PresetHandler;

public class ModuleOreAutoSmelt implements Listener {

	public ModuleOreAutoSmelt() {
		dropCount = PresetHandler.moduleOreAutoSmeltIngotDrop;
	}

	int dropCount;

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {

		Material blockBroken = event.getBlock().getType();
		GameMode gameMode = event.getPlayer().getGameMode();

		if (blockBroken.equals(Material.GOLD_ORE)) {
			if (!gameMode.equals(GameMode.SURVIVAL)
					|| event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.WOODEN_PICKAXE)
					|| event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.STONE_PICKAXE))
				
				return;

			event.setDropItems(false);
			event.setExpToDrop(5);

			event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(),
					getItemStack(Material.GOLD_INGOT, dropCount));

		} else if (blockBroken.equals(Material.IRON_ORE)) {
			if (!gameMode.equals(GameMode.SURVIVAL)
					|| event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.WOODEN_PICKAXE)
					|| event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.STONE_PICKAXE))
				return;

			event.setDropItems(false);
			event.setExpToDrop(5);

			event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(),
					getItemStack(Material.IRON_INGOT, dropCount));

		} else if (blockBroken.equals(Material.COAL_ORE)) {
			if (!gameMode.equals(GameMode.SURVIVAL)
					|| event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.WOODEN_PICKAXE)
					|| event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.STONE_PICKAXE))
				return;

			event.setDropItems(false);
			event.setExpToDrop(5);

			event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(),
					getItemStack(Material.TORCH, 2));
		} else if (blockBroken.equals(Material.DIAMOND_ORE)) {
			if (!gameMode.equals(GameMode.SURVIVAL)
					|| event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.WOODEN_PICKAXE)
					|| event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.STONE_PICKAXE))
				return;

			event.setDropItems(false);
			event.setExpToDrop(5);

			event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(),
					getItemStack(Material.DIAMOND, dropCount));
		}
	}

	ItemStack getItemStack(Material material, int count) {
		ItemStack itemStack = new ItemStack(material);
		itemStack.setAmount(count);
		return itemStack;
	}
}