package nl.thedutchmc.uhcplus.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.presets.PresetHandler;
import nl.thedutchmc.uhcplus.teams.Team;
import nl.thedutchmc.uhcplus.teams.TeamHandler;

public class ReviveCommandHandler implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(!PresetHandler.moduleRevive) {
			sender.sendMessage(ChatColor.RED + "This module is not enabled!");
			return true;
		}
		
		//If the sender isn't a player, they cannot use this command
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only Players may use this command!");
			return true;
		}
		
		//If the sender is not in gamemode survival (meaning they are not participating) and they are not OP, they may not use this command!
		if(!((Player)sender).getGameMode().equals(GameMode.SURVIVAL) && !sender.hasPermission("uhcp.revive")) {
			sender.sendMessage(ChatColor.RED + "Only Players still playing may use this command!");
			return true;
		}
		
		Player pSender = (Player) sender;
		
		boolean hasToken = false;
		
		for(ItemStack i : pSender.getInventory().getStorageContents()) {
			
			if(i == null) continue;
			
			if(i.getType().equals(Material.NETHER_STAR)) {
				if(i.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Revive Token")) {
					hasToken = true;
				}
			}
		}
		
		if(!hasToken) {
			sender.sendMessage(ChatColor.RED + "You do not have the required revive token!");
			return true;
		}
		
		List<UUID> reviveCandidates = new ArrayList<>();
		
		for(Team team : TeamHandler.teams) {
			
			if(!team.getTeamMembers().contains(pSender.getUniqueId())) continue;
			
			for(UUID member : team.getTeamMembers()) {
				if(!Bukkit.getPlayer(member).getGameMode().equals(GameMode.SURVIVAL)) reviveCandidates.add(member);
			}
		}
				
		Player toRevive = getPlayerToRevive(reviveCandidates, 0);
				
 		if(toRevive == null) {
 			sender.sendMessage(ChatColor.RED + "None of your team members are online!");
 			return true;
 		}
		
 		Inventory inv = pSender.getInventory();
 		for(int i = 0; i < inv.getContents().length; i++) {
 			ItemStack content = inv.getContents()[i];
 			
 			if(content == null) continue;
 			
 			if(content.getType().equals(Material.NETHER_STAR)) {
				if(content.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Revive Token")) {
					inv.remove(content);
				}
 			}
 		}
 		
		TeamHandler.revivePlayer(toRevive.getUniqueId());
		
		sender.sendMessage(ChatColor.GRAY + "Revivng " + ChatColor.RED + toRevive.getName());
		
		return true;
	}
	
	@javax.annotation.Nullable
	Player getPlayerToRevive(List<UUID> candidates, int tries) {
		int candidateIndex = (candidates.size() == 1) ? 0 : ThreadLocalRandom.current().nextInt(0, candidates.size());
		
		Player revive = Bukkit.getPlayer(candidates.get(candidateIndex));

		if(tries > PresetHandler.maxPlayerCountPerTeam * 2) return null;
		
		if(!revive.isOnline()) getPlayerToRevive(candidates, tries++);
		
		return revive;
	}
}
