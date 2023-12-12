package com.natamus.areas.objects;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class AreaObject {
	public Level level;
	public BlockPos location;
	public String areaname;
	public int radius;
	public String customrgb;

	public List<Player> containsplayers;

	public AreaObject(Level w, BlockPos l, String a, int r, String rgb) {
		level = w;
		location = l;
		areaname = a;
		radius = r;
		customrgb = rgb;
		containsplayers = new ArrayList<Player>();

		if (Variables.areasperlevel.containsKey(level)) {
			Variables.areasperlevel.get(level).put(l, this);
		}
	}
}
