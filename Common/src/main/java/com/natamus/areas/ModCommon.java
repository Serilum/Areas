package com.natamus.areas;

import com.natamus.areas.config.ConfigHandler;
import com.natamus.areas.events.GUIEvent;
import com.natamus.areas.util.Reference;
import com.natamus.collective.config.GenerateJSONFiles;
import com.natamus.collective.data.BlockEntityData;
import com.natamus.collective.globalcallbacks.CollectiveGuiCallback;
import com.natamus.collective.services.Services;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModCommon {

	public static void init() {
		ConfigHandler.initConfig();
		load();
	}

	private static void load() {
		GenerateJSONFiles.requestJSONFile(Reference.MOD_ID, "area_names.json");

		BlockEntityData.addBlockEntityToCache(BlockEntityType.SIGN, false, true);
		BlockEntityData.addBlockEntityToCache(BlockEntityType.HANGING_SIGN, false, true);

		if (Services.MODLOADER.isClientSide()) {
			CollectiveGuiCallback.ON_GUI_RENDER.register(((guiGraphics, deltaTracker) -> {
				GUIEvent.renderOverlay(guiGraphics, deltaTracker);
			}));
		}
	}
}