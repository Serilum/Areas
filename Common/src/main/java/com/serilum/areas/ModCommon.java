package com.serilum.areas;

import com.serilum.areas.config.ConfigHandler;
import com.serilum.areas.util.Reference;
import com.natamus.collective.config.GenerateJSONFiles;
import com.natamus.collective.data.BlockEntityData;
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
	}
}