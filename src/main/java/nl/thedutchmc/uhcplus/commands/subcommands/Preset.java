package nl.thedutchmc.uhcplus.commands.subcommands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import nl.thedutchmc.uhcplus.ConfigurationHandler;
import nl.thedutchmc.uhcplus.presets.PresetHandler;

public class Preset {

	public boolean presetSubcommand(CommandSender sender, Command command, String label, String[] args) {

		ChatColor cr = ChatColor.RED;
		ChatColor cg = ChatColor.GOLD;
		ChatColor cw = ChatColor.WHITE;

		if (args.length < 2) { // check if the args length is less than 2, if this is the case the user has
								// entered /uhcp preset, and thus not a full command, refer them to the preset
								// help page

			sender.sendMessage(cr + "Invalid usage! See /uhcp preset help for help.");

		} else { // Args length is more than or equal to 2, meaning they have entered /uhcp
					// preset <subcommand>

			// uhcp preset help
			if (args[1].equalsIgnoreCase("help")) {

				if (!sender.hasPermission("uhcp.preset.help")) {
					sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
					return true;
				}

				sender.sendMessage(cg + "UHCPlus Preset Help Page");
				sender.sendMessage(cg + "---------------------");
				sender.sendMessage("- " + cg + "/uhcp preset create <preset name> " + cw
						+ "Create a new preset with the specified name.");
				sender.sendMessage("- " + cg + "/uhcp preset list " + cw + "List all available presets.");
				sender.sendMessage("- " + cg + "/uhcp preset setdefault <preset name> " + cw
						+ "Set the preset that should be loaded after a server restart. This will NOT load the preset right now!");
				sender.sendMessage("- " + cg + "/uhcp preset load <preset name> " + cw + "Load the specified preset.");
				sender.sendMessage(
						"- " + cg + "/uhcp preset seeloaded " + cw + "Shows which preset is curretly loaded");
				sender.sendMessage("- " + cg + "/uhcp preset delete <preset name> " + cw
						+ "Deletes the specified preset. Warning: This action cannot be undone!");
				sender.sendMessage("- " + cg + "/uchp preset options list <page> " + cw
						+ "Lists the available options you can modify.");
				sender.sendMessage("- " + cg + "/uhcp preset options <option> <value> " + cw
						+ "Modifies the option to the set value. This will modify the currently active preset!");

				// uhcp preset create <arg>
			} else if (args[1].equalsIgnoreCase("create")) {

				if (!sender.hasPermission("uhcp.preset.create")) {
					sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
					return true;
				}

				if (args.length != 3) { // Check if the args length is not equal to 3, if this is the case the user
										// hasn't specified a name for the preset, inform them about this.

					sender.sendMessage(cr
							+ "You need to specify what you want the new preset to be named. Usage: /uhcp preset create <preset name>");

				} else { // The args length is equal to 3, meaning they have entered /uhcp preset create
							// <preset name>

					PresetHandler presetHandler = new PresetHandler();
					presetHandler.createPreset(args[2]);
					sender.sendMessage(cg + "Created a new preset with the name " + cr + args[2] + cg + ".");

				}

				// uhcp preset list
			} else if (args[1].equalsIgnoreCase("list")) {

				if (!sender.hasPermission("uhcp.preset.list")) {
					sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
					return true;
				}

				ConfigurationHandler configurationHandler = new ConfigurationHandler();

				configurationHandler.loadConfig();

				List<String> availablePresets = ConfigurationHandler.availablePresets;

				sender.sendMessage(cg + "Available presets:");

				for (String str : availablePresets) {
					sender.sendMessage(cg + "- " + str);
				}

				// uhcp preset setdefault <arg>
			} else if (args[1].equalsIgnoreCase("setdefault")) {

				if (!sender.hasPermission("uhcp.preset.setdefault")) {
					sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
					return true;
				}

				boolean presetExists = doesPresetExist(args[2]);

				ConfigurationHandler configurationHandler = new ConfigurationHandler();

				if (presetExists) {
					configurationHandler.setDefaultPreset(args[2]);

					sender.sendMessage(cg + "Set the default preset to " + cr + args[2] + cg + ".");
				} else {
					sender.sendMessage(cr + "This preset does not exist!");
				}

				// uhcp preset load <arg>
			} else if (args[1].equalsIgnoreCase("load")) {

				if (!sender.hasPermission("uhcp.preset.load")) {
					sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
					return true;
				}

				if (args.length != 3) { // Check if the args length is not equal to 3, if this is the case the user
										// hasn't specified a name for the preset, inform them about this.

					sender.sendMessage(cr
							+ "You need to specify which preset you want to load. Usage: /uhcp preset load <preset name>");

				} else { // The args length is equal to 3, meaning they have entered /uhcp preset load
							// <preset name>

					boolean presetExists = doesPresetExist(args[2]);

					PresetHandler presetHandler = new PresetHandler();

					if (presetExists) {
						presetHandler.loadPreset(args[2]);
						sender.sendMessage(cg + "Loaded the preset " + cr + args[2] + cg + ".");
					} else {
						sender.sendMessage(cr + "This preset does not exist!");
					}
				}

				// uhcp preset seeloaded
			} else if (args[1].equalsIgnoreCase("seeloaded")) {

				if (!sender.hasPermission("uhcp.preset.seeloaded")) {
					sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
					return true;
				}

				sender.sendMessage(cg + "The currently loaded preset is " + cr + PresetHandler.loadedPreset);

				// uhcp preset delete <arg>
			} else if (args[1].equalsIgnoreCase("delete")) {

				if (!sender.hasPermission("uhcp.preset.delete")) {
					sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
					return true;
				}

				if (args.length != 3) { // Check if the args length is not equal to 3, if this is the case the user
										// hasn't specified a name for the preset, inform them about this.

					sender.sendMessage(cr
							+ "You need to specify which preset you want to delete. Usage: /uhcp preset delete <preset name>");

				} else { // The args length is equal to 3, meaning they have entered /uhcp preset delete
							// <preset name>

					boolean presetExists = doesPresetExist(args[2]);

					if (presetExists) {
						PresetHandler presetHandler = new PresetHandler();
						presetHandler.removePreset(args[2]);

						sender.sendMessage(cg + "The preset " + cr + args[2] + cg + " has been deleted!");

					} else {
						sender.sendMessage(cr + "This preset does not exist!");
					}
				}

				// uhcp preset options <args>
			} else if (args[1].equalsIgnoreCase("options")) { // uhcp preset options

				if (!sender.hasPermission("uhcp.preset.options")) {
					sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
					return true;
				}

				// uhcp preset options <no arg>
				if (!(args.length >= 3)) { // Check if the args length is not equal than or more than 3, this means the
											// user has entered /uhcp preset options, and hasn't given an option
					sender.sendMessage(cr
							+ "You need to specifiy which option you want to modify! See /uhcp preset options list <page>, for a list of available options!");
				} else {

					// uhcp preset options list
					if (args[2].equalsIgnoreCase("list")) { // uchp preset options list

						// uhcp preset options list 2
						// options list page 2
						if (args.length >= 4 && args[3].equalsIgnoreCase("2")) {

							sender.sendMessage(cg + "UHCPlus Preset Options: page 2");
							sender.sendMessage(cg + "-----------------------------");
							sender.sendMessage("- " + cg + "ingotDropCount " + cw
									+ "Sets the amount of ingots that will drop from an ore. moduleoreautosmelt needs to be enabled for this. Range: 1-infinite");
							sender.sendMessage(
									"- " + cg + "timeToPvp " + cw + "Sets after how many minutes PVP will be enabled");
							sender.sendMessage("- " + cg + "worldBorderSize " + cw
									+ "The size of the world border at game start, in side length, For example: 1400 means the border will be at coordinate 700");
							sender.sendMessage("- " + cg + "worldBorderShrinkAfter " + cw
									+ "Time until the world border starts shrinking in minutes");
							sender.sendMessage("- " + cg + "worldBorderShrinkTo " + cw
									+ "The size of the world border after it has stopped shrinking, in side length");
							sender.sendMessage("- " + cg + "gameTime " + cw + "Game time in minutes");
							sender.sendMessage("- " + cg + "moduleAntiCheatTime " + cw
									+ "The time after which a player can safely mine diamond ore again without OPs being notified. In Seconds");
							sender.sendMessage("- " + cg + "moduleAxeOfDestructionLevelOneTime " + cw
									+ "Time in seconds until the Axe levels up to level 2 in seconds");
							sender.sendMessage("- " + cg + "moduleAxeOfDestructionLevelTwoTime " + cw
									+ "Time in seconds until the Axe levels up to level 3 in seconds");
							sender.sendMessage("- " + cg + "moduleSwordOfDivinityLevelOneTime " + cw
									+ "Time in seconds until the Sword levels up to level 2 in Seconds");
							sender.sendMessage("- " + cg + "moduleSwordOfDivinityLevelTwoTime " + cw
									+ "Time in seconds until the Sword levels up to level 3 in Seconds");

							// uhcp preset options list
							// preset options list page 1
						} else {
							sender.sendMessage(cg + "UHCPlus Preset Options: page 1");
							sender.sendMessage(cg + "-----------------------------");
							sender.sendMessage("- " + cg + "maxTeamCount " + cw
									+ "Sets the maximum number of teams. Range: 1-infinite");
							sender.sendMessage("- " + cg + "maxPlayersPerTeam " + cw
									+ "Sets the maximum amount of players per team. Range: 1-infinite");
							sender.sendMessage("- " + cg + "moduleOreAutoSmelt " + cw
									+ "Enable or disable the automatic smelting of ores. Options: true/false");
							sender.sendMessage("- " + cg + "moduleTreeFullRemove " + cw
									+ "This Module will remove the entire stem if one block is broken. Options: true/false");
							sender.sendMessage("- " + cg + "moduleLeaveDecay " + cw
									+ "This module will decay all leaves as soon as one decays. Options: true/false");
							sender.sendMessage("- " + cg + "moduleEnchantedTools " + cw
									+ "This module will turn all crafted tools into enchanted tools. Options: true/false");
							sender.sendMessage("- " + cg + "moduleInfiniteEnchanting " + cw
									+ "This module will give the player a stack of enchanting tables and anvils, and the maximum amount of levels. Options: true/false");
							sender.sendMessage("- " + cg + "moduleSheepDropString " + cw
									+ "This module will let sheep drop string instead of wool. Options: true/false");
							sender.sendMessage("- " + cg + "moduleGravelDropArrow " + cw
									+ "This module will let gravel drop arrows instead of the default drops. Options: true/false");
							sender.sendMessage("- " + cg + "moduleDissalowGrindingEnchantedTools " + cw
									+ "This module will prevent tools crafted with moduleEnchantedTools from being grinded for XP. Options: true/false");
							sender.sendMessage("- " + cg + "moduleLightGoldenApple " + cw
									+ "This module will add a light golden apple, crafted with four gold ingots instead of eight. Options: true/false");
							sender.sendMessage("- " + cg + "moduleLightAnvil " + cw
									+ "This module will add a light anvil, crafted with six iron ingots and one iron block. Options: true/false");
							sender.sendMessage("- " + cg + "moduleDioriteDamage " + cw
									+ "[WIP] This module will damage players if they have diorite in their inventory for too long. Options: true/false");
							sender.sendMessage("- " + cg + "moduleAntiCheat " + cw
									+ "This module will alert OPs if a player finds diamonds too quickly. Options: true/false");
							sender.sendMessage("- " + cg + "moduleAxeOfDestruction " + cw
									+ "This module will add the Axe of Destruction, crafted with two iron blocks and three iron ingots. Options: true/false");
							sender.sendMessage("- " + cg + "axeOfDestructionLevelling " + cw
									+ "Enabling this will allow the Axe of Destruction to level up. Options: true/false");
							sender.sendMessage("- " + cg + "moduleSwordOfDivinity " + cw
									+ "This module will add the Sword of Divinity, crafted with two iron blocks and one iron ingot. Options: true/false");
							sender.sendMessage("- " + cg + "swordOfDivinityLevelling " + cw
									+ "Enabling this will allow the Sword of Divinity to level up. Options: true/false");
							sender.sendMessage("- " + cg + "moduleTeamInventory " + cw
									+ "This module will add an inventory accessible by every member of a team. Options: true/false");
							sender.sendMessage("- " + cg + "moduleOneHeartStart " + cw
									+ "This module will set the player's health to 1 heart at start. Options: true/false");
							sender.sendMessage("- " + cg + "moduleSlimeBoost " + cw + "This module will give players a strength boost if they hold on to slime for a while");
							sender.sendMessage("- " + cg + "moduleStatistics" + cw + "This module will keep track of statistics to a MySql database");
						}
					} else {

						// uhcp preset options <arg> <no arg>
						if (args.length != 4) { // check if args length is not equal to 4, meaning the user didnt /uhcp
												// preset options <option> <value>, inform them.
							sender.sendMessage(cr + "Missing arguments! Usage: /uhcp preset options <option> <value>");

						} else {

							// uhcp preset options maxteamcount <arg>
							if (args[2].equalsIgnoreCase("maxTeamCount")) { // /uhcp preset option maxteamcount <value>

								if (isNumber(args[3]) && Integer.valueOf(args[3]) > 0) {

									PresetHandler.maxTeamCount = Integer.valueOf(args[3]);
									PresetHandler.changedPresetOption();

									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr
											+ args[3] + cg + "!");

								} else {
									sender.sendMessage(cr + "Invalid value! Must be a positive number!");
								}

								// uhcp preset options maxplayersperteam <arg>
							} else if (args[2].equalsIgnoreCase("maxplayersperteam")) { // /uhcp preset option
																						// maxplayersperteam <value>

								if (isNumber(args[3]) && Integer.valueOf(args[3]) > 0) {
									PresetHandler.maxPlayerCountPerTeam = Integer.valueOf(args[3]);
									PresetHandler.changedPresetOption();

									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr
											+ args[3] + cg + "!");

								} else {
									sender.sendMessage(cr + "Invalid value! Must be a positive number!");
								}

								// uhcp preset options moduleoreautosmelt <arg>
							} else if (args[2].equalsIgnoreCase("moduleoreautosmelt")) {
								if (args[3].equalsIgnoreCase("true") || args[3].equalsIgnoreCase("false")) {
									PresetHandler.moduleOreAutoSmelt = Boolean.valueOf(args[3]);
									PresetHandler.changedPresetOption();

									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr
											+ args[3] + cg + "!");

								} else {
									sender.sendMessage(cr + "Invalid value! Options: true/false");
								}

								// uhcp preset options ingotDropCount <arg>
							} else if (args[2].equalsIgnoreCase("ingotdropcount")) {
								if (isNumber(args[3]) && Integer.valueOf(args[3]) > 0) {
									PresetHandler.moduleOreAutoSmeltIngotDrop = Integer.valueOf(args[3]);
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr
											+ args[3] + cg + "!");

								} else {
									sender.sendMessage(cr + "Invalid value! Must be a positive number!");
								}

								// uhcp preset options moduleTreeFullRemove <arg>
							} else if (args[2].equalsIgnoreCase("moduletreefullremove")) {
								if (args[3].equalsIgnoreCase("true")) {
									PresetHandler.moduleTreeFullRemove = true;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr + "true"
											+ cg + "!");

								} else if (args[3].equalsIgnoreCase("false")) {
									PresetHandler.moduleTreeFullRemove = false;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr
											+ "false" + cg + "!");

								} else {
									sender.sendMessage(ChatColor.RED + "Invalid option! Either true or false.");
								}

								// uhcp preset options moduleLeaveDecay <arg>
							} else if (args[2].equalsIgnoreCase("moduleleavedecay")) {
								if (args[3].equalsIgnoreCase("true")) {
									PresetHandler.moduleLeaveDecay = true;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr + "true"
											+ cg + "!");

								} else if (args[3].equalsIgnoreCase("false")) {
									PresetHandler.moduleLeaveDecay = false;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr
											+ "false" + cg + "!");

								} else {
									sender.sendMessage(ChatColor.RED + "Invalid option! Either true or false.");
								}

								// uhcp preset options moduleEnchantedTools <arg>
							} else if (args[2].equalsIgnoreCase("moduleenchantedtools")) {
								if (args[3].equalsIgnoreCase("true")) {
									PresetHandler.moduleEnchantedTools = true;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr + "true"
											+ cg + "!");

								} else if (args[3].equalsIgnoreCase("false")) {
									PresetHandler.moduleEnchantedTools = false;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr
											+ "false" + cg + "!");

								} else {
									sender.sendMessage(ChatColor.RED + "Invalid option! Either true or false.");
								}

								// uhcp preset options moduleInfiniteEnchanting <arg>
							} else if (args[2].equalsIgnoreCase("moduleinfiniteenchanting")) {
								if (args[3].equalsIgnoreCase("true")) {
									PresetHandler.moduleInfiniteEnchanting = true;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr + "true"
											+ cg + "!");

								} else if (args[3].equalsIgnoreCase("false")) {
									PresetHandler.moduleInfiniteEnchanting = false;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr
											+ "false" + cg + "!");

								} else {
									sender.sendMessage(ChatColor.RED + "Invalid option! Either true or false.");
								}

								// uhcp preset options moduleSheepDropString <arg>
							} else if (args[2].equalsIgnoreCase("modulesheepdropstring")) {
								if (args[3].equalsIgnoreCase("true")) {
									PresetHandler.moduleSheepDropString = true;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr + "true"
											+ cg + "!");

								} else if (args[3].equalsIgnoreCase("false")) {
									PresetHandler.moduleSheepDropString = false;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr
											+ "false" + cg + "!");

								} else {
									sender.sendMessage(ChatColor.RED + "Invalid option! Either true or false.");
								}

								// uhcp preset options moduleGravelDropArrow <arg>
							} else if (args[2].equalsIgnoreCase("graveldroparrow")) {
								if (args[3].equalsIgnoreCase("true")) {
									PresetHandler.moduleGravelDropArrow = true;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr + "true"
											+ cg + "!");

								} else if (args[3].equalsIgnoreCase("false")) {
									PresetHandler.moduleGravelDropArrow = false;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr
											+ "false" + cg + "!");

								} else {
									sender.sendMessage(ChatColor.RED + "Invalid option! Either true or false.");
								}

								// uhcp preset options moduleDissalowGrindingEnchantedTools <arg>
							} else if (args[2].equalsIgnoreCase("moduledissalowgrindingenchantedtools")) {
								if (args[3].equalsIgnoreCase("true")) {
									PresetHandler.moduleDissalowGrindingEnchantedTools = true;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr + "true"
											+ cg + "!");

								} else if (args[3].equalsIgnoreCase("false")) {
									PresetHandler.moduleDissalowGrindingEnchantedTools = false;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr
											+ "false" + cg + "!");

								} else {
									sender.sendMessage(ChatColor.RED + "Invalid option! Either true or false.");
								}

								// uhcp preset options moduleLightGoldenApple <arg>
							} else if (args[2].equalsIgnoreCase("modulelightgoldenapple")) {
								if (args[3].equalsIgnoreCase("true")) {
									PresetHandler.moduleLightGoldenApple = true;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr + "true"
											+ cg + "!");

								} else if (args[3].equalsIgnoreCase("false")) {
									PresetHandler.moduleLightGoldenApple = false;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr
											+ "false" + cg + "!");

								} else {
									sender.sendMessage(ChatColor.RED + "Invalid option! Either true or false.");
								}

								// uhcp preset options moduleLightAnvil <arg>
							} else if (args[2].equalsIgnoreCase("modulelightanvil")) {
								if (args[3].equalsIgnoreCase("true")) {
									PresetHandler.moduleLightAnvil = true;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr + "true"
											+ cg + "!");

								} else if (args[3].equalsIgnoreCase("false")) {
									PresetHandler.moduleLightAnvil = false;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr
											+ "false" + cg + "!");

								} else {
									sender.sendMessage(ChatColor.RED + "Invalid option! Either true or false.");
								}

								// uhcp preset options moduleDioriteDamage <arg>
							} else if (args[2].equalsIgnoreCase("moduledioritedamage")) {
								if (args[3].equalsIgnoreCase("true")) {
									PresetHandler.moduleDioriteDamage = true;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr + "true"
											+ cg + "!");

								} else if (args[3].equalsIgnoreCase("false")) {
									PresetHandler.moduleDioriteDamage = false;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr
											+ "false" + cg + "!");

								} else {
									sender.sendMessage(ChatColor.RED + "Invalid option! Either true or false.");
								}

								// uhcp preset options moduleAntiCheat <arg>
							} else if (args[2].equalsIgnoreCase("moduleanticheat")) {
								if (args[3].equalsIgnoreCase("true")) {
									PresetHandler.moduleAntiCheat = true;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr + "true"
											+ cg + "!");

								} else if (args[3].equalsIgnoreCase("false")) {
									PresetHandler.moduleAntiCheat = false;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr
											+ "false" + cg + "!");

								} else {
									sender.sendMessage(ChatColor.RED + "Invalid option! Either true or false.");
								}

								// uhcp preset options moduleAxeOfDestruction <arg>
							} else if (args[2].equalsIgnoreCase("moduleaxeofdestruction")) {
								if (args[3].equalsIgnoreCase("true")) {
									PresetHandler.moduleAxeOfDestruction = true;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr + "true"
											+ cg + "!");

								} else if (args[3].equalsIgnoreCase("false")) {
									PresetHandler.moduleAxeOfDestruction = false;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr
											+ "false" + cg + "!");

								} else {
									sender.sendMessage(ChatColor.RED + "Invalid option! Either true or false.");
								}

								// uhcp preset options axeOfDestructionLevelling <arg>
							} else if (args[2].equalsIgnoreCase("axeofdestructionlevelling")) {
								if (args[3].equalsIgnoreCase("true")) {
									PresetHandler.axeOfDestructionLevelling = true;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr + "true"
											+ cg + "!");

								} else if (args[3].equalsIgnoreCase("false")) {
									PresetHandler.axeOfDestructionLevelling = false;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr
											+ "false" + cg + "!");

								} else {
									sender.sendMessage(ChatColor.RED + "Invalid option! Either true or false.");
								}

								// uhcp preset options moduleSwordOfDivinity <arg>
							} else if (args[2].equalsIgnoreCase("moduleswordofdivinity")) {
								if (args[3].equalsIgnoreCase("true")) {
									PresetHandler.moduleSwordOfDivinity = true;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr + "true"
											+ cg + "!");

								} else if (args[3].equalsIgnoreCase("false")) {
									PresetHandler.moduleSwordOfDivinity = false;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr
											+ "false" + cg + "!");

								} else {
									sender.sendMessage(ChatColor.RED + "Invalid option! Either true or false.");
								}

								// uhcp preset options swordOfDivinityLevelling <arg>
							} else if (args[2].equalsIgnoreCase("swordofdivinitylevelling")) {
								if (args[3].equalsIgnoreCase("true")) {
									PresetHandler.swordOfDivinityLevelling = true;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr + "true"
											+ cg + "!");

								} else if (args[3].equalsIgnoreCase("false")) {
									PresetHandler.swordOfDivinityLevelling = false;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr
											+ "false" + cg + "!");

								} else {
									sender.sendMessage(ChatColor.RED + "Invalid option! Either true or false.");
								}

								// uhcp preset options moduleTeamInventory <arg>
							} else if (args[2].equalsIgnoreCase("moduleteaminventory")) {
								if (args[3].equalsIgnoreCase("true")) {
									PresetHandler.moduleTeamInventory = true;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr + "true"
											+ cg + "!");

								} else if (args[3].equalsIgnoreCase("false")) {
									PresetHandler.moduleTeamInventory = false;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr
											+ "false" + cg + "!");

								} else {
									sender.sendMessage(ChatColor.RED + "Invalid option! Either true or false.");
								}

								// uhcp preset options moduleOneHeartStart <arg>
							} else if (args[2].equalsIgnoreCase("moduleoneheartstart")) {
								if (args[3].equalsIgnoreCase("true")) {
									PresetHandler.moduleOneHeartStart = true;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr + "true"
											+ cg + "!");

								} else if (args[3].equalsIgnoreCase("false")) {
									PresetHandler.moduleOneHeartStart = false;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr
											+ "false" + cg + "!");

								} else {
									sender.sendMessage(ChatColor.RED + "Invalid option! Either true or false.");
								}

							//uhcp preset options moduleSlimeBoost <arg>
							} else if (args[2].equalsIgnoreCase("moduleslimeboost")) {
								if (args[3].equalsIgnoreCase("true")) {
									PresetHandler.moduleSlimeBoost = true;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr + "true"
											+ cg + "!");

								} else if (args[3].equalsIgnoreCase("false")) {
									PresetHandler.moduleSlimeBoost = false;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr
											+ "false" + cg + "!");

								} else {
									sender.sendMessage(ChatColor.RED + "Invalid option! Either true or false.");
								}
								
							//uhcp preset options moduleStatistics <arg>
							} else if(args[2].equalsIgnoreCase("moduleStatistics")) {
								if(args[3].equalsIgnoreCase("true")) {
									PresetHandler.moduleStatistics = true;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr + "true"
											+ cg + "!");

								} else if (args[3].equalsIgnoreCase("false")) {
									PresetHandler.moduleStatistics = false;
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[2] + cg + " changed to " + cr
											+ "false" + cg + "!");
								} else {
									sender.sendMessage(ChatColor.RED + "Invalid option! Either true or false.");
								}
																
							// uhcp preset options timeToPvp <arg>
							} else if (args[2].equalsIgnoreCase("timetopvp")) {
								if (isNumber(args[3]) && Integer.valueOf(args[4]) > 0) {
									PresetHandler.timeToPvp = Integer.valueOf(args[4]);
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[3] + cg + " changed to " + cr
											+ args[4] + cg + "!");

								} else {
									sender.sendMessage(ChatColor.RED + "Invalid option! Must be a positive number!");
								}

								// uhcp preset options worldBorderSize <arg>
							} else if (args[2].equalsIgnoreCase("worldbordersize")) {
								if (isNumber(args[3]) && Integer.valueOf(args[4]) > 0) {
									PresetHandler.worldBorderSize = Integer.valueOf(args[4]);
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[3] + cg + " changed to " + cr
											+ args[4] + cg + "!");

								} else {
									sender.sendMessage(ChatColor.RED + "Invalid option! Must be a number!");
								}

								// uhcp preset options worldBorderShrinkAfter <arg>
							} else if (args[2].equalsIgnoreCase("worldbordershrinkafter")) {
								if (isNumber(args[3]) && Integer.valueOf(args[4]) > 0) {
									PresetHandler.worldBorderShrinkAfter = Integer.valueOf(args[4]);
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[3] + cg + " changed to " + cr
											+ args[4] + cg + "!");

								} else {
									sender.sendMessage(ChatColor.RED + "Invalid option! Must be a number!");
								}

								// uhcp preset options worldBorderShrinkTo <arg>
							} else if (args[2].equalsIgnoreCase("worldbordershrinkto")) {
								if (isNumber(args[3]) && Integer.valueOf(args[4]) > 0) {
									PresetHandler.worldBorderShrinkTo = Integer.valueOf(args[4]);
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[3] + cg + " changed to " + cr
											+ args[4] + cg + "!");

								} else {
									sender.sendMessage(ChatColor.RED + "Invalid option! Must be a number!");
								}

								// uhcp preset options gameTime <arg>
							} else if (args[2].equalsIgnoreCase("gametime")) {
								if (isNumber(args[3]) && Integer.valueOf(args[4]) > 0) {
									PresetHandler.gameTime = Integer.valueOf(args[4]);
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[3] + cg + " changed to " + cr
											+ args[4] + cg + "!");

								} else {
									sender.sendMessage(ChatColor.RED + "Invalid option! Must be a number!");
								}

								// uhcp preset options moduleAntiCheatTime <arg>
							} else if (args[2].equalsIgnoreCase("moduleanticheattime")) {
								if (isNumber(args[3]) && Integer.valueOf(args[4]) > 0) {
									PresetHandler.moduleAntiCheatTime = Integer.valueOf(args[4]);
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[3] + cg + " changed to " + cr
											+ args[4] + cg + "!");

								} else {
									sender.sendMessage(ChatColor.RED + "Invalid option! Must be a number!");
								}

								// uhcp preset options moduleAxeOfDestructionLevelOneTime <arg>
							} else if (args[2].equalsIgnoreCase("moduleaxeofdestructionlevelonetime")) {
								if (isNumber(args[3]) && Integer.valueOf(args[4]) > 0) {
									PresetHandler.moduleAxeOfDestructionLevelOneTime = Integer.valueOf(args[4]);
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[3] + cg + " changed to " + cr
											+ args[4] + cg + "!");

								} else {
									sender.sendMessage(ChatColor.RED + "Invalid option! Must be a number!");
								}

								// uhcp preset options moduleAxeOfDestructionLevelTwoTime <arg>
							} else if (args[2].equalsIgnoreCase("moduleaxeofdestructionleveltwotime")) {
								if (isNumber(args[3]) && Integer.valueOf(args[4]) > 0) {
									PresetHandler.moduleAxeOfDestructionLevelTwoTime = Integer.valueOf(args[4]);
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[3] + cg + " changed to " + cr
											+ args[4] + cg + "!");

								} else {
									sender.sendMessage(ChatColor.RED + "Invalid option! Must be a number!");

								}

								// uhcp preset options moduleSwordOfDivinityLevelOneTime <arg>
							} else if (args[2].equalsIgnoreCase("moduleswordofdivinitylevelonetime")) {
								if (isNumber(args[3]) && Integer.valueOf(args[4]) > 0) {
									PresetHandler.moduleSwordOfDivinityLevelOneTime = Integer.valueOf(args[4]);
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[3] + cg + " changed to " + cr
											+ args[4] + cg + "!");

								} else {
									sender.sendMessage(ChatColor.RED + "Invalid option! Must be a number!");
								}

								// uhcp preset options moduleSwordOfDivinityLevelTwoTime <arg>
							} else if (args[2].equalsIgnoreCase("moduleswordofdivinityleveltwotime")) {
								if (isNumber(args[3]) && Integer.valueOf(args[4]) > 0) {
									PresetHandler.moduleSwordOfDivinityLevelTwoTime = Integer.valueOf(args[4]);
									PresetHandler.changedPresetOption();
									sender.sendMessage(cg + "Option " + cr + args[3] + cg + " changed to " + cr
											+ args[4] + cg + "!");

								} else {
									sender.sendMessage(ChatColor.RED + "Invalid option! Must be a number!");
								}
								
								//uhcp preset options <invalid option>
							} else {
								sender.sendMessage(ChatColor.RED + "Invalid option!");
							}
						}
					}
				}
			}
		}
		return true;
	}

	// Iterate over all the available presets, to check if the one the user wants to
	// use exists
	boolean doesPresetExist(String presetName) {
		List<String> availablePresets = ConfigurationHandler.availablePresets;

		return availablePresets.contains(presetName);
	}

	// Check if the supplied String is a round number
	boolean isNumber(String value) {
		try {
			Integer.valueOf(value);

			return true;
		} catch (NumberFormatException e) {

			return false;
		}

	}

}
