package com.serilum.areas.callbacks;

import com.natamus.collective.implementations.event.Event;
import com.natamus.collective.implementations.event.EventFactory;
import com.serilum.areas.objects.AreaObject;
import net.minecraft.world.entity.player.Player;

public class AreaCallback {
	private AreaCallback() { }

	public static final Event<On_Area_Enter> ON_AREA_ENTER = EventFactory.createArrayBacked(On_Area_Enter.class, callbacks -> (player, areaObject) -> {
		for (On_Area_Enter callback : callbacks) {
			callback.onAreaEnter(player, areaObject);
		}
	});

	public static final Event<On_Area_Leave> ON_AREA_LEAVE = EventFactory.createArrayBacked(On_Area_Leave.class, callbacks -> (player, areaObject) -> {
		for (On_Area_Leave callback : callbacks) {
			callback.onAreaLeave(player, areaObject);
		}
	});

	@FunctionalInterface
	public interface On_Area_Enter {
		 void onAreaEnter(Player player, AreaObject areaObject);
	}

	@FunctionalInterface
	public interface On_Area_Leave {
		 void onAreaLeave(Player player, AreaObject areaObject);
	}
}
