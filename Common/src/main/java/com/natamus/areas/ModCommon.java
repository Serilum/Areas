package com.natamus.areas;

import com.natamus.areas.config.ConfigHandler;
import com.natamus.areas.util.Reference;
import com.natamus.collective.config.GenerateJSONFiles;

public class ModCommon {

	public static void init() {
		ConfigHandler.initConfig();
		load();
	}

	private static void load() {
		GenerateJSONFiles.requestJSONFile(Reference.MOD_ID, "area_names.json");
	}
}