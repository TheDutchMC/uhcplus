package nl.thedutchmc.uhcplus.modules.modules.kits;

import java.io.Serializable;
import java.util.List;

public class KitWritable implements Serializable {

    private static final long serialVersionUID = 1L;

	private String kitName;
	private boolean kitEnabled;
	private List<ItemStackSerialized> itemsSerialized;
	
	public KitWritable(String kitName, boolean kitEnabled, List<ItemStackSerialized> itemsSerialized) {
		this.kitName = kitName;
		this.kitEnabled = kitEnabled;
		this.itemsSerialized = itemsSerialized;
	}
	
	public String getKitName() {
		return kitName;
	}
	
	public boolean getKitEnabled() {
		return kitEnabled;
	}
	
	public List<ItemStackSerialized> getItemsSerialized() {
		return itemsSerialized;
	}
}
