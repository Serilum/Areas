package com.natamus.areas.data;

import com.natamus.areas.objects.AreaObject;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class AreaVariables {
	public static HashMap<Level, HashMap<BlockPos, AreaObject>> areaObjects = new HashMap<Level, HashMap<BlockPos, AreaObject>>();
	public static HashMap<Level, CopyOnWriteArrayList<BlockPos>> newSignsToCheck = new HashMap<Level, CopyOnWriteArrayList<BlockPos>>();
	public static HashMap<Level, HashMap<BlockPos, Integer>> newSignChecksLeft = new HashMap<Level, HashMap<BlockPos, Integer>>();
	public static CopyOnWriteArrayList<AreaObject> enteredAreas = new CopyOnWriteArrayList<AreaObject>();
}