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
			PresetHandler.setPrefabOption("moduleAxeOfDestruction", !clickedItem.getItemMeta().hasEnchants());
		if (clickedItem.getType().equals(Material.NETHERITE_AXE))
			PresetHandler.setPrefabOption("axeOfDestructionLevelling", !clickedItem.getItemMeta().hasEnchants());
		if (clickedItem.getType().equals(Material.DIORITE))
			PresetHandler.setPrefabOption("moduleDioriteDamage", !clickedItem.getItemMeta().hasEnchants());
		if (clickedItem.getType().equals(Material.GRINDSTONE))
			PresetHandler.setPrefabOption("moduleDissalowGrindingEnchantedTools", !clickedItem.getItemMeta().hasEnchants());
		if (clickedItem.getType().equals(Material.IRON_PICKAXE))
			PresetHandler.setPrefabOption("moduleEnchantedTools", !clickedItem.getItemMeta().hasEnchants());
		if (clickedItem.getType().equals(Material.GRAVEL))
			PresetHandler.setPrefabOption("moduleGravelDropArrow", !clickedItem.getItemMeta().hasEnchants());
		if (clickedItem.getType().equals(Material.ENCHANTING_TABLE))
			PresetHandler.setPrefabOption("moduleInfiniteEnchanting", !clickedItem.getItemMeta().hasEnchants());
		if (clickedItem.getType().equals(Material.OAK_LEAVES))
			PresetHandler.setPrefabOption("moduleLeaveDecay", !clickedItem.getItemMeta().hasEnchants());
		if (clickedItem.getType().equals(Material.FURNACE))
			PresetHandler.setPrefabOption("moduleOreAutoSmelt", !clickedItem.getItemMeta().hasEnchants());
		if (clickedItem.getType().equals(Material.STRING))
			PresetHandler.setPrefabOption("moduleSheepDropString", !clickedItem.getItemMeta().hasEnchants());
		if (clickedItem.getType().equals(Material.IRON_SWORD))
			PresetHandler.setPrefabOption("moduleSwordOfDivinity", !clickedItem.getItemMeta().hasEnchants());
		if (clickedItem.getType().equals(Material.NETHERITE_SWORD))
			PresetHandler.setPrefabOption("swordOfDivinityLevelling", !clickedItem.getItemMeta().hasEnchants());
		if (clickedItem.getType().equals(Material.BARREL))
			PresetHandler.setPrefabOption("moduleTeamInventory", !clickedItem.getItemMeta().hasEnchants());
		if (clickedItem.getType().equals(Material.OAK_LOG))
			PresetHandler.setPrefabOption("moduleTreeFullRemove", !clickedItem.getItemMeta().hasEnchants());
		if (clickedItem.getType().equals(Material.APPLE))
			PresetHandler.setPrefabOption("moduleOneHeartStart", !clickedItem.getItemMeta().hasEnchants());
		if (clickedItem.getType().equals(Material.SLIME_BALL))
			PresetHandler.setPrefabOption("moduleSlimeBoost", !clickedItem.getItemMeta().hasEnchants());
		if (clickedItem.getType().equals(Material.STICK))
			PresetHandler.setPrefabOption("moduleSticksFromLogs", !clickedItem.getItemMeta().hasEnchants());

		PresetHandler.changedPresetOption();

		ModulesGui.openGui(event.getWhoClicked());
	}

	@EventHandler
	public void onInventoryDragEvent(InventoryDragEvent event) {
		if (event.getInventory().equals(ModulesGui.getGui()))
			event.setCancelled(true);
	}

}
