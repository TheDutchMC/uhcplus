package nl.thedutchmc.uhcplus.modules.modules.moduleStatistics.listeners;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import nl.thedutchmc.uhcplus.modules.modules.moduleStatistics.Database;
import nl.thedutchmc.uhcplus.UhcPlus;

public class PlayerDeathEventListener implements Listener {

	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent event) {
		
		if(!(event.getEntity().getKiller() instanceof Player)) return;
		
		UUID killer = event.getEntity().getKiller().getUniqueId();
		UUID killed = event.getEntity().getUniqueId();
		
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				try {
					Statement pullS = Database.getConnection().createStatement();
					ResultSet rs = pullS.executeQuery("SELECT uuid FROM playerKills");
					
					List<String> dbUuids = new ArrayList<>();
					while(rs.next()) {
						dbUuids.add(rs.getString(1));
					}
					
					if(dbUuids.contains(killer.toString())) {
						Statement s = Database.getConnection().createStatement();
						s.execute("UPDATE playerKills SET kills = (kills + 1) WHERE uuid = \"" + killer.toString() + "\"");
					} else {
						Statement s = Database.getConnection().createStatement();
						s.execute("INSERT INTO playerKills (uuid, kills) VALUES (\"" + killer.toString() + "\", 1)");
					}

					Statement pullS1 = Database.getConnection().createStatement();
					ResultSet rs1 = pullS1.executeQuery("SELECT uuid FROM playerTimesDied");
					
					dbUuids.clear();
					while(rs1.next()) {
						dbUuids.add(rs.getString(1));
					}
					
					if(dbUuids.contains(killed.toString())) {
						Statement s = Database.getConnection().createStatement();
						s.execute("UPDATE playerTimesDied SET death = (death + 1) WHERE uuid = \"" + killed.toString() + "\"");
					} else {
						Statement s = Database.getConnection().createStatement();
						s.execute("INSERT INTO playerTimesDied (uuid, death) VALUES (\"" + killed.toString() + "\", 1)");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}.runTaskAsynchronously(UhcPlus.INSTANCE);
		
		
		
		
	}
}
