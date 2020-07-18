package nl.thedutchmc.uhcplus.modules.moduleClasses;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.presets.PresetHandler;

public class ModuleDioriteDamage implements Listener {

	static HashMap<UUID, Integer> playersWithDiorite = new HashMap<>();
	static HashMap<UUID, Boolean> playersWarningGiven = new HashMap<>();
	static BukkitTask damageTask;
	
	@EventHandler
	public void onPlayerPickupItemEvent(EntityPickupItemEvent event) {

		if(!(event.getEntity() instanceof Player)) return;

		ItemStack i = event.getItem().getItemStack();
		if(!i.getType().equals(Material.DIORITE) && !i.getType().equals(Material.POLISHED_DIORITE)) return;
		
		Player player = (Player) event.getEntity();
		
		if(!playersWithDiorite.containsKey(player.getUniqueId())) {
			playersWithDiorite.put(player.getUniqueId(), 0);
			playersWarningGiven.put(player.getUniqueId(), false);
		}
	}
	
	public void dioriteDamage() {
		damageTask = new BukkitRunnable() {
			
			@Override
			public void run() {
				
				if(!PresetHandler.moduleDioriteDamage) return;
				
				for(Player p : Bukkit.getOnlinePlayers()) {
					UUID uuid = p.getUniqueId();
					
					if(playersWithDiorite.containsKey(uuid)) {
						
						if(!p.getInventory().contains(Material.DIORITE) && !p.getInventory().contains(Material.POLISHED_DIORITE)) {
							playersWithDiorite.remove(uuid);
							playersWarningGiven.remove(uuid);
							continue;
						}
						
						int itemHeldSeconds = playersWithDiorite.get(uuid);
												
						if(itemHeldSeconds >= 10 && itemHeldSeconds < 50) {
							p.damage(0.1);
						} else if(itemHeldSeconds >= 50 && itemHeldSeconds < 100) {
							p.damage(0.5);
						} else if(itemHeldSeconds >= 100) {
							p.damage(1.0);
						}
						
						if(p.getHealth() < 4 && !playersWarningGiven.get(uuid)) {
							p.sendMessage(ChatColor.RED + "Warning! You are dying of diorite poisioning! Remove it from your inventory quickly!");
							playersWarningGiven.put(uuid, true);
						}
						
						itemHeldSeconds++;
						playersWithDiorite.put(uuid, itemHeldSeconds);
					}
				}
			}
		}.runTaskTimer(UhcPlus.INSTANCE, 100, 20);
	}
	
	public void cancelDamageTask() {
		if(damageTask != null) damageTask.cancel();
	}
}
