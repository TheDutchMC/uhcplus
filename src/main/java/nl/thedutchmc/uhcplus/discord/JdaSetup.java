package nl.thedutchmc.uhcplus.discord;

import javax.security.auth.login.LoginException;

import org.bukkit.Bukkit;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.JDA.Status;

@SuppressWarnings("deprecation")
public class JdaSetup {

	static boolean jdaConnected;
	
	private static JDA jda;
	
	public static JDA getJda() {
		return jda;
	}
	
	public void setupJda() {
		try {
			jda = new JDABuilder(DiscordConfigurationHandler.token).build();
			
			boolean settingUp = true;
			while(settingUp) {
				if(jda.awaitStatus(Status.CONNECTED) != null) {
					settingUp = false;
					jdaConnected = true;
					System.out.println("[UhcPlus] Connected to Discord");
				}
			}
		} catch(LoginException e) {
			e.printStackTrace();
		} catch(InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	public static boolean enoughVoiceChannels() {
		return (DiscordConfigurationHandler.voiceChannelIds.size() >= (Bukkit.getServer().getOnlinePlayers().size() / 2)) ? true : false;
	}
}