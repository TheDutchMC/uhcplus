package nl.thedutchmc.uhcplus.modules.modules.kits;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import nl.thedutchmc.uhcplus.UhcPlus;

public class KitStorage {
	
	public static Kit loadKit(String kitName) {
		
		File file = new File(UhcPlus.INSTANCE.getDataFolder() + File.separator + "kits", kitName + ".kit");
		
		if(!file.exists()) {
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		
		try {
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			
			Object obj = objectIn.readObject();
			objectIn.close();
			fileIn.close();
			
			KitWritable kw = (KitWritable) obj;
			
			List<ItemStack> kitItems = new ArrayList<>();
			for(ItemStackSerialized itemStackSerialized : kw.getItemsSerialized()) {
				Map<String, Object> itemSerialized = itemStackSerialized.getSerializedItemStack();
				
				ItemStack stack = ItemStack.deserialize(itemSerialized);
								
				kitItems.add(stack);
			}
			
			Kit k = new Kit(kw.getKitName());
			k.setKitEnabled(kw.getKitEnabled());
			k.setKitItems(kitItems);
			
			return k;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void writeKit(String kitName) {
		File file = new File(UhcPlus.INSTANCE.getDataFolder() + File.separator + "kits", kitName + ".kit");
		
		Kit k = null;
		
		for(Kit kit : KitHandler.kits) {
			if(kit.getKitName().equals(kitName)) {
				k = kit;
				break;
			}
		}	
		
		List<ItemStackSerialized> serializedItems = new ArrayList<>();
		for(int i = 0; i < k.getKitItems().size(); i++) {
			ItemStack stack = k.getKitItems().get(i);
			
			if(stack == null) continue;
			stack.setItemMeta(null);
			
			serializedItems.add(new ItemStackSerialized(stack.serialize()));
		}
		
		KitWritable kw = new KitWritable(k.getKitName(), k.getKitEnabled(), serializedItems);
		
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
			
			objOut.writeObject(kw);
			
			objOut.close();
			fileOut.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
