package nl.thedutchmc.uhcplus.modules.modules;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.presets.PresetHandler;

public class ModuleSlimeBoost implements Listener {

	static HashMap<UUID, Integer> playersWithSlime = new HashMap<>();
	static BukkitTask boostTask;
	
	@EventHandler
	public void onEntityPickupItemEvent(EntityPickupItemEvent event) {
	
		if(!(event.getEntity() instanceof Player)) return;
				
		ItemStack i = event.getItem().getItemStack();
		if(!i.getType().equals(Material.SLIME_BALL) && !i.getType().equals(Material.SLIME_BLOCK)) return;
		
		Player p = (Player) event.getEntity();
		UUID uuid = p.getUniqueId();
		
		if(!playersWithSlime.containsKey(uuid)) {
			playersWithSlime.put(uuid, 0);
			p.sendMessage(ChatColor.GOLD + "You have picked up slime, this will give you a strength boost over time, so hold on to it!");
		}
	}
	
	public void slimeBoost() {
		boostTask = new BukkitRunnable() {
			
			@Override
			public void run() {
				
				if(!PresetHandler.moduleSlimeBoost) return;
				
				for(Player p : Bukkit.getOnlinePlayers()) {
					UUID uuid = p.getUniqueId();
					
					if(playersWithSlime.containsKey(uuid)) {
						
						if(!p.getInventory().contains(Material.SLIME_BALL) && !p.getInventory().contains(Material.SLIME_BLOCK)) {
							playersWithSlime.remove(uuid);
							if(p.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
							continue;
						}
						
						int itemHeldSeconds = playersWithSlime.get(uuid);
						
						if(itemHeldSeconds >= 30 && itemHeldSeconds < 200) {
							p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 0));
						} else if(itemHeldSeconds >= 200 && itemHeldSeconds < 600) {
							p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 1));
						} else if(itemHeldSeconds >= 600 && itemHeldSeconds < 1200) {
							p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 2));
						} else if(itemHeldSeconds >= 1200) {
							p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 3));
						}
						
						itemHeldSeconds++;
						playersWithSlime.put(uuid, itemHeldSeconds);
						
					}
				}
			}
		}.runTaskTimer(UhcPlus.INSTANCE, 20, 20);
	}
	
	public void cancelBoostTask() {
		if(boostTask != null) boostTask.cancel();
	}
	
}
