package nl.thedutchmc.uhcplus.presets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import nl.thedutchmc.uhcplus.UhcPlus;

public class DefaultPreset {

	private File presetConfigFile;


	public void loadPreset(String presetName, boolean shouldReturn) {

		presetConfigFile = new File(UhcPlus.INSTANCE.getDataFolder() + File.separator + "presets", presetName + ".yml");


		// Check if the config file exists, if it doesnt, create it.
		if (!presetConfigFile.exists()) {
			presetConfigFile.getParentFile().mkdirs();

			try {
				FileUtils.copyToFile(UhcPlus.INSTANCE.getResource("default.yml"), presetConfigFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Load the preset configuration file
		if (shouldReturn) {
			readPreset();
		}
	}

	public void readPreset() {

		try {
			BufferedReader reader = new BufferedReader(new FileReader(presetConfigFile));

			String curLine;
			while((curLine = reader.readLine()) != null) {
				if(curLine.startsWith("#")) continue;
				
				String optionName = curLine.split(":")[0];
				String optionValue = curLine.split(":")[1].replaceFirst(" ", "");
				
				if(optionValue.matches("^[0-9]+$")) {
					PresetHandler.setPrefabOption(optionName, Integer.valueOf(optionValue));
				} else if(optionValue.equalsIgnoreCase("true") || optionValue.equalsIgnoreCase("false")) {
					 PresetHandler.setPrefabOption(optionName, Boolean.valueOf(optionValue));
				} else {
					PresetHandler.setPrefabOption(optionName, optionValue);
				}
			}	
			
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writePreset(String presetName) {
		loadPreset(presetName, false);

		try {
			FileWriter fw = new FileWriter(presetConfigFile, false);
			
			for(Map.Entry<String, Object> entry : PresetHandler.getPrefabOptions().entrySet()) {
				fw.write(entry.getKey() + ": " + entry.getValue().toString());
				fw.write("\n");
			}
			
			fw.flush();
			fw.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}