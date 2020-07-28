package nl.thedutchmc.uhcplus.modules.modules.moduleStatistics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import nl.thedutchmc.uhcplus.UhcPlus;

public class Database {

	private static Connection connection = null;
	
	public void connect() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String uri = "jdbc:mysql://" + StatisticsConfigurationHandler.ip
					+ ":" + StatisticsConfigurationHandler.port
					+ "/" + StatisticsConfigurationHandler.name
					+ "?&serverTimezone=" + StatisticsConfigurationHandler.timezone;
			
			connection = DriverManager.getConnection(uri, StatisticsConfigurationHandler.username, StatisticsConfigurationHandler.password);
		} catch (ClassNotFoundException e) {
			UhcPlus.logWarn("No MySQL Driver found! Please fix this!");
		} catch (SQLException e) {
			UhcPlus.logWarn("Unable to connect to the MySQL database! Please verify all the information in the configuration!");
		}
 	}
	
	public static Connection getConnection() {
		return connection;
	}
	
}
