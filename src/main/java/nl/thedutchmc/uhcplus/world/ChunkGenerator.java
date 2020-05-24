package nl.thedutchmc.uhcplus.world;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import io.papermc.lib.PaperLib;
import net.dv8tion.jda.api.entities.Guild;
import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.discord.DiscordConfigurationHandler;
import nl.thedutchmc.uhcplus.discord.JdaSetup;

public class ChunkGenerator {

	private UhcPlus plugin;
	
	public ChunkGenerator(UhcPlus plugin) {
		this.plugin = plugin;
	}

	static World overworld;
	static int x = 700;
	static int z;
	static int cIndex = 0;
	static Chunk chunk = null;
	static int chunksDone = 0;
	static int checkEvery;
	static int checkEveryOriginal;
	static int progress = 0;

	public void generateChunks() {
		
		int worldRadius = 700;
		
		int startingCoordZ = worldRadius;
		
		z = -startingCoordZ;		
		
		overworld = Bukkit.getServer().getWorld("uhcworld");
				
		//Calculate the amount of chunks
		double chunkCount = Math.pow((worldRadius/8), 2);
		checkEvery = (int) chunkCount / 20;
		checkEveryOriginal = checkEvery;
		
		//Generate the chunks 
		new BukkitRunnable() {

			@Override
			public void run() {	
				
				if(!(Runtime.getRuntime().freeMemory() < 524288000)) { //In Bytes, so 500MB
					if(x > -worldRadius) {
						
						//System.out.println("chunk: " + x + "," + z);
						
						Location location = new Location(overworld, x, 0, z);
						PaperLib.getChunkAtAsync(location, true);
			
						chunk = location.getChunk();
											
						if(z > worldRadius) {
							x -= 16;
							z = -startingCoordZ;
						}
												
						cIndex++;
						chunksDone++;
						
						if(cIndex == 100) {
							overworld.save();
							cIndex = 0;
						}
						
						if(chunksDone == checkEvery) {
							progress += 5;
							checkEvery += checkEveryOriginal;
							System.out.println("[UhcPlus] Chunk generation progress: " + progress + "%");
						}
							
						if(chunk != null) {
							overworld.unloadChunkRequest(x, z);
						}
						
						z += 16;
					} else {
						System.out.println("[UhcPlus] Done. Players may now join.");
						UhcPlus.PLAYER_CAN_JOIN = true;
						
						this.cancel();
					}
				} else {
					System.gc();
				}
			}
		}.runTaskTimer(plugin, 200, 1);
	}

}
