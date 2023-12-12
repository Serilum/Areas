package com.natamus.areas;

import com.mojang.blaze3d.vertex.PoseStack;
import com.natamus.areas.events.ClientEvent;
import com.natamus.areas.events.GUIEvent;
import com.natamus.areas.fabric.cmds.FabricClientCommandAreas;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;

public class ModFabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() { 
		registerEvents();
	}
	
	private void registerEvents() {
		ClientTickEvents.START_CLIENT_TICK.register((Minecraft mc) -> {
			ClientEvent.onClientTick(mc);
		});

		FabricClientCommandAreas.register(ClientCommandManager.DISPATCHER);

		HudRenderCallback.EVENT.register((PoseStack poseStack, float tickDelta) -> {
			GUIEvent.renderOverlay(poseStack, tickDelta);
		});
	}
}
