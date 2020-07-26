package nl.thedutchmc.uhcplus.modules.modules.kits;

import java.io.Serializable;

public class KitWritable implements Serializable {

    private static final long serialVersionUID = 1L;

	private String kitName;
	private boolean kitEnabled;
	private String serializedItemStack;
	
	public KitWritable(String kitName, boolean kitEnabled, String serializedItemStack) {
		this.kitName = kitName;
		this.kitEnabled = kitEnabled;
		this.serializedItemStack = serializedItemStack;
	}
	
	public String getKitName() {
		return kitName;
	}
	
	public boolean getKitEnabled() {
		return kitEnabled;
	}
	
	public String getSerializedItemStack() {
		return serializedItemStack;
	}
}
