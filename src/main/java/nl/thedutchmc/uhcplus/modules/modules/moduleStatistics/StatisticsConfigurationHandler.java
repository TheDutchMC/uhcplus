package nl.thedutchmc.uhcplus.modules.modules.moduleStatistics;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import nl.thedutchmc.uhcplus.UhcPlus;

public class StatisticsConfigurationHandler {

	public static String ip, port, name, timezone, username, password;
	
	private File file;
	private FileConfiguration config;
	
	public FileConfiguration getConfig() {
		return config;
	}
	
	public void loadConfig() {
		file = new File(UhcPlus.INSTANCE.getDataFolder(), "statistics-configuration.yml");
		
		if(!file.exists()) {
			file.getParentFile().mkdirs();
			UhcPlus.INSTANCE.saveResource("statistics-configuration.yml", false);			
		}
		
		config = new YamlConfiguration();
		
		try {
			config.load(file);
			readConfig();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			UhcPlus.logWarn("Invalid statistics-configuration.yml! Exiting");
			Bukkit.getPluginManager().disablePlugin(UhcPlus.INSTANCE);
		}
	}
	
	public void readConfig() {
		ip = this.getConfig().getString("ip");
		port = this.getConfig().getString("port");
		name = this.getConfig().getString("name");
		timezone = this.getConfig().getString("timezone");
		username = this.getConfig().getString("username");
		password = this.getConfig().getString("password");
		
		
	}
}
