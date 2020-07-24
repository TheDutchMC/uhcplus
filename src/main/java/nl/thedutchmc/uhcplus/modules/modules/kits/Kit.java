package nl.thedutchmc.uhcplus.modules.modules.kits;

import java.util.List;

import org.bukkit.inventory.ItemStack;

public class Kit {

	private String kitName;
	private List<ItemStack> kitItems;
	private boolean kitEnabled;
	
	public Kit(String kitName) {
		this.kitName = kitName;
	}
	
	public void setKitItems(List<ItemStack> kitItems) {
		this.kitItems = kitItems;
	}
	
	public List<ItemStack> getKitItems() {
		return kitItems;
	}
	
	public void setKitName(String kitName) {
		this.kitName = kitName;
	}
	
	public String getKitName() {
		return kitName;
	}
	
	public void setKitEnabled(boolean enabled) {
		enabled = kitEnabled;
	}
	
	public boolean getKitEnabled() {
		return kitEnabled;
	}
	
}
