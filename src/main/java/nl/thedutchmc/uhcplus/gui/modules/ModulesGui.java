package nl.thedutchmc.uhcplus.gui.modules;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.gui.CreateItem;
import nl.thedutchmc.uhcplus.presets.PresetHandler;

public class ModulesGui {

	private static Inventory gui;

	public static void setupGui() {
		gui = Bukkit.createInventory(null, 27, "Modules");
		setupItems();
	}

	static void setupItems() {

		gui.setItem(0,
				createItem(Material.IRON_AXE, "Axe of Destruction", PresetHandler.moduleAxeOfDestruction,
						"This module adds the Axe of Destruction", "This is an iron axe enchanted with:",
						"- Sharpness II", "- Knockback I", "- Efficiency III", "- Durability II",
						"This tool can level up if it is enabled to a diamond and netherrite variant.",
						"Each level-up adds +1 on the enchantments."));

		gui.setItem(1, createItem(Material.NETHERITE_AXE, "Axe of Destruction levelling",
				PresetHandler.axeOfDestructionLevelling, "This enables the Axe of Destruction to level up"));

		gui.setItem(2, createItem(Material.DIORITE, "Diorite Damage", PresetHandler.moduleDioriteDamage,
				"This module will slowly damage the player if they hold on to Diorite for too long"));

		gui.setItem(3, createItem(Material.GRINDSTONE, "Dissalow Grinding Enchanted Tools",
				PresetHandler.moduleDissalowGrindingEnchantedTools, "This module will prevent tools created with",
				"the module Enchanted Tools from being grinded down for XP"));

		gui.setItem(4, createItem(Material.IRON_PICKAXE, "Enchanted Tools", PresetHandler.moduleEnchantedTools,
				"This module will turn any crafted tool into an enchanted one"));

		gui.setItem(5, createItem(Material.GRAVEL, "Gravel drops arrows", PresetHandler.moduleGravelDropArrow,
				"This module will let gravel drop arrows"));

		gui.setItem(6,
				createItem(Material.ENCHANTING_TABLE, "Infinite Enchanting", PresetHandler.moduleInfiniteEnchanting,
						"This module will give players a stack of enchanting tables",
						"and a stack of anvils, players will also receive the maximum amount of XP."));

		gui.setItem(7, createItem(Material.OAK_LEAVES, "Leave Decay", PresetHandler.moduleLeaveDecay,
				"This module will let leaves decay faster"));

		gui.setItem(8, createItem(Material.FURNACE, "Ore Auto Smelt", PresetHandler.moduleOreAutoSmelt,
				"This module will automatically smelt ores when mined"));

		gui.setItem(9, createItem(Material.STRING, "Sheep drop String", PresetHandler.moduleSheepDropString,
				"This module will let sheep drop string"));

		gui.setItem(10,
				createItem(Material.IRON_SWORD, "Sword of Divinity", PresetHandler.moduleSwordOfDivinity,
						"This module enables the Sword of Divinity", "This is an iron sword enchanted with:",
						"- Sharpness II", "- Knockback I",
						"This weapon can level up if it is enabled to a diamond and netherrite variant.",
						"Each level-up adds +1 on the enchantments."));

		gui.setItem(11, createItem(Material.NETHERITE_SWORD, "Sword of Divinity levelling",
				PresetHandler.swordOfDivinityLevelling, "This enables the Sword of Divinity to level up"));

		gui.setItem(12, createItem(Material.BARREL, "Team Inventory", PresetHandler.moduleTeamInventory,
				"This module will give each team a shared inventory", "accessible with /ti and /teaminventory"));

		gui.setItem(13, createItem(Material.OAK_LOG, "Tree full Remove", PresetHandler.moduleTreeFullRemove,
				"This module will remove the entire stem of a tree if one block of it is broken"));

		gui.setItem(14, createItem(Material.APPLE, "One Heart Start", PresetHandler.moduleOneHeartStart,
				"This module will give players one heart of health at the start of the UHC"));

		gui.setItem(15, createItem(Material.SLIME_BALL, "Slime Boost", PresetHandler.moduleSlimeBoost, "This module will give players a strength boost if they hold on to slime for a while"));
		
		gui.setItem(16, createItem(Material.STICK, "Sticks from logs", PresetHandler.moduleSticksFromLogs, "This module will allow players to craft 16 sticks from 2 logs"));
		
		//close menu button
		gui.setItem(26, CreateItem.create(Material.BARRIER, ChatColor.RESET + "Close menu"));
	}

	static ItemStack createItem(Material m, String name, boolean glowing, String... lore) {
		final ItemStack item = new ItemStack(m, 1);
		final ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(name);

		meta.setLore(Arrays.asList(lore));

		item.setItemMeta(meta);

		if (glowing)
			item.addUnsafeEnchantment(Enchantment.MENDING, 1);

		return item;
	}

	public static Inventory getGui() {
		return gui;
	}

	public static void openGui(HumanEntity ent) {
		setupItems();
		ent.openInventory(gui);
	}
}
