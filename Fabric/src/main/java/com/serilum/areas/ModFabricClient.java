package com.serilum.areas;

import com.serilum.areas.events.ClientEvent;
import com.serilum.areas.fabric.cmds.FabricClientCommandAreas;
import net.fabricmc.api.ClientModInitializer;
import com.serilum.areas.util.Reference;
import com.natamus.collective.check.ShouldLoadCheck;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;

public class ModFabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() { 
		if (!ShouldLoadCheck.shouldLoad(Reference.MOD_ID)) {
			return;
		}

		registerEvents();
	}
	
	private void registerEvents() {
		ClientTickEvents.START_CLIENT_TICK.register((Minecraft mc) -> {
			ClientEvent.onClientTick(mc);
		});

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			FabricClientCommandAreas.register(dispatcher);
		});
	}
}
