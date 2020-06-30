package nl.thedutchmc.uhcplus.modules.moduleListeners;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import nl.thedutchmc.uhcplus.UhcPlus;

@SuppressWarnings("deprecation")
public class ModuleDioriteDamage implements Listener {

	private UhcPlus plugin;
	
	public ModuleDioriteDamage(UhcPlus plugin) {
		this.plugin = plugin;
	}
	
	static List<UUID> playersWithDiorite = new ArrayList<>();
	
	@EventHandler
	public void onPlayerPickupItemEvent(PlayerPickupItemEvent event) {
		
		Item item = event.getItem();
		if(item.getItemStack().getType().equals(Material.DIORITE) || item.getItemStack().getType().equals(Material.POLISHED_DIORITE)) {
			
			Player player = event.getPlayer();

			playersWithDiorite.add(player.getUniqueId());
			new BukkitRunnable() {
				
				@Override
				public void run() {
					Inventory inv = player.getInventory();
					if(!(inv.contains(Material.DIORITE) || inv.contains(Material.POLISHED_DIORITE))) {
						
						playersWithDiorite.remove(player.getUniqueId());
					} else {
						player.damage(0.1);
					}
				}
			}.runTaskTimer(plugin, 20, 20);
			
		}
		
	}
}
