package nl.thedutchmc.uhcplus.modules.modules.moduleStatistics;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

import nl.thedutchmc.uhcplus.modules.modules.moduleStatistics.listeners.PlayerDeathEventListener;
import nl.thedutchmc.uhcplus.UhcPlus;

public class ModuleStatistics {

	private static Connection c;
	
	private static PlayerDeathEventListener playerDeathEventListener;
	
	
	public void loadModule() {
		
		StatisticsConfigurationHandler config = new StatisticsConfigurationHandler();
		config.loadConfig();
		
		Database db = new Database();
		db.connect();
		c = Database.getConnection();
		
		setupDatabase();
		
		//instantiate all the listener classes;
		playerDeathEventListener = new PlayerDeathEventListener();
		
		//Register all Listeners
		Bukkit.getPluginManager().registerEvents(playerDeathEventListener, UhcPlus.INSTANCE);
	}
	
	public void unloadModule() {
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Unregister all listeners
		//TODO unregister all listeners
		HandlerList.unregisterAll(playerDeathEventListener);
		
	}
	
	void setupDatabase() {
		
		boolean playerKillsExists = false, playerDamageDoneExists = false, playerTimesDiedExists = false, playerTimesWonExists = false;
		
		try {
			DatabaseMetaData dbmd = c.getMetaData();
			String[] types = {"TABLE"};
			ResultSet rs = dbmd.getTables(null, null, "%", types);
			while (rs.next()) {
				
				String tableName = rs.getString("TABLE_NAME");
				
				switch(tableName) {
				
				
				case "playerKills":
					playerKillsExists = true;
					break;
				case "playerDamageDone":
					playerDamageDoneExists = true;
					break;
				case "playerTimesDied":
					playerTimesDiedExists = true;
					break;
				case "playerTimesWon":
					playerTimesWonExists = true;
					break;
				}
			}
			
			if(!playerKillsExists) {
				Statement s = c.createStatement();
				s.execute("CREATE TABLE playerKills (uuid varchar(200), kills bigint);");
			}
			
			if(!playerDamageDoneExists) {
				Statement s = c.createStatement();
				s.execute("CREATE TABLE playerDamageDone (uuid varchar(200), damageDone bigint);");
			}
			
			if(!playerTimesDiedExists) {
				Statement s = c.createStatement();
				s.execute("CREATE TABLE playerTimesDied (uuid varchar(200), death bigint);"); 
			}
			
			if(!playerTimesWonExists) {
				Statement s = c.createStatement();
				s.execute("CREATE TABLE playerTimesWon (uuid varchar(200), won bigint);");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
