package com.serilum.areas.neoforge.events;

import com.serilum.areas.cmds.ClientCommandAreas;
import com.serilum.areas.data.ClientConstants;
import com.serilum.areas.events.ClientEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;

public class NeoForgeClientEvent {
	@SubscribeEvent
	public static void onClientTick(ClientTickEvent.Pre e) {
		ClientEvent.onClientTick(ClientConstants.mc);
	}

	@SubscribeEvent
	public static void registerCommands(RegisterClientCommandsEvent e) {
		ClientCommandAreas.register(e.getDispatcher());
	}
}