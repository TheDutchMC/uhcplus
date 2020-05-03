package nl.thedutchmc.uhcplus.uhc;

import org.bukkit.Bukkit;
import org.bukkit.WorldBorder;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.teams.TeamHandler;

public class ScoreboardHandler {

	private UhcPlus plugin;
	
	public Scoreboard getInformationScoreboard() {
		
		ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
		
		Scoreboard informationScoreboard = scoreboardManager.getNewScoreboard();
		
		@SuppressWarnings("deprecation")
		Objective informationObjective = informationScoreboard.registerNewObjective("infObj", "dummy");
		
		informationObjective.setDisplayName("=== UHC ===");
		informationObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		/* What the scoreboard should look like:
		 === UHC ===
		 
		 World Border: (x) , (z) 
		 
		 Teams alive: (alive teams)
		 
		 Players alive in your team: (players alive)
		 
		 ============
		
		 */

		updateInformationScoreboard(informationObjective);
		
		return informationScoreboard;
	}
	
	public void updateInformationScoreboard(Objective objective) {
		
		WorldBorder worldborder = Bukkit.getWorld("uhcworld").getWorldBorder();
		int worldborderCoord = (int) worldborder.getSize() / 2;
		
		TeamHandler teamHandler = new TeamHandler(plugin, null, false);
		
		Score line1 = objective.getScore("");
		Score line2 = objective.getScore(ChatColor.GOLD + "World border: " + ChatColor.RED + worldborderCoord + " " + -worldborderCoord);
		Score line3 = objective.getScore("");
		Score line4 = objective.getScore(ChatColor.GOLD + "Teams alive: " + ChatColor.RED + teamHandler.getAliveTeams().size());
		
		line1.setScore(3);
		line2.setScore(2);
		line3.setScore(1);
		line4.setScore(0);
	}
}
