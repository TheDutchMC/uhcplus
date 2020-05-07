package nl.thedutchmc.uhcplus.uhc.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import nl.thedutchmc.uhcplus.ConfigurationHandler;

public class CraftItemEventListener implements Listener {

	static HashMap<ItemTimesCrafted, UUID> craftCount = new HashMap<>();
	
	@EventHandler
	public void onCraftItemEvent(CraftItemEvent event) {
		
		boolean itemTimesCraftedExists = false;
		
		//Check if the items enum contains the item that was crafted
		if(items.contains(event.getCurrentItem())) {
			
			//Loop over the craftCount hashmap
			for(Map.Entry<ItemTimesCrafted, UUID> entry : craftCount.entrySet()) {
				
				//Check if the entry we're at right now is of the player
				if (event.getWhoClicked().getUniqueId().equals(entry.getValue())) {
					
					//Check if the ItemTimesCrafted we're at right now has the correct material
					if(entry.getKey().getMaterialCrafted().equals(event.getCurrentItem().getType())) {
						
						itemTimesCraftedExists = true;
						
						//Check if amount of times the material has been crafted is more than or equal to what is allowed, cancel and notify the user
						if(entry.getKey().getTimesCrafted() >= ConfigurationHandler.limitCraftingAmount - 1) {
							
							event.setCancelled(true);
							event.getWhoClicked().sendMessage(ChatColor.RED + "You have exceeded the maximum amount of crafts for this item!");
							
						//Its less than what is allowed, so increase the times it has been crafted with 1.
						} else {
							entry.getKey().addTimesCrafted();
						}	
					}	
				}
			}
			
			//The ItemTimesCrafted does not exist yet for this material for this player, create it
			if(!itemTimesCraftedExists) {
				
				craftCount.put(new ItemTimesCrafted(
						event.getCurrentItem().getType()),
						event.getWhoClicked().getUniqueId());
				
			}
			
		}
	}
	
	
	enum items {
		
		WOODEN_SHOVEL,
		WOODEN_PICKAXE,
		WOODEN_AXE,
		WOODEN_HOE,
		WOODEN_SWORD,
		
		STONE_SHOVEL,
		STONE_PICKAXE,
		STONE_AXE,
		STONE_HOE,
		STONE_SWORD;
		
		public static boolean contains(ItemStack itemStack) {
			
			for(items item : values()) {
				
				if(item.name().equals(itemStack.getType().toString())) return true;
			}
			
			return false;
		}
	};
	
	public class ItemTimesCrafted {
		
		private Material materialCrafted;
		private int timesCrafted;
		
		public ItemTimesCrafted(Material materialCrafted) {
			this.materialCrafted = materialCrafted;
			timesCrafted = 0;
		}
		
		public Material getMaterialCrafted() {
			return materialCrafted;
		}
		
		public int getTimesCrafted() {
			return timesCrafted;
		}
		
		public void addTimesCrafted() {
			timesCrafted++;
		}
		
	}
	
}