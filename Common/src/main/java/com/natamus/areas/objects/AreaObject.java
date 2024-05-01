package com.natamus.areas.objects;

import com.natamus.areas.data.AreaVariables;
import com.natamus.collective.functions.HashMapFunctions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.List;

public class AreaObject {
	public Level level;
	public BlockPos location;
	public String areaName;
	public int radius;
	public String customRGB;
	public List<String> signLines;

	public AreaObject(Level levelIn, BlockPos locationIn, String areaNameIn, int radiusIn, String customRGBIn, List<String> signLinesIn) {
		level = levelIn;
		location = locationIn;
		areaName = areaNameIn;
		radius = radiusIn;
		customRGB = customRGBIn;
		signLines = signLinesIn;

		HashMapFunctions.computeIfAbsent(AreaVariables.areaObjects, level, k -> new HashMap<BlockPos, AreaObject>()).put(location, this);
	}
}
