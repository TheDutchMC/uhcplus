package nl.thedutchmc.uhcplus.modules.modules.kits;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import nl.thedutchmc.uhcplus.UhcPlus;

public class KitHandler {

	public static List<Kit> kits = new ArrayList<>();
	
	public static void setup() {		
		File kitsDir = new File(UhcPlus.INSTANCE.getDataFolder() + File.separator + "kits");

		if(!kitsDir.exists()) {
			kitsDir.mkdirs();
		}
		
		FilenameFilter filter = new FilenameFilter() {
			
			@Override
			public boolean accept(File file, String name) {
				return name.endsWith(".kit");
			}
		};
		
		String[] filenames;
		filenames = kitsDir.list(filter);
		
		for(String filename : filenames) {
			kits.add(KitStorage.loadKit(filename.split(".kit")[0]));
		}
	}
	
	public static void newKit(String kitName, List<ItemStack> kitItems) {
		Kit k = new Kit(kitName);
		k.setKitItems(kitItems);
		k.setKitEnabled(true);
		kits.add(k);
		KitStorage.writeKit(kitName);
	}
	
	public static void modifyKit(String kitName, List<ItemStack> newKitItems) {
		for(int i = 0; i < kits.size(); i++) {
			Kit k = kits.get(i);
			
			if(k.getKitName().equals(kitName)) {
				k.setKitItems(newKitItems);
				kits.set(i, k);
				KitStorage.writeKit(kitName);
				
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
				KitStorage.writeKit(kitName);
				
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
	
	public static void setKitEnabled(String kitName, boolean enabled) {
		for(int i = 0; i < kits.size(); i++) {
			Kit k = kits.get(i);
			if(k.getKitName().equals(kitName)) {
							
				k.setKitEnabled(enabled);				
				
				kits.set(i, k);
				KitStorage.writeKit(kitName);
			}
		}
	}
}
