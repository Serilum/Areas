package com.natamus.areas.objects;

import com.natamus.areas.util.Reference;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Variables {
	public static ResourceLocation networkchannel = new ResourceLocation(Reference.MOD_ID, Reference.MOD_ID);
	public static HashMap<Level, HashMap<BlockPos, AreaObject>> areasperlevel = new HashMap<Level, HashMap<BlockPos, AreaObject>>();
	public static HashMap<Level, CopyOnWriteArrayList<BlockPos>> ignoresignsperlevel = new HashMap<Level, CopyOnWriteArrayList<BlockPos>>();
	
	public static HashMap<Level, CopyOnWriteArrayList<BlockPos>> checkifshouldignoreperlevel = new HashMap<Level, CopyOnWriteArrayList<BlockPos>>();
	public static HashMap<Level, HashMap<BlockPos, Integer>> ignoremap = new HashMap<Level, HashMap<BlockPos, Integer>>();
}
