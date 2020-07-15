package nl.thedutchmc.uhcplus.modules.moduleClasses;

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
import nl.thedutchmc.uhcplus.events.SwordOfDivinityLevelUpEvent;
import nl.thedutchmc.uhcplus.presets.PresetHandler;

public class ModuleSwordOfDivinity implements Listener {

	public static HashMap<UUID, UUID> swordPlayerTracker = new HashMap<>();
	public static List<UUID> playerSwordCraftedChecker = new ArrayList<>();
	
	private static ItemStack itemStackCraftEvent, itemStackEntityPickupEvent;
	
	@EventHandler
	public void onCraftItemEvent(CraftItemEvent event) {
		
		itemStackCraftEvent = event.getRecipe().getResult();
		
		if(!itemStackCraftEvent.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Sword of Divinity")) return;
		
		if(playerSwordCraftedChecker.contains(event.getWhoClicked().getUniqueId())) {
			event.getWhoClicked().sendMessage(ChatColor.RED + "You may only craft this sword once!");
			event.setCancelled(true);
			return;
		}
		
		//Check if the player is shift-crafting, we do not allow this
		if(event.getClick().equals(ClickType.SHIFT_LEFT)) {
			event.getWhoClicked().sendMessage(ChatColor.RED + "You may not shift-craft this item!");
			event.setCancelled(true);
			return;
		}
		
		UUID swordUuid = UUID.fromString(itemStackCraftEvent.getItemMeta().getLore().get(1));
		swordPlayerTracker.put(swordUuid, event.getWhoClicked().getUniqueId());
		playerSwordCraftedChecker.add(event.getWhoClicked().getUniqueId());
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				Player p = Bukkit.getPlayer(swordPlayerTracker.get(swordUuid));
				Inventory inv = p.getInventory();
				
				for(int i = 0; i < inv.getContents().length; i++) {
					ItemStack is = inv.getContents()[i];
					
					if(is != null) {
						if(is.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Sword of Divinity")) {
							
							int swordLevel = Integer.valueOf(is.getItemMeta().getLore().get(0).split(" ")[1]);
							
							if(swordLevel == 1) {
								
								inv.remove(is);
								inv.setItem(i, getSwordOfDivinityLevelOne(swordUuid));
								
								new BukkitRunnable() {
									@Override
									public void run() {
										
										Bukkit.getPluginManager().callEvent(new SwordOfDivinityLevelUpEvent(swordLevel+1, swordUuid));
									}
								}.runTask(UhcPlus.INSTANCE);
							}
							
						}
					}
					
				}
			}
		}.runTaskLaterAsynchronously(UhcPlus.INSTANCE, PresetHandler.moduleSwordOfDivinityLevelOneTime * 20);
	}
	
	@EventHandler
	public void onEntityPickupItemEvent(EntityPickupItemEvent event) {
		
		itemStackEntityPickupEvent = event.getItem().getItemStack();
		if(!itemStackEntityPickupEvent.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Sword of Divinity")) return;
		
		swordPlayerTracker.replace(UUID.fromString(itemStackEntityPickupEvent.getItemMeta().getLore().get(1)), event.getEntity().getUniqueId());
	}
	
	@EventHandler
	public void onSwordLevelUpEvent(SwordOfDivinityLevelUpEvent event) {
		
		if(event.getSwordLevel() == 2) {
			new BukkitRunnable() {
				@Override
				public void run() {
					
					UUID swordUuid = event.getSwordUuid();
					
					Player p = Bukkit.getPlayer(swordPlayerTracker.get(swordUuid));
					Inventory inv = p.getInventory();
					
					for(int i = 0; i < inv.getContents().length; i++) {
						ItemStack is = inv.getContents()[i];
						
						if(is == null) return;
						
						if(is.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Sword of Divinity")) {
							
							inv.remove(is);
							inv.setItem(i, getSwordOfDivinityLevelTwo(swordUuid));
						}	
					}
				}
			}.runTaskLaterAsynchronously(UhcPlus.INSTANCE, (PresetHandler.moduleSwordOfDivinityLevelTwoTime - PresetHandler.moduleSwordOfDivinityLevelOneTime) * 20);
		}
		
	}
	
	private static ItemStack getSwordOfDivinityLevelOne(UUID swordUuid) {
		ItemStack stack = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Sword of Divinity");
		List<String> lores = new ArrayList<>();
		lores.add("Level 2");
		lores.add(swordUuid.toString());
		meta.setLore(lores);
		stack.setItemMeta(meta);
		
		stack.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
		stack.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
		stack.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
		
		return stack;
	}
	
	private static ItemStack getSwordOfDivinityLevelTwo(UUID swordUuid) {
		ItemStack stack = new ItemStack(Material.NETHERITE_SWORD);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Ultimate Sword of Divinity");
		List<String> lores = new ArrayList<>();
		lores.add("Level 3");
		lores.add(swordUuid.toString());
		meta.setLore(lores);
		stack.setItemMeta(meta);
		
		stack.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 4);
		stack.addUnsafeEnchantment(Enchantment.KNOCKBACK, 3);
		stack.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
		
		return stack;
	}
	
}
