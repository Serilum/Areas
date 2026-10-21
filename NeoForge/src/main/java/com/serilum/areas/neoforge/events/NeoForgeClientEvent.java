package com.serilum.areas.neoforge.events;

import com.serilum.areas.callbacks.AreaCallback;
import com.serilum.areas.cmds.ClientCommandAreas;
import com.serilum.areas.data.ClientConstants;
import com.serilum.areas.events.ClientEvent;
import com.serilum.areas.neoforge.api.AreaEnterEvent;
import com.serilum.areas.neoforge.api.AreaLeaveEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;

public class NeoForgeClientEvent {
	public static void registerAreaCallbacks() {
		AreaCallback.ON_AREA_ENTER.register((player, areaObject) -> {
			NeoForge.EVENT_BUS.post(new AreaEnterEvent(player, areaObject.areaName, areaObject.areaType, areaObject.location, areaObject.radius));
		});

		AreaCallback.ON_AREA_LEAVE.register((player, areaObject) -> {
			NeoForge.EVENT_BUS.post(new AreaLeaveEvent(player, areaObject.areaName, areaObject.areaType, areaObject.location, areaObject.radius));
		});
	}

	@SubscribeEvent
	public static void onClientTick(ClientTickEvent.Pre e) {
		ClientEvent.onClientTick(ClientConstants.mc);
	}

	@SubscribeEvent
	public static void registerCommands(RegisterClientCommandsEvent e) {
		ClientCommandAreas.register(e.getDispatcher());
	}
}