package nl.thedutchmc.uhcplus.uhc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.WorldBorder;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.teams.TeamHandler;

public class ScoreboardHandler {

	static String previousWorldborder, previousTeamsAlive, previousTimeRemaining;

	public Objective getInformationObjective() {

		Objective informationObjective = UhcPlus.scoreboard.registerNewObjective("infObj", "dummy",
				ChatColor.GOLD + "=== " + ChatColor.AQUA + "UHC " + ChatColor.GOLD + "===");

		informationObjective.setDisplaySlot(DisplaySlot.SIDEBAR);

		updateInformationScoreboard(informationObjective);

		return informationObjective;
	}

	public void updateInformationScoreboard(Objective objective) {

		WorldBorder worldborder = Bukkit.getWorld("uhcworld").getWorldBorder();
		int worldborderCoord = (int) worldborder.getSize() / 2;

		if (previousWorldborder != null && previousTeamsAlive != null) {
			UhcPlus.scoreboard.resetScores(previousWorldborder);
			UhcPlus.scoreboard.resetScores("");
			UhcPlus.scoreboard.resetScores(previousTeamsAlive);
			UhcPlus.scoreboard.resetScores(previousTimeRemaining);
		}

		int seconds = UhcTimeRemainingCalculator.getTimeRemaining();

		previousWorldborder = ChatColor.GOLD + "World border: " + ChatColor.RED + worldborderCoord + " "
				+ -worldborderCoord;
		previousTeamsAlive = ChatColor.GOLD + "Teams alive: " + ChatColor.RED + TeamHandler.getAliveTeams().size();
		previousTimeRemaining = ChatColor.GOLD + "Time remaining: " + ChatColor.RED
				+ String.format("%02d:%02d:%02d", seconds / 60 / 60, seconds / 60, seconds % 60);

		Score line0 = objective.getScore("");
		Score line1 = objective.getScore(previousTimeRemaining);
		Score line2 = objective.getScore(" ");
		Score line3 = objective.getScore(previousWorldborder);
		Score line4 = objective.getScore("");
		Score line5 = objective.getScore(previousTeamsAlive);

		line0.setScore(5);
		line1.setScore(4);
		line2.setScore(3);
		line3.setScore(2);
		line4.setScore(1);
		line5.setScore(0);

	}
}
