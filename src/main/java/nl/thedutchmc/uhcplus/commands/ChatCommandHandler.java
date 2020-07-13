package nl.thedutchmc.uhcplus.commands;

import java.util.Map;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nl.thedutchmc.uhcplus.players.PlayerHandler;
import nl.thedutchmc.uhcplus.players.PlayerObject;

public class ChatCommandHandler implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		//chat, to toggle between team and global chat
		if(command.getName().equalsIgnoreCase("chat")) {
			
			Player p = (Player) sender;
			
			for(Map.Entry<UUID, PlayerObject> entry : PlayerHandler.playerObjects.entrySet()) {
				PlayerObject playerObject = entry.getValue();
				
				if(playerObject.getPlayerUuid().equals(p.getUniqueId())) {
					
					playerObject.setTeamChatEnabled(!playerObject.getTeamChatEnabled());
					sender.sendMessage(ChatColor.GOLD + "Chat toggled to " + ChatColor.RED + (playerObject.getTeamChatEnabled() ? "team" : "global") + ChatColor.GOLD +  " chat!");
				}	
			}	
		}

		return true;
		
	}
}
