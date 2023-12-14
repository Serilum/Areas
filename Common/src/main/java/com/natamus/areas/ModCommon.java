package com.natamus.areas;

import com.natamus.areas.config.ConfigHandler;
import com.natamus.areas.integrations.BlueMapIntegration;
import com.natamus.areas.util.Reference;
import com.natamus.collective.config.GenerateJSONFiles;
import de.bluecolored.bluemap.api.BlueMapAPI;

public class ModCommon {

	public static void init() {
		ConfigHandler.initConfig();
		load();
	}

	private static void load() {
		GenerateJSONFiles.requestJSONFile(Reference.MOD_ID, "area_names.json");
//		try {
//			BlueMapAPI.onEnable(BlueMapIntegration::updateBlueMap);
//		} catch (NoClassDefFoundError | IllegalStateException ignored) {
//			System.out.println("BlueMap is not loaded");
//		}
	}
}