package nl.thedutchmc.uhcplus.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import nl.thedutchmc.uhcplus.ConfigurationHandler;
import nl.thedutchmc.uhcplus.UhcPlus;
import nl.thedutchmc.uhcplus.presetHandler.PresetHandler;

public class CommandHandler implements CommandExecutor {

	private UhcPlus plugin;

	public CommandHandler(UhcPlus plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		ChatColor cr = ChatColor.RED;
		ChatColor cg = ChatColor.GOLD;
		ChatColor cw = ChatColor.WHITE;
		
		//Check if the entered command is equal to uhcp, because this is our base command we dont need to handle anything else
		if(command.getName().equalsIgnoreCase("uhcp")) {
			
			//Check if the player gave any arguments ('subcommands'), if none are given, they just entered /uhcp, show them how to get the help page.
			if(args.length == 0) {
				sender.sendMessage(cr + "Invalid usage! See /uhcp help for help.");
			} else {
				
				if(args[0].equalsIgnoreCase("help")) { //Check if args[0] (the first 'subcommand') is help, if so send the player the help page

					sender.sendMessage(cg + "UHCPlus Help Page");
					sender.sendMessage(cg + "---------------");
					sender.sendMessage("- " + cg + "/uhcp " + cw + "UHCPlus base command.");
					sender.sendMessage("- " + cg + "/uhcp help " + cw + "Shows this page.");
					sender.sendMessage("- " + cg + "/uhcp version " + cw + "Shows you the version of UHCPlus you are on.");
					sender.sendMessage("- " + cg + "/uhcp preset " + cw + "Manage the presets.");
					
					return true;
					
				} else if(args[0].equalsIgnoreCase("version")) { //Returns the version to the sender
					
					sender.sendMessage(cg + "You are on UHCPlus version " + cr + UhcPlus.VERSION + cg + ".");
					return true;
					
				} else if(args[0].equalsIgnoreCase("preset")) {
					
					if(args.length < 2) { //check if the args length is less than 2, if this is the case the user has entered /uhcp preset, and thus not a full command, refer them to the preset help page 
 						
						sender.sendMessage(cr + "Invalid usage! See /uhcp preset help for help.");
						
						return true;
						
					} else { //Args length is more than or equal to 2, meaning they have entered /uhcp preset <subcommand>
						
						if(args[1].equalsIgnoreCase("help")) {
							
							sender.sendMessage(cg + "UHCPlus Preset Help Page");
							sender.sendMessage(cg + "---------------------");
							sender.sendMessage("- " + cg + "/uhcp preset create <preset name> " + cw + "Create a new preset with the specified name.");
							sender.sendMessage("- " + cg + "/uhcp preset list " + cw + "List all available presets.");
							sender.sendMessage("- " + cg + "/uhcp preset setdefault <preset name> " + cw + "Set the preset that should be loaded after a server restart. This will NOT load the preset right now!");
							sender.sendMessage("- " + cg + "/uhcp preset load <preset name> " + cw + "Load the specified preset.");
							sender.sendMessage("- " + cg + "/uhcp preset seeloaded " + cw + "Shows which preset is curretly loaded");
							sender.sendMessage("- " + cg + "/uhcp preset delete <preset name> " + cw + "Deletes the specified preset. Warning: This action cannot be undone!");
							sender.sendMessage("- " + cg + "/uchp preset options list " + cw + "Lists the available options you can modify.");
							sender.sendMessage("- " + cg + "/uhcp preset options <option> <value> " + cw + "Modifies the option to the set value. This will modify the currently active preset!");
							
							return true;
							
						} else if(args[1].equalsIgnoreCase("create")) {
							
							if(args.length != 3) { //Check if the args length is not equal to 3, if this is the case the user hasn't specified a name for the preset, inform them about this.
								
								sender.sendMessage(cr + "You need to specify what you want the new preset to be named. Usage: /uhcp preset create <preset name>");
								
								return true;
									
							} else { //The args length is equal to 3, meaning they have entered /uhcp preset create <preset name>
								
								PresetHandler presetHandler = new PresetHandler(plugin);
								presetHandler.createPreset(args[2])
								;
								sender.sendMessage(cg + "Created a new preset with the name " + cr + args[2] + cg + ".");
								
								return true;
							}
						} else if (args[1].equalsIgnoreCase("list" )) {
							
							ConfigurationHandler configurationHandler = new ConfigurationHandler(plugin);
							
							configurationHandler.loadConfig();
							
							List<String> availablePresets = ConfigurationHandler.availablePresets;
							
							sender.sendMessage(cg + "Available presets:");
							
							for(String str : availablePresets) {
								sender.sendMessage(cg + "- " + str);
							}
							
							return true;
							
						} else if(args[1].equalsIgnoreCase("setdefault")) {
							
							
							boolean presetExists = doesPresetExist(args[2]);
							
							ConfigurationHandler configurationHandler = new ConfigurationHandler(plugin);
							
							if(presetExists) {
								configurationHandler.setDefaultPreset(args[2]);
								
								sender.sendMessage(cg + "Set the default preset to " + cr + args[2] + cg + ".");
							} else {
								sender.sendMessage(cr + "This preset does not exist!");
							}
							
							return true;

						} else if(args[1].equalsIgnoreCase("load")) {
								if(args.length != 3) { //Check if the args length is not equal to 3, if this is the case the user hasn't specified a name for the preset, inform them about this.

									sender.sendMessage(cr + "You need to specify which preset you want to load. Usage: /uhcp preset load <preset name>");
								
								return true;
									
							} else { //The args length is equal to 3, meaning they have entered /uhcp preset load <preset name>
								
								boolean presetExists = doesPresetExist(args[2]);
								
								PresetHandler presetHandler = new PresetHandler(plugin);

								if(presetExists) {
									presetHandler.loadPreset(args[2]);
									sender.sendMessage(cg + "Loaded the preset " + cr + args[2] + cg + ".");
								} else {
									sender.sendMessage(cr + "This preset does not exist!");
								}
								
								return true;
							}
						} else if(args[1].equalsIgnoreCase("seeloaded")) {
							
							sender.sendMessage(cg + "The currently loaded preset is " + cr + PresetHandler.loadedPreset);
							
							return true;
							
						} else if(args[1].equalsIgnoreCase("delete")) {
							
							if(args.length != 3) { //Check if the args length is not equal to 3, if this is the case the user hasn't specified a name for the preset, inform them about this.

								sender.sendMessage(cr + "You need to specify which preset you want to delete. Usage: /uhcp preset delete <preset name>");
							
							return true;
								
							} else { //The args length is equal to 3, meaning they have entered /uhcp preset delete <preset name>
								
								boolean presetExists = doesPresetExist(args[2]);
								
								if(presetExists) {
									
									PresetHandler presetHandler = new PresetHandler(plugin);
									presetHandler.removePreset(args[2]);
									
									sender.sendMessage(cg + "The preset " + cr + args[2] + cg + " has been deleted!");
									
								} else {
									sender.sendMessage(cr + "This preset does not exist!");
								}
							
								return true;
							}
						} else if(args[1].equalsIgnoreCase("options")) { //uhcp preset options
							
							if(!(args.length >= 3)) { //Check if the args length is not equal than or more than 3, this means the user has entered /uhcp preset options, and hasn't given an option
								sender.sendMessage(cr + "You need to specifiy which option you want to modify! See /uhcp preset options list, for a list of available options!");
								return true;
							} else {
								
								if(args[2].equalsIgnoreCase("list")) { //uchp preset options list
									
									sender.sendMessage(cg + "UHCPlus Preset Options");
									sender.sendMessage(cg + "--------------------");
									sender.sendMessage("- " + cg + "maxteamcount " + cw + "Sets the maximum number of teams. Range: 1-16");
									sender.sendMessage("- " + cg + "maxplayersperteam " + cw + "Sets the maximum amount of players per team. Range: 1-infinite");
									sender.sendMessage("- " + cg + "moduleoreautosmelt " + cw + "Enable or disable the automatic smelting of ores. Options: true/false");
									sender.sendMessage("- " + cg + "ingotdropcount " + cw + "Sets the amount of ingots that will drop from an ore. moduleoreautosmelt needs to be enabled for this. Range: 1-infinite");
									
									return true;
									
								} else {
									
									if(args.length != 4) { //check if args length is not equal to 4, meaning the user didnt /uhcp preset options <option> <value>, inform them.
										sender.sendMessage(cr + "Missing arguments! Usage: /uhcp preset options <option> <value>");
										
										return true;
									} else {
										
										if(args[2].equalsIgnoreCase("maxteamcount")) { // /uhcp preset option maxteamcount <value>
											
											if(isNumber(args[3])) {
												int teamCount = Integer.valueOf(args[3]);
												
												if(teamCount <= 16 && teamCount >= 1) {
												
													PresetHandler.maxTeamCount = args[3];
													
													PresetHandler presetHandler = new PresetHandler(plugin);
													presetHandler.changePresetOption();

													sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr + args[3] + cg + "!");
													
													return true;
													
												} else {
													sender.sendMessage(cr + "Out of range! Range: 1-16");
													
													return true;
												}
											} else {
												sender.sendMessage(cr + "Invalid value! Must be a number and in range 1-16!");
												return true;
											}
										} else if(args[2].equalsIgnoreCase("maxplayersperteam")) { // /uhcp preset option maxplayersperteam <value>
											
											if(isNumber(args[3])) {
												if(Integer.valueOf(args[3]) >= 1) {
													
													PresetHandler.maxPlayerCountPerTeam = args[3];
													PresetHandler presetHandler = new PresetHandler(plugin);
													presetHandler.changePresetOption();
													
													sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr + args[3] + cg + "!");
													
													return true;
													
												} else {
													sender.sendMessage(cr + "Value may not be negative or zero!");
													return true;
												}
																							
											} else {
												sender.sendMessage(cr + "Invalid value! Must be a number!");
												return true;
											}
										} else if(args[2].equalsIgnoreCase("moduleoreautosmelt")) {
											if(args[3].equalsIgnoreCase("true") || args[3].equalsIgnoreCase("false")) {
												PresetHandler.moduleOreAutoSmelt = Boolean.valueOf(args[3]);
												PresetHandler presetHandler = new PresetHandler(plugin);
												presetHandler.changePresetOption();
												
												sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr + args[3] + cg + "!");
												
												return true;
											} else {
												sender.sendMessage(cr + "Invalid value! Options: true/false");
											}
										} else if(args[2].equalsIgnoreCase("ingotdropcount")) { // /uhcp preset option ingotdropcount <value>
											if(isNumber(args[3])) {
												if(Integer.valueOf(args[3]) >= 1) {
													PresetHandler.moduleOreAutoSmeltIngotDrop = Integer.valueOf(args[3]);
													
													PresetHandler presetHandler = new PresetHandler(plugin);
													presetHandler.changePresetOption();
													
													sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr + args[3] + cg + "!");
													
													return true;
												} else {
													sender.sendMessage(cr + "Value may not be negative or zero!");
													return true;
												}
											} else {
												sender.sendMessage(cr + "Invalid value! Must be a number!");
												return true;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	//Iterate over all the available presets, to check if the one the user wants to use exists
	boolean doesPresetExist(String presetName) {
		List<String> availablePresets = ConfigurationHandler.availablePresets;
		
		return availablePresets.contains(presetName);
	}
	
	//Check if the supplied String is a round number
	boolean isNumber(String value) {
		try {
			Integer.valueOf(value);
			
			return true;
		} catch(NumberFormatException e) {
			
			return false;
		}
		
	}
}
