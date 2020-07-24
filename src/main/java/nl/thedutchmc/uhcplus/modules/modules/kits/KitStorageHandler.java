package nl.thedutchmc.uhcplus.modules.modules.kits;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import nl.thedutchmc.uhcplus.UhcPlus;

public class KitStorageHandler {

	private File file;
	private FileConfiguration storage;
	
	public FileConfiguration getStorage() {
		return storage;
	}
	
	public void loadStorage() {
		file = new File(UhcPlus.INSTANCE.getDataFolder() + File.separator + "kits", "storage.yml");
		
		if(!file.exists()) {
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
		
		storage = new YamlConfiguration();
		
		try {
			storage.load(file);
			readStorage();
		} catch (InvalidConfigurationException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void readStorage() {
		KitHandler.kits = (List<Kit>) this.getStorage().getList("kits");
	}
	
	public void writeStorage() {
		this.getStorage().set("kits", KitHandler.kits);
		
		try {
			this.getStorage().save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
