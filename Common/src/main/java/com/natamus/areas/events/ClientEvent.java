package com.natamus.areas.events;

import com.natamus.areas.config.ConfigHandler;
import com.natamus.areas.objects.AreaObject;
import com.natamus.areas.data.AreaVariables;
import com.natamus.areas.util.Util;
import com.natamus.collective.data.BlockEntityData;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTypes;

import java.util.ArrayList;
import java.util.List;

public class ClientEvent {
	public static void onClientTick(Minecraft mc) {
		Player player = mc.player;
        if (player == null) {
            return;
        }

		GUIEvent.tickHUDFade();

		if (player.tickCount % 20 != 0) {
			return;
		}

		for (AreaObject enteredAreaObject : AreaVariables.enteredAreas) {
			if (Util.getAreaSign(enteredAreaObject.level, enteredAreaObject.location) == null) {
				Util.removedArea(enteredAreaObject, player);
			}
		}

		Level level = player.level();
		BlockPos ppos = player.blockPosition();

		int radius = ConfigHandler.radiusAroundPlayerToCheckForSigns;

		List<BlockEntity> nearbySigns = new ArrayList<BlockEntity>();
		nearbySigns.addAll(BlockEntityData.getCachedBlockEntities(BlockEntityTypes.SIGN, level));
		nearbySigns.addAll(BlockEntityData.getCachedBlockEntities(BlockEntityTypes.HANGING_SIGN, level));

		List<AreaObject> allAreaObjects = new ArrayList<AreaObject>();
		for (BlockEntity nearbyBlockEntity : nearbySigns) {
			BlockPos nspos = nearbyBlockEntity.getBlockPos();
			if (!nspos.closerThan(ppos, radius)) {
				continue;
			}

			AreaObject areaObject = Util.getAreaSign(level, nspos);
			if (areaObject == null) {
				continue;
			}

			allAreaObjects.add(areaObject);
		}

		for (AreaObject areaObject : allAreaObjects) {
			if (areaObject == null) {
				continue;
			}

			BlockPos nspos = areaObject.location;
			if (ppos.closerThan(nspos, areaObject.radius)) {
				if (AreaVariables.enteredAreas.contains(areaObject)) {
					continue;
				}

				Util.enterArea(areaObject, player);
			}
			else if (AreaVariables.enteredAreas.contains(areaObject)) {
				Util.exitArea(areaObject, player);
			}
		}
	}
}
