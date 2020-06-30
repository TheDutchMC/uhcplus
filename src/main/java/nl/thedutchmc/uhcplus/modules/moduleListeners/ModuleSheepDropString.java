package nl.thedutchmc.uhcplus.modules.moduleListeners;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class ModuleSheepDropString implements Listener {

	@EventHandler
	public void onEntityDeathEvent(EntityDeathEvent event) {
		
		if(event.getEntityType().equals(EntityType.SHEEP)) {
			
			event.getDrops().clear();
			event.getDrops().add(new ItemStack(Material.STRING, 1));
			
		}	
	}
}
