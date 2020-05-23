package nl.thedutchmc.uhcplus.modules.moduleListeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.UhcPlus;

public class ModuleDissalowGrindingEnchantedTools implements Listener {

	private UhcPlus plugin;
	
	public ModuleDissalowGrindingEnchantedTools(UhcPlus plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {
		
		if(event.getInventory().getType().equals(InventoryType.GRINDSTONE)) {
			

			
			new BukkitRunnable() {
				
				@Override
				public void run() {
					
					ItemStack[] itemsInInventory = event.getInventory().getContents();
					for(ItemStack itemStack : itemsInInventory) {
						
						if(itemStack != null) {
							ItemMeta itemMeta = itemStack.getItemMeta();
							List<String> lore = new ArrayList<>();
							lore = itemMeta.getLore();
							
							if(lore != null) {
								if(lore.contains("An enchanted tool")) {
									
									HumanEntity humanEntity = event.getWhoClicked();
									humanEntity.closeInventory();
									
									Player p = (Player) humanEntity;
									p.sendMessage(ChatColor.RED + "You may not use this tool in a grindstone!");
								}
							}
						}
					}
				}
			}.runTaskLater(plugin, 1);	
		}
	}
}
