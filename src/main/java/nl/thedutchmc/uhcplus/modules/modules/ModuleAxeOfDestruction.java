package nl.thedutchmc.uhcplus.modules.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.events.AxeOfDestructionLevelUpEvent;
import nl.thedutchmc.uhcplus.presets.PresetHandler;

public class ModuleAxeOfDestruction implements Listener {

	// First UUID: Axe UUID
	// Second UUID: Player UUID
	public static HashMap<UUID, UUID> axePlayerTracker = new HashMap<>();
	public static List<UUID> playerAxeCraftedTracker = new ArrayList<>();

	private static ItemStack itemStackCraftEvent, itemStackEntityPickupEvent;

	// Whenever a player crafts an item
	@EventHandler
	public void onCraftItemEvent(CraftItemEvent event) {

		// Get the itemstack for this event
		itemStackCraftEvent = event.getRecipe().getResult();

		// Check if the crafted item is an axe, else return
		if (!itemStackCraftEvent.getItemMeta().getDisplayName().equals(ChatColor.RED + "Axe of Destruction"))
			return;

		// Check if the player has already crafted one of these axes
		if (playerAxeCraftedTracker.contains(event.getWhoClicked().getUniqueId())) {
			event.getWhoClicked().sendMessage(ChatColor.RED + "You may only craft this axe once!");
			event.setCancelled(true);
			return;
		}

		// Check if the player is shift-crafting, we do not allow this
		if (event.getClick().equals(ClickType.SHIFT_LEFT)) {
			event.getWhoClicked().sendMessage(ChatColor.RED + "You may not shift-craft this item!");
			event.setCancelled(true);
			return;
		}

		// Get the axe uuid from the lore, and add it to the Hashmap
		UUID axeUuid = UUID.fromString(itemStackCraftEvent.getItemMeta().getLore().get(1));
		axePlayerTracker.put(axeUuid, event.getWhoClicked().getUniqueId());
		playerAxeCraftedTracker.add(event.getWhoClicked().getUniqueId());

		new BukkitRunnable() {

			@Override
			public void run() {

				// Get the player and their inventory
				Player p = Bukkit.getPlayer(axePlayerTracker.get(axeUuid));
				Inventory inv = p.getInventory();

				// Loop over all the items in the inventory
				for (int i = 0; i < inv.getStorageContents().length; i++) {
					ItemStack is = inv.getStorageContents()[i];

					// if nothing is nu,, check if the displayname matches
					if (is != null && is.getItemMeta() != null && is.getItemMeta().getDisplayName() != null) {
						if (is.getItemMeta().getDisplayName().equals(ChatColor.RED + "Axe of Destruction")) {

							// get the current axeLevel from the lore
							int axeLevel = Integer.valueOf(is.getItemMeta().getLore().get(0).split(" ")[1]);

							// If its level 1, we want to level it up
							if (axeLevel == 1) {

								// Remove the axe and give a new one of a higher level
								inv.remove(is);
								inv.setItem(i, getAxeOfDestructionLevelOne(axeUuid));

								// Calling events should be sync
								new BukkitRunnable() {
									@Override
									public void run() {

										// Call the levelUp event
										Bukkit.getPluginManager()
												.callEvent(new AxeOfDestructionLevelUpEvent(axeLevel + 1, axeUuid));
									}
								}.runTask(UhcPlus.INSTANCE);
							}
						}
					}
				}
			}
		}.runTaskLaterAsynchronously(UhcPlus.INSTANCE, PresetHandler.moduleAxeOfDestructionLevelOneTime * 20);
	}

	// If a player picks up the axe, we want to transfer the ownership, ie change it
	// in the hashmap
	@EventHandler
	public void onEntityPickupItemEvent(EntityPickupItemEvent event) {

		itemStackEntityPickupEvent = event.getItem().getItemStack();
		if (!itemStackEntityPickupEvent.getItemMeta().getDisplayName().equals(ChatColor.RED + "Axe of Destruction"))
			return;

		axePlayerTracker.replace(UUID.fromString(itemStackEntityPickupEvent.getItemMeta().getLore().get(1)),
				event.getEntity().getUniqueId());

	}

	// If the axe is levelled up to level two
	@EventHandler
	public void onAxeLevelUpEvent(AxeOfDestructionLevelUpEvent event) {

		// Check if the level is actually two
		if (event.getAxeLevel() == 2) {
			new BukkitRunnable() {

				@Override
				public void run() {

					UUID axeUuid = event.getAxeUuid();

					Player p = Bukkit.getPlayer(axePlayerTracker.get(axeUuid));
					Inventory inv = p.getInventory();

					// loop over the contents of the player's inventory
					for (int i = 0; i < inv.getStorageContents().length; i++) {
						ItemStack is = inv.getStorageContents()[i];

						if (is == null)
							return;

						// Check if the current slot is the Axe
						if (is.getItemMeta().getDisplayName().equals(ChatColor.RED + "Axe of Destruction")) {

							// Remove the axe, and give a new one of a higher level.
							inv.remove(is);
							inv.setItem(i, getAxeOfDestructionLevelTwo(axeUuid));

						}
					}

				}
			}.runTaskLaterAsynchronously(UhcPlus.INSTANCE, (PresetHandler.moduleAxeOfDestructionLevelTwoTime
					- PresetHandler.moduleAxeOfDestructionLevelOneTime) * 20);
		}

	}

	// Axe level one, shown as level two to the player
	private static ItemStack getAxeOfDestructionLevelOne(UUID axeUuid) {
		ItemStack stack = new ItemStack(Material.DIAMOND_AXE);
		ItemMeta meta = stack.getItemMeta();

		meta.setDisplayName(ChatColor.RED + "Axe of Destruction");
		List<String> lores = new ArrayList<>();
		lores.add("Level 2");
		lores.add(axeUuid.toString());
		meta.setLore(lores);
		stack.setItemMeta(meta);

		stack.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
		stack.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
		stack.addUnsafeEnchantment(Enchantment.DIG_SPEED, 4);
		stack.addUnsafeEnchantment(Enchantment.DURABILITY, 2);

		return stack;
	}

	// Axe level two, shown as level three to the player
	private static ItemStack getAxeOfDestructionLevelTwo(UUID axeUuid) {
		ItemStack stack = new ItemStack(Material.NETHERITE_AXE);
		ItemMeta meta = stack.getItemMeta();

		meta.setDisplayName(ChatColor.RED + "Ultimate Axe of Destruction");
		List<String> lores = new ArrayList<>();
		lores.add("Level 3");
		lores.add(axeUuid.toString());
		meta.setLore(lores);
		stack.setItemMeta(meta);

		stack.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 4);
		stack.addUnsafeEnchantment(Enchantment.KNOCKBACK, 3);
		stack.addUnsafeEnchantment(Enchantment.DIG_SPEED, 5);
		stack.addUnsafeEnchantment(Enchantment.DURABILITY, 3);

		return stack;
	}
}