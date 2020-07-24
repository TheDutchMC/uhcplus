package nl.thedutchmc.uhcplus.modules.modules.kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

public class KitHandler {

	public static List<Kit> kits = new ArrayList<>();
	public static KitStorageHandler kitStorageHandler = new KitStorageHandler();
	
	public static void setup() {
		kitStorageHandler.loadStorage();
	}
	
	public static void newKit(String kitName, List<ItemStack> kitItems) {
		Kit k = new Kit(kitName);
		k.setKitItems(kitItems);
		kits.add(k);
		kitStorageHandler.writeStorage();
	}
	
	public static void modifyKit(String kitName, List<ItemStack> newKitItems) {
		for(int i = 0; i < kits.size(); i++) {
			Kit k = kits.get(i);
			
			if(k.getKitName().equals(kitName)) {
				k.setKitItems(newKitItems);
				kits.set(i, k);
				kitStorageHandler.writeStorage();
				
				break;
			}
		}
		
	}

	public static Kit getKit(String kitName) {
		for(Kit k : kits) {
			if(k.getKitName().equals(kitName)) {
				return k;
			}
		}
		
		return null;
	}
	
	public static void removeKit(String kitName) {
		for(Kit k : kits) {
			if(k.getKitName().equals(kitName)) {
				kits.remove(k);
				kitStorageHandler.writeStorage();
				
				break;
			}
		}
	}
	
	public static List<Kit> getEnabledKits() {
		List<Kit> enabledKits = new ArrayList<>();
		for(Kit k : kits) {
			if(k.getKitEnabled()) enabledKits.add(k);
		}
		
		return enabledKits;
	}
}
