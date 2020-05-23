package nl.thedutchmc.uhcplus.discord;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.JDA.Status;

@SuppressWarnings("deprecation")
public class JdaSetup {

	static boolean jdaConnected;
	
	private JDA jda;
	
	public JDA getJda() {
		return jda;
	}
	
	public void setupJda() {
		try {
			jda = new JDABuilder(AccountType.BOT)
					.setToken(DiscordConfigurationHandler.token)
					.build();
			
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
}
