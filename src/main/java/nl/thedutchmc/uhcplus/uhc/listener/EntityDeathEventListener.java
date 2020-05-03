package nl.thedutchmc.uhcplus.uhc.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class EntityDeathEventListener implements Listener {

	@EventHandler
	public void onEntityDeathEvent(EntityDeathEvent event) {
		
		EntityType eventEntityType = event.getEntityType();
		
		//Check if the killed entity is a cow
		if(eventEntityType.equals(EntityType.COW)) {
			
			//Grab the location of the killed cow
			Location deathLocation = event.getEntity().getLocation();
			
			//Drop leather at the death point of the cow
			deathLocation.getBlock().getWorld().dropItemNaturally(deathLocation, new ItemStack(Material.LEATHER, 1));
			
			//We don't need to remove the natural drops of the cow, since we still want it to drop beef, and if the player
			//is lucky it might also drop more leather
			
		}
		
	}
}
