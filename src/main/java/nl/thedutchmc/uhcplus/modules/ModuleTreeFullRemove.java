package nl.thedutchmc.uhcplus.modules;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class ModuleTreeFullRemove implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
				
		if(isLog(block.getType())) {
			breakTree(block, 2);
		}
	}
	
	
	private void breakTree(Block block, int i) {
		if (isLog(block.getType())) {
			block.breakNaturally();
			i = 2;
		}else {
			i--;
		}
		
		if (i > 0) {
			for (BlockFace blockFace : BlockFace.values()) {
				if (blockFace.equals(BlockFace.DOWN) || 
						blockFace.equals(BlockFace.UP) || 
						blockFace.equals(BlockFace.NORTH) || 
						blockFace.equals(BlockFace.EAST) || 
						blockFace.equals(BlockFace.SOUTH) || 
						blockFace.equals(BlockFace.WEST)) {
					
					breakTree(block.getRelative(blockFace), i);
				}
			}
		}
	}
	
	private boolean isLog(Material material) {
		
		if(material.equals(Material.OAK_LOG) || 
				material.equals(Material.SPRUCE_LOG) || 
				material.equals(Material.ACACIA_LOG) || 
				material.equals(Material.JUNGLE_LOG) || 
				material.equals(Material.DARK_OAK_LOG)) {
			
			return true;
		}
		
		return false;
		
	}
}
