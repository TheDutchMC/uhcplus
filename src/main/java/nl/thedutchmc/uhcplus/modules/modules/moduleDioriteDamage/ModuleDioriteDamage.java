package nl.thedutchmc.uhcplus.modules.modules.moduleDioriteDamage;

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
import nl.thedutchmc.uhcplus.presets.PresetHandler;
import nl.thedutchmc.uhcplus.UhcPlus;

public class ModuleDioriteDamage implements Listener {

	static HashMap<UUID, DioritePlayer> playersWithDiorite = new HashMap<>();
	static BukkitTask damageTask;
	
	@EventHandler
	public void onPlayerPickupItemEvent(EntityPickupItemEvent event) {

		if(!(event.getEntity() instanceof Player)) return;

		ItemStack i = event.getItem().getItemStack();
		if(!i.getType().equals(Material.DIORITE) && !i.getType().equals(Material.POLISHED_DIORITE)) return;
		
		Player p = (Player) event.getEntity();
		UUID uuid = p.getUniqueId();
		
		if(!playersWithDiorite.containsKey(uuid)) {
			playersWithDiorite.put(uuid, new DioritePlayer(uuid));
		}
	}
	
	public void dioriteDamage() {
		damageTask = new BukkitRunnable() {
			
			@Override
			public void run() {
				
				if(!(boolean) PresetHandler.getPrefabOption("moduleDioriteDamage")) return;
				
				for(Player p : Bukkit.getOnlinePlayers()) {
					UUID uuid = p.getUniqueId();
					
					if(playersWithDiorite.containsKey(uuid)) {
						
						if(!p.getInventory().contains(Material.DIORITE) && !p.getInventory().contains(Material.POLISHED_DIORITE)) {
							playersWithDiorite.remove(uuid);
							continue;
						}
						
						final DioritePlayer dp = playersWithDiorite.get(uuid);
						
						int itemHeldSeconds = dp.getSecondsHeld();
												
						if(itemHeldSeconds >= 10 && itemHeldSeconds < 50) {
							p.damage(0.1);
						} else if(itemHeldSeconds >= 50 && itemHeldSeconds < 100) {
							p.damage(0.5);
						} else if(itemHeldSeconds >= 100) {
							p.damage(1.0);
						}
						
						if(p.getHealth() < 4 && !dp.getWarned()) {
							p.sendMessage(ChatColor.RED + "Warning! You are dying of diorite poisioning! Remove it from your inventory quickly!");
							
							dp.setWarned(true);
							playersWithDiorite.put(uuid, dp);
						}
						
						itemHeldSeconds++;
						dp.setSecondsHeld(itemHeldSeconds);
						playersWithDiorite.put(uuid, dp);
					}
				}
			}
		}.runTaskTimer(UhcPlus.INSTANCE, 100, 20);
	}
	
	public void cancelDamageTask() {
		if(damageTask != null) damageTask.cancel();
	}
}
