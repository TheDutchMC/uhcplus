package nl.thedutchmc.uhcplus.commands;

import java.util.Map;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nl.thedutchmc.uhcplus.players.PlayerHandler;
import nl.thedutchmc.uhcplus.players.PlayerObject;

public class ChatCommandHandler implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		Player p = (Player) sender;

		if(!p.getGameMode().equals(GameMode.SURVIVAL)) {
			sender.sendMessage(ChatColor.RED + "This command may only be used by playing players!");
			return true;
		}
		
		//chat, to toggle between team and global chat
		for(Map.Entry<UUID, PlayerObject> entry : PlayerHandler.playerObjects.entrySet()) {
			PlayerObject playerObject = entry.getValue();
			
			if(playerObject.getPlayerUuid().equals(p.getUniqueId())) {
				
				playerObject.setTeamChatEnabled(!playerObject.getTeamChatEnabled());
				sender.sendMessage(ChatColor.GOLD + "Chat toggled to " + ChatColor.RED + (playerObject.getTeamChatEnabled() ? "team" : "global") + ChatColor.GOLD +  " chat!");
			}	
		}	

		return true;
		
	}
}
