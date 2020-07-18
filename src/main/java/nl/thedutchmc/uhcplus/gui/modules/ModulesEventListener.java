package nl.thedutchmc.uhcplus.gui.modules;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import nl.thedutchmc.uhcplus.presets.PresetHandler;

public class ModulesEventListener implements Listener {

	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {

		if (!event.getInventory().equals(ModulesGui.getGui()) || event.getCurrentItem() == null
				|| event.getCurrentItem().getType().equals(Material.AIR))
			return;

		event.setCancelled(true);

		final ItemStack clickedItem = event.getCurrentItem();

		if (clickedItem.getType().equals(Material.BARRIER)) {
			event.getWhoClicked().closeInventory();
			return;
		}

		if (clickedItem.getType().equals(Material.IRON_AXE))
			PresetHandler.moduleAxeOfDestruction = !clickedItem.getItemMeta().hasEnchants();
		if (clickedItem.getType().equals(Material.NETHERITE_AXE))
			PresetHandler.axeOfDestructionLevelling = !clickedItem.getItemMeta().hasEnchants();
		if (clickedItem.getType().equals(Material.DIORITE))
			PresetHandler.moduleDioriteDamage = !clickedItem.getItemMeta().hasEnchants();
		if (clickedItem.getType().equals(Material.GRINDSTONE))
			PresetHandler.moduleDissalowGrindingEnchantedTools = !clickedItem.getItemMeta().hasEnchants();
		if (clickedItem.getType().equals(Material.IRON_PICKAXE))
			PresetHandler.moduleEnchantedTools = !clickedItem.getItemMeta().hasEnchants();
		if (clickedItem.getType().equals(Material.GRAVEL))
			PresetHandler.moduleGravelDropArrow = !clickedItem.getItemMeta().hasEnchants();
		if (clickedItem.getType().equals(Material.ENCHANTING_TABLE))
			PresetHandler.moduleInfiniteEnchanting = !clickedItem.getItemMeta().hasEnchants();
		if (clickedItem.getType().equals(Material.OAK_LEAVES))
			PresetHandler.moduleLeaveDecay = !clickedItem.getItemMeta().hasEnchants();
		if (clickedItem.getType().equals(Material.FURNACE))
			PresetHandler.moduleOreAutoSmelt = !clickedItem.getItemMeta().hasEnchants();
		if (clickedItem.getType().equals(Material.STRING))
			PresetHandler.moduleSheepDropString = !clickedItem.getItemMeta().hasEnchants();
		if (clickedItem.getType().equals(Material.IRON_SWORD))
			PresetHandler.moduleSwordOfDivinity = !clickedItem.getItemMeta().hasEnchants();
		if (clickedItem.getType().equals(Material.NETHERITE_SWORD))
			PresetHandler.swordOfDivinityLevelling = !clickedItem.getItemMeta().hasEnchants();
		if (clickedItem.getType().equals(Material.BARREL))
			PresetHandler.moduleTeamInventory = !clickedItem.getItemMeta().hasEnchants();
		if (clickedItem.getType().equals(Material.OAK_LOG))
			PresetHandler.moduleTreeFullRemove = !clickedItem.getItemMeta().hasEnchants();
		if (clickedItem.getType().equals(Material.APPLE))
			PresetHandler.moduleOneHeartStart = !clickedItem.getItemMeta().hasEnchants();
		if (clickedItem.getType().equals(Material.SLIME_BALL))
			PresetHandler.moduleSlimeBoost = !clickedItem.getItemMeta().hasEnchants();

		PresetHandler.changedPresetOption();

		ModulesGui.openGui(event.getWhoClicked());
	}

	@EventHandler
	public void onInventoryDragEvent(InventoryDragEvent event) {
		if (event.getInventory().equals(ModulesGui.getGui()))
			event.setCancelled(true);
	}

}
