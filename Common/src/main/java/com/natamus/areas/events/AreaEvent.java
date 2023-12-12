package com.natamus.areas.events;

import com.natamus.areas.data.AreaVariables;
import com.natamus.areas.functions.ZoneFunctions;
import com.natamus.areas.objects.AreaObject;
import com.natamus.areas.util.Util;
import com.natamus.collective.functions.HashMapFunctions;
import com.natamus.collective.functions.SignFunctions;
import com.natamus.collective.functions.StringFunctions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AreaEvent {
	public static HashMap<Level, Integer> tickDelayPerLevel = new HashMap<Level, Integer>();

	public static void onWorldTick(ServerLevel level) {
		int ticks = HashMapFunctions.computeIfAbsent(tickDelayPerLevel, level, k -> 1);
		if (ticks % 20 != 0) {
			tickDelayPerLevel.put(level, ticks+1);
			return;
		}
		tickDelayPerLevel.put(level, 1);

		for (BlockPos signPos : HashMapFunctions.computeIfAbsent(AreaVariables.newSignsToCheck, level, k -> new CopyOnWriteArrayList<BlockPos>())) {
			BlockEntity blockEntity = level.getBlockEntity(signPos);
			if (!(blockEntity instanceof SignBlockEntity)) {
				AreaVariables.newSignsToCheck.get(level).remove(signPos);
				continue;
			}

			int checksLeft = HashMapFunctions.computeIfAbsent(AreaVariables.newSignChecksLeft, level, k -> new HashMap<BlockPos, Integer>()).getOrDefault(signPos, 60);

			SignBlockEntity signBlockEntity = (SignBlockEntity)blockEntity;
			List<String> signLines = SignFunctions.getSignText(blockEntity);

			if (!String.join("", signLines).equals("")) {
				AreaObject areaObject = Util.getAreaSign(level, signPos);
				if (areaObject == null) {
					AreaVariables.newSignsToCheck.get(level).remove(signPos);
					AreaVariables.newSignChecksLeft.get(level).remove(signPos);
					continue;
				}

				String zonePrefix = "Area";
				String rgb = "";
				boolean customrgb = false;

				int i = -1;
				for (String line : signLines) {
					i += 1;
					if (i == 0 && !ZoneFunctions.hasZonePrefix(line)) {
						continue;
					}

					if (line.length() < 1) {
						continue;
					}

					for (String zpx : ZoneFunctions.zonePrefixes) {
						if (line.toLowerCase().contains(zpx)) {
							zonePrefix = StringFunctions.capitalizeFirst(zpx.replace("[", "").replace("]", ""));
							break;
						}
					}

					String possiblergb = ZoneFunctions.getZoneRGB(line.toLowerCase());
					if (!possiblergb.equals("")) {
						customrgb = true;
					}
				}

				Util.updateAreaSign(level, signPos, signBlockEntity, signLines, areaObject.areaName, zonePrefix, areaObject.customRGB, areaObject.radius, customrgb);

				AreaVariables.newSignsToCheck.get(level).remove(signPos);
				AreaVariables.newSignChecksLeft.get(level).remove(signPos);
				continue;
			}

			if (checksLeft == 0) {
				AreaVariables.newSignsToCheck.get(level).remove(signPos);
				AreaVariables.newSignChecksLeft.get(level).remove(signPos);
				continue;
			}

			AreaVariables.newSignChecksLeft.get(level).put(signPos, checksLeft-1);
		}
	}

	public static void onNeighbourNotice(Level level, BlockPos pos, BlockState state, EnumSet<Direction> notifiedSides, boolean forceRedstoneUpdate) {
		if (level.isClientSide) {
			return;
		}

		if (Util.isSignBlock(state.getBlock())) {
			AreaVariables.newSignsToCheck.computeIfAbsent(level, k -> new CopyOnWriteArrayList<BlockPos>()).add(pos.immutable());
		}
	}
}
