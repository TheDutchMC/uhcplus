package nl.thedutchmc.uhcplus.modules.modules.kits;

import java.io.Serializable;
import java.util.Map;

public class ItemStackSerialized implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, Object> serializedItemStack;
	
	public ItemStackSerialized(Map<String, Object> serializedItemStack) {
		this.serializedItemStack = serializedItemStack;
	}
	
	public Map<String, Object> getSerializedItemStack() {
		return serializedItemStack;
	}
	
	
}
