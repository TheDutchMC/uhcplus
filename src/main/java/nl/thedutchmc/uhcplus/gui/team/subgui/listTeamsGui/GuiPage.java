package nl.thedutchmc.uhcplus.gui.team.subgui.listTeamsGui;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.gui.CreateItem;
import nl.thedutchmc.uhcplus.teams.Team;
import nl.thedutchmc.uhcplus.teams.TeamHandler;

public class GuiPage {
	private Inventory gui;
	private int pageId;
	private int guiSize;
	private boolean nextPage = false;
	
	public GuiPage(int pageId) {
		this.pageId = pageId;
		
		setupGui();
		setupItems();
	}
	
	public int getPageId() {
		return pageId;
	}
	
	public Inventory getGui() {
		return gui;
	}
	
	void setupGui() {
		
		gui.clear();
		
		//Base size, what the minimum size should be
		int baseSize = ((TeamHandler.teams.size()) - (51* pageId)) + 5;
				
		//Gui size has to be a multiple of 9, so calculate the next multiple of 9 based on the base size
		guiSize = baseSize % 9 == 0 ? baseSize : baseSize + (9 - (baseSize % 9));
				
		//Gui cant be larger than 54 slots (6 rows of 9), so we add a new page and set gui size to 54
		if(guiSize > 54) {
			nextPage = true;
			guiSize = 54;
			ListTeamsGui.guiPages.put(pageId +1, new GuiPage(pageId + 1));
		}
		
		//Create the gui
		gui = Bukkit.createInventory(null, guiSize, "Teams: page " + (pageId + 1));
	}
	
	void setupItems() {
		
		//Loop over the Teams that need to be on this page
		int j = 0;
		for(int i = (51 * pageId); i < TeamHandler.teams.size() && j < 51; i++, j++) {
			
			Team team = TeamHandler.teams.get(i);
			
			//Instructions for the player
			List<String> lore = new ArrayList<>();
			lore.add("Click to join team");
			
			//Add each member of the team as lore
			for(UUID uuid : team.getTeamMembers()) {
				lore.add(ChatColor.GRAY + "- " + Bukkit.getPlayer(uuid).getName());
			}
			
			//Add the item to the inventory
			gui.setItem(j, ListTeamsGui.createItems(Material.PAPER, ChatColor.RESET + "" + team.getTeamColor() + "Team " + i, lore));
			
		}
		
		//Calculate the position of the back button based on if there's a next page, and add the item to the inventory
		int positionBackButton = nextPage ? guiSize - 3 : guiSize - 2;
		gui.setItem(positionBackButton, CreateItem.create(Material.WHITE_STAINED_GLASS_PANE, ChatColor.RESET + "Previous page"));
		
		//Button to go the next page, if there is one
		if(nextPage) gui.setItem(guiSize - 2, CreateItem.create(Material.BLACK_STAINED_GLASS_PANE, ChatColor.RESET + "Next page"));
		
		//Add the button to go back to the main menu
		gui.setItem(guiSize - 1, CreateItem.create(Material.BARRIER, ChatColor.RESET + "Main menu"));
	}
	
	public void openGui(HumanEntity ent) {
		//refresh the items on the page before opening it
		setupItems();
		ent.openInventory(gui);
	}	
}
