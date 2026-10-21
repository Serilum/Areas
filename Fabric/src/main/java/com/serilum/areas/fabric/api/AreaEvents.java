package com.serilum.areas.fabric.api;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

public class AreaEvents {
	private AreaEvents() { }

	public static final Event<Area_Enter> AREA_ENTER = EventFactory.createArrayBacked(Area_Enter.class, callbacks -> (player, areaName, areaType, signPos, radius) -> {
		for (Area_Enter callback : callbacks) {
			callback.onAreaEnter(player, areaName, areaType, signPos, radius);
		}
	});

	public static final Event<Area_Leave> AREA_LEAVE = EventFactory.createArrayBacked(Area_Leave.class, callbacks -> (player, areaName, areaType, signPos, radius) -> {
		for (Area_Leave callback : callbacks) {
			callback.onAreaLeave(player, areaName, areaType, signPos, radius);
		}
	});

	@FunctionalInterface
	public interface Area_Enter {
		 void onAreaEnter(Player player, String areaName, String areaType, BlockPos signPos, int radius);
	}

	@FunctionalInterface
	public interface Area_Leave {
		 void onAreaLeave(Player player, String areaName, String areaType, BlockPos signPos, int radius);
	}
}
