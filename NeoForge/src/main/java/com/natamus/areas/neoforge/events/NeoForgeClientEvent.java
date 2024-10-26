package com.natamus.areas.neoforge.events;

import com.natamus.areas.cmds.ClientCommandAreas;
import com.natamus.areas.data.ClientConstants;
import com.natamus.areas.events.ClientEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;

@EventBusSubscriber(value = Dist.CLIENT)
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