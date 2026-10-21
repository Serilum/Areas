package com.serilum.areas.forge.events;

import com.serilum.areas.callbacks.AreaCallback;
import com.serilum.areas.cmds.ClientCommandAreas;
import com.serilum.areas.data.ClientConstants;
import com.serilum.areas.events.ClientEvent;
import com.serilum.areas.forge.api.AreaEnterEvent;
import com.serilum.areas.forge.api.AreaLeaveEvent;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

import java.lang.invoke.MethodHandles;

public class ForgeClientEvent {
	public static void registerEventsInBus() {
		BusGroup.DEFAULT.register(MethodHandles.lookup(), ForgeClientEvent.class);

		AreaCallback.ON_AREA_ENTER.register((player, areaObject) -> {
			AreaEnterEvent.BUS.post(new AreaEnterEvent(player, areaObject.areaName, areaObject.areaType, areaObject.location, areaObject.radius));
		});

		AreaCallback.ON_AREA_LEAVE.register((player, areaObject) -> {
			AreaLeaveEvent.BUS.post(new AreaLeaveEvent(player, areaObject.areaName, areaObject.areaType, areaObject.location, areaObject.radius));
		});
	}

	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent.Pre e) {
		ClientEvent.onClientTick(ClientConstants.mc);
	}

	@SubscribeEvent
	public static void registerCommands(RegisterClientCommandsEvent e) {
		ClientCommandAreas.register(e.getDispatcher());
	}
}