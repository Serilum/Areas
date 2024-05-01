package com.natamus.areas;

import com.natamus.areas.config.ConfigHandler;
import com.natamus.areas.events.GUIEvent;
import com.natamus.areas.util.Reference;
import com.natamus.collective.config.GenerateJSONFiles;
import com.natamus.collective.globalcallbacks.CollectiveGuiCallback;

public class ModCommon {

	public static void init() {
		ConfigHandler.initConfig();
		load();
	}

	private static void load() {
		GenerateJSONFiles.requestJSONFile(Reference.MOD_ID, "area_names.json");

		CollectiveGuiCallback.ON_GUI_RENDER.register(((guiGraphics, tickDelta) -> {
			GUIEvent.renderOverlay(guiGraphics, tickDelta);
		}));
	}
}