package nl.thedutchmc.uhcplus.modules.moduleAntiCheat;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.modules.moduleAntiCheat.listeners.BlockBreakEventListener;

public class ModuleAntiCheat {

	public static HashMap<UUID, Date> timeSinceLastDiamond = new HashMap<>();
	
	private static BlockBreakEventListener blockBreakEventListener;
	
	private UhcPlus plugin;
	
	public ModuleAntiCheat(UhcPlus plugin) {
		this.plugin = plugin;
	}
	
	public void enableModule() {
		
		blockBreakEventListener = new BlockBreakEventListener();
		
		Bukkit.getPluginManager().registerEvents(blockBreakEventListener, plugin);
		
	}
	
	public void disableModule() {
		
		HandlerList.unregisterAll(blockBreakEventListener);
		
	}
	
}
