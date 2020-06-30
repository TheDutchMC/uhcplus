package nl.thedutchmc.uhcplus.modules;

import org.bukkit.Material;

public class BlockChecker {

	public static boolean isLog(Material material) {
		
		if(material.equals(Material.OAK_LOG) ||
				material.equals(Material.BIRCH_LOG) ||
				material.equals(Material.SPRUCE_LOG) || 
				material.equals(Material.ACACIA_LOG) || 
				material.equals(Material.JUNGLE_LOG) || 
				material.equals(Material.DARK_OAK_LOG)) {
			
			return true;
		}
		
		return false;
		
	}
	public static Boolean isLeave(Material material) {
		
		if(material.equals(Material.OAK_LEAVES) ||
				material.equals(Material.BIRCH_LEAVES) ||
				material.equals(Material.SPRUCE_LEAVES) ||
				material.equals(Material.ACACIA_LEAVES) ||
				material.equals(Material.JUNGLE_LEAVES) ||
				material.equals(Material.DARK_OAK_LEAVES)) {
			return true;
		}
		
		return false;
	}
}
