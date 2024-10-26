package com.natamus.areas.forge.events;

import com.natamus.areas.cmds.ClientCommandAreas;
import com.natamus.areas.data.ClientConstants;
import com.natamus.areas.events.ClientEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT)
public class ForgeClientEvent {
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent e) {
		if (e.phase.equals(TickEvent.Phase.START)) {
			ClientEvent.onClientTick(ClientConstants.mc);
		}
	}

	@SubscribeEvent
	public void registerCommands(RegisterClientCommandsEvent e) {
		ClientCommandAreas.register(e.getDispatcher());
	}
}