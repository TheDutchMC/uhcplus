package nl.thedutchmc.uhcplus.modules.moduleClasses;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

public class ModuleTeamInventory {

	private Inventory gui;

	public void setupGui() {
		gui = Bukkit.createInventory(null, 27, "Team Inventory");
	}

	public Inventory getGui() {
		return gui;
	}

	public void openGui(HumanEntity ent) {
		ent.openInventory(gui);
	}

}
